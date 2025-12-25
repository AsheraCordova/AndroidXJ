//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.customview.widget;
import r.android.content.Context;
import r.android.util.Log;
import r.android.view.View;
import r.android.view.ViewConfiguration;
import r.android.view.ViewGroup;
import r.android.view.animation.Interpolator;
import r.android.widget.OverScroller;
import androidx.core.view.ViewCompat;
import java.util.Arrays;
public class ViewDragHelper {
  public static final int INVALID_POINTER=-1;
  public static final int STATE_IDLE=0;
  public static final int STATE_DRAGGING=1;
  public static final int STATE_SETTLING=2;
  public static final int EDGE_LEFT=1 << 0;
  public static final int EDGE_RIGHT=1 << 1;
  public static final int EDGE_TOP=1 << 2;
  public static final int EDGE_BOTTOM=1 << 3;
  public static final int EDGE_ALL=EDGE_LEFT | EDGE_TOP | EDGE_RIGHT| EDGE_BOTTOM;
  public static final int DIRECTION_HORIZONTAL=1 << 0;
  public static final int DIRECTION_VERTICAL=1 << 1;
  public static final int DIRECTION_ALL=DIRECTION_HORIZONTAL | DIRECTION_VERTICAL;
  private static final int EDGE_SIZE=20;
  private static final int BASE_SETTLE_DURATION=256;
  private static final int MAX_SETTLE_DURATION=600;
  private static final boolean DEBUG=false;
  private int mDragState;
  private int mTouchSlop;
  private int mActivePointerId=INVALID_POINTER;
  private int mPointersDown;
  private final float mMaxVelocity;
  private float mMinVelocity;
  private int mEdgeSize;
  private final int mDefaultEdgeSize;
  private int mTrackingEdges;
  private final OverScroller mScroller;
  private final Callback mCallback;
  private View mCapturedView;
  private boolean mReleaseInProgress;
  private final ViewGroup mParentView;
  private Interpolator mInterpolator;
public abstract static class Callback {
    public void onViewDragStateChanged(    int state){
    }
    public void onViewPositionChanged(    View changedView,    int left,    int top,    int dx,    int dy){
    }
    public void onViewCaptured(    View capturedChild,    int activePointerId){
    }
    public void onViewReleased(    View releasedChild,    float xvel,    float yvel){
    }
    public void onEdgeTouched(    int edgeFlags,    int pointerId){
    }
    public boolean onEdgeLock(    int edgeFlags){
      return false;
    }
    public void onEdgeDragStarted(    int edgeFlags,    int pointerId){
    }
    public int getOrderedChildIndex(    int index){
      return index;
    }
    public int getViewHorizontalDragRange(    View child){
      return 0;
    }
    public int getViewVerticalDragRange(    View child){
      return 0;
    }
    public abstract boolean tryCaptureView(    View child,    int pointerId);
    public int clampViewPositionHorizontal(    View child,    int left,    int dx){
      return 0;
    }
    public int clampViewPositionVertical(    View child,    int top,    int dy){
      return 0;
    }
  }
  private static final Interpolator sInterpolator=new Interpolator(){
    public float getInterpolation(    float t){
      t-=1.0f;
      return t * t * t* t* t + 1.0f;
    }
  }
;
  private final Runnable mSetIdleRunnable=new Runnable(){
    public void run(){
      setDragState(STATE_IDLE);
    }
  }
;
  public static ViewDragHelper create(  ViewGroup forParent,  Callback cb){
    return new ViewDragHelper(forParent.getContext(),forParent,cb);
  }
  public static ViewDragHelper create(  ViewGroup forParent,  float sensitivity,  Callback cb){
    final ViewDragHelper helper=create(forParent,cb);
    helper.mTouchSlop=(int)(helper.mTouchSlop * (1 / sensitivity));
    return helper;
  }
  private ViewDragHelper(  Context context,  ViewGroup forParent,  Callback cb){
    requireNonNull(forParent,"Parent view may not be null");
    requireNonNull(cb,"Callback may not be null");
    mParentView=forParent;
    mCallback=cb;
    final ViewConfiguration vc=ViewConfiguration.get(context);
    final float density=context.getResources().getDisplayMetrics().density;
    mDefaultEdgeSize=(int)(EDGE_SIZE * density + 0.5f);
    mEdgeSize=mDefaultEdgeSize;
    mTouchSlop=vc.getScaledTouchSlop();
    mMaxVelocity=vc.getScaledMaximumFlingVelocity();
    mMinVelocity=vc.getScaledMinimumFlingVelocity();
    mInterpolator=sInterpolator;
    Interpolator delegatingInterpolator=new Interpolator(){
      public float getInterpolation(      float input){
        return mInterpolator.getInterpolation(input);
      }
    }
;
    mScroller=new OverScroller(context,delegatingInterpolator);
  }
  public boolean smoothSlideViewTo(  View child,  int finalLeft,  int finalTop){
    mCapturedView=child;
    mActivePointerId=INVALID_POINTER;
    boolean continueSliding=forceSettleCapturedViewAt(finalLeft,finalTop,0,0);
    if (!continueSliding && mDragState == STATE_IDLE && mCapturedView != null) {
      mCapturedView=null;
    }
    return continueSliding;
  }
  public boolean smoothSlideViewTo(  View child,  int finalLeft,  int finalTop,  int duration,  Interpolator interpolator){
    mCapturedView=child;
    mActivePointerId=INVALID_POINTER;
    boolean continueSliding=forceSettleCapturedViewAt(finalLeft,finalTop,duration,interpolator);
    if (!continueSliding && mDragState == STATE_IDLE && mCapturedView != null) {
      mCapturedView=null;
    }
    return continueSliding;
  }
  public boolean settleCapturedViewAt(  int finalLeft,  int finalTop){
    if (!mReleaseInProgress) {
      throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to " + "Callback#onViewReleased");
    }
    return forceSettleCapturedViewAt(finalLeft,finalTop,/*(int)mVelocityTracker.getXVelocity(mActivePointerId),(int)mVelocityTracker.getYVelocity(mActivePointerId)*/0,0);
  }
  private boolean forceSettleCapturedViewAt(  int finalLeft,  int finalTop,  int xvel,  int yvel){
    final int startLeft=mCapturedView.getLeft();
    final int startTop=mCapturedView.getTop();
    final int dx=finalLeft - startLeft;
    final int dy=finalTop - startTop;
    if (dx == 0 && dy == 0) {
      mScroller.abortAnimation();
      setDragState(STATE_IDLE);
      return false;
    }
    final int duration=computeSettleDuration(mCapturedView,dx,dy,xvel,yvel);
    mInterpolator=sInterpolator;
    mScroller.startScroll(startLeft,startTop,dx,dy,duration);
    setDragState(STATE_SETTLING);
    return true;
  }
  private boolean forceSettleCapturedViewAt(  int finalLeft,  int finalTop,  int duration,  Interpolator interpolator){
    final int startLeft=mCapturedView.getLeft();
    final int startTop=mCapturedView.getTop();
    final int dx=finalLeft - startLeft;
    final int dy=finalTop - startTop;
    if (dx == 0 && dy == 0) {
      mScroller.abortAnimation();
      setDragState(STATE_IDLE);
      return false;
    }
    if (interpolator != null) {
      mInterpolator=interpolator;
    }
 else {
      mInterpolator=sInterpolator;
    }
    mScroller.startScroll(startLeft,startTop,dx,dy,duration);
    setDragState(STATE_SETTLING);
    return true;
  }
  private int computeSettleDuration(  View child,  int dx,  int dy,  int xvel,  int yvel){
    xvel=clampMag(xvel,(int)mMinVelocity,(int)mMaxVelocity);
    yvel=clampMag(yvel,(int)mMinVelocity,(int)mMaxVelocity);
    final int absDx=Math.abs(dx);
    final int absDy=Math.abs(dy);
    final int absXVel=Math.abs(xvel);
    final int absYVel=Math.abs(yvel);
    final int addedVel=absXVel + absYVel;
    final int addedDistance=absDx + absDy;
    final float xweight=xvel != 0 ? (float)absXVel / addedVel : (float)absDx / addedDistance;
    final float yweight=yvel != 0 ? (float)absYVel / addedVel : (float)absDy / addedDistance;
    int xduration=computeAxisDuration(dx,xvel,mCallback.getViewHorizontalDragRange(child));
    int yduration=computeAxisDuration(dy,yvel,mCallback.getViewVerticalDragRange(child));
    return (int)(xduration * xweight + yduration * yweight);
  }
  private int computeAxisDuration(  int delta,  int velocity,  int motionRange){
    if (delta == 0) {
      return 0;
    }
    final int width=mParentView.getWidth();
    final int halfWidth=width / 2;
    final float distanceRatio=Math.min(1f,(float)Math.abs(delta) / width);
    final float distance=halfWidth + halfWidth * distanceInfluenceForSnapDuration(distanceRatio);
    int duration;
    velocity=Math.abs(velocity);
    if (velocity > 0) {
      duration=4 * Math.round(1000 * Math.abs(distance / velocity));
    }
 else {
      final float range=(float)Math.abs(delta) / motionRange;
      duration=(int)((range + 1) * BASE_SETTLE_DURATION);
    }
    return Math.min(duration,MAX_SETTLE_DURATION);
  }
  private int clampMag(  int value,  int absMin,  int absMax){
    final int absValue=Math.abs(value);
    if (absValue < absMin)     return 0;
    if (absValue > absMax)     return value > 0 ? absMax : -absMax;
    return value;
  }
  private float clampMag(  float value,  float absMin,  float absMax){
    final float absValue=Math.abs(value);
    if (absValue < absMin)     return 0;
    if (absValue > absMax)     return value > 0 ? absMax : -absMax;
    return value;
  }
  private float distanceInfluenceForSnapDuration(  float f){
    f-=0.5f;
    f*=0.3f * (float)Math.PI / 2.0f;
    return (float)Math.sin(f);
  }
  public boolean continueSettling(  boolean deferCallbacks){
    if (mDragState == STATE_SETTLING) {
      boolean keepGoing=mScroller.computeScrollOffset();
      final int x=mScroller.getCurrX();
      final int y=mScroller.getCurrY();
      final int dx=x - mCapturedView.getLeft();
      final int dy=y - mCapturedView.getTop();
      if (dx != 0) {
        ViewCompat.offsetLeftAndRight(mCapturedView,dx);
      }
      if (dy != 0) {
        ViewCompat.offsetTopAndBottom(mCapturedView,dy);
      }
      if (dx != 0 || dy != 0) {
        mCallback.onViewPositionChanged(mCapturedView,x,y,dx,dy);
      }
      if (keepGoing && x == mScroller.getFinalX() && y == mScroller.getFinalY()) {
        mScroller.abortAnimation();
        keepGoing=false;
      }
      if (!keepGoing) {
        if (deferCallbacks) {
          mParentView.post(mSetIdleRunnable);
        }
 else {
          setDragState(STATE_IDLE);
        }
      }
    }
    return mDragState == STATE_SETTLING;
  }
  void setDragState(  int state){
    mParentView.removeCallbacks(mSetIdleRunnable);
    if (mDragState != state) {
      mDragState=state;
      mCallback.onViewDragStateChanged(state);
      if (mDragState == STATE_IDLE) {
        mCapturedView=null;
      }
    }
  }
  private static <T>T requireNonNull(  T obj,  String message){
    if (obj == null)     throw new NullPointerException(message);
    return obj;
  }
}
