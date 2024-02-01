package androidx.core.widget;
import r.android.graphics.Rect;
import r.android.view.View;
import r.android.view.ViewGroup;
import r.android.widget.FrameLayout;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ScrollingView;
import androidx.core.view.ViewCompat;
public class NestedScrollView extends FrameLayout implements NestedScrollingParent3, NestedScrollingChild3, ScrollingView {
  static final int ANIMATED_SCROLL_GAP=250;
  private static final int DEFAULT_SMOOTH_SCROLL_DURATION=250;
public interface OnScrollChangeListener {
    void onScrollChange(    NestedScrollView v,    int scrollX,    int scrollY,    int oldScrollX,    int oldScrollY);
  }
  private int mLastMotionY;
  private boolean mIsLayoutDirty=true;
  private boolean mIsLaidOut=false;
  private boolean mIsBeingDragged=false;
  private boolean mFillViewport;
  private boolean mSmoothScrollingEnabled=true;
  private int mTouchSlop;
  private int mMinimumVelocity;
  private int mMaximumVelocity;
  private int mActivePointerId=INVALID_POINTER;
  private final int[] mScrollOffset=new int[2];
  private final int[] mScrollConsumed=new int[2];
  private int mNestedYOffset;
  private int mLastScrollerY;
  private static final int INVALID_POINTER=-1;
  private final NestedScrollingParentHelper mParentHelper;
  private final NestedScrollingChildHelper mChildHelper;
  private OnScrollChangeListener mOnScrollChangeListener;
  public void dispatchNestedScroll(  int dxConsumed,  int dyConsumed,  int dxUnconsumed,  int dyUnconsumed,  int[] offsetInWindow,  int type,  int[] consumed){
    mChildHelper.dispatchNestedScroll(dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,offsetInWindow,type,consumed);
  }
  public boolean startNestedScroll(  int axes,  int type){
    return mChildHelper.startNestedScroll(axes,type);
  }
  public void stopNestedScroll(  int type){
    mChildHelper.stopNestedScroll(type);
  }
  public boolean hasNestedScrollingParent(  int type){
    return mChildHelper.hasNestedScrollingParent(type);
  }
  public boolean dispatchNestedScroll(  int dxConsumed,  int dyConsumed,  int dxUnconsumed,  int dyUnconsumed,  int[] offsetInWindow,  int type){
    return mChildHelper.dispatchNestedScroll(dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,offsetInWindow,type);
  }
  public boolean dispatchNestedPreScroll(  int dx,  int dy,  int[] consumed,  int[] offsetInWindow,  int type){
    return mChildHelper.dispatchNestedPreScroll(dx,dy,consumed,offsetInWindow,type);
  }
  public void setNestedScrollingEnabled(  boolean enabled){
    mChildHelper.setNestedScrollingEnabled(enabled);
  }
  public boolean isNestedScrollingEnabled(){
    return mChildHelper.isNestedScrollingEnabled();
  }
  public boolean startNestedScroll(  int axes){
    return startNestedScroll(axes,ViewCompat.TYPE_TOUCH);
  }
  public void stopNestedScroll(){
    stopNestedScroll(ViewCompat.TYPE_TOUCH);
  }
  public boolean hasNestedScrollingParent(){
    return hasNestedScrollingParent(ViewCompat.TYPE_TOUCH);
  }
  public boolean dispatchNestedScroll(  int dxConsumed,  int dyConsumed,  int dxUnconsumed,  int dyUnconsumed,  int[] offsetInWindow){
    return mChildHelper.dispatchNestedScroll(dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,offsetInWindow);
  }
  public boolean dispatchNestedPreScroll(  int dx,  int dy,  int[] consumed,  int[] offsetInWindow){
    return dispatchNestedPreScroll(dx,dy,consumed,offsetInWindow,ViewCompat.TYPE_TOUCH);
  }
  public boolean dispatchNestedFling(  float velocityX,  float velocityY,  boolean consumed){
    return mChildHelper.dispatchNestedFling(velocityX,velocityY,consumed);
  }
  public boolean dispatchNestedPreFling(  float velocityX,  float velocityY){
    return mChildHelper.dispatchNestedPreFling(velocityX,velocityY);
  }
  public void onNestedScroll(  View target,  int dxConsumed,  int dyConsumed,  int dxUnconsumed,  int dyUnconsumed,  int type,  int[] consumed){
    onNestedScrollInternal(dyUnconsumed,type,consumed);
  }
  private void onNestedScrollInternal(  int dyUnconsumed,  int type,  int[] consumed){
    final int oldScrollY=getScrollY();
    scrollBy(0,dyUnconsumed);
    final int myConsumed=getScrollY() - oldScrollY;
    if (consumed != null) {
      consumed[1]+=myConsumed;
    }
    final int myUnconsumed=dyUnconsumed - myConsumed;
    mChildHelper.dispatchNestedScroll(0,myConsumed,0,myUnconsumed,null,type,consumed);
  }
  public boolean onStartNestedScroll(  View child,  View target,  int axes,  int type){
    return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
  }
  public void onNestedScrollAccepted(  View child,  View target,  int axes,  int type){
    mParentHelper.onNestedScrollAccepted(child,target,axes,type);
    startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL,type);
  }
  public void onStopNestedScroll(  View target,  int type){
    mParentHelper.onStopNestedScroll(target,type);
    stopNestedScroll(type);
  }
  public void onNestedScroll(  View target,  int dxConsumed,  int dyConsumed,  int dxUnconsumed,  int dyUnconsumed,  int type){
    onNestedScrollInternal(dyUnconsumed,type,null);
  }
  public void onNestedPreScroll(  View target,  int dx,  int dy,  int[] consumed,  int type){
    dispatchNestedPreScroll(dx,dy,consumed,null,type);
  }
  public boolean onStartNestedScroll(  View child,  View target,  int axes){
    return onStartNestedScroll(child,target,axes,ViewCompat.TYPE_TOUCH);
  }
  public void onNestedScrollAccepted(  View child,  View target,  int axes){
    onNestedScrollAccepted(child,target,axes,ViewCompat.TYPE_TOUCH);
  }
  public void onStopNestedScroll(  View target){
    onStopNestedScroll(target,ViewCompat.TYPE_TOUCH);
  }
  public void onNestedScroll(  View target,  int dxConsumed,  int dyConsumed,  int dxUnconsumed,  int dyUnconsumed){
    onNestedScrollInternal(dyUnconsumed,ViewCompat.TYPE_TOUCH,null);
  }
  public void onNestedPreScroll(  View target,  int dx,  int dy,  int[] consumed){
    onNestedPreScroll(target,dx,dy,consumed,ViewCompat.TYPE_TOUCH);
  }
  public boolean onNestedFling(  View target,  float velocityX,  float velocityY,  boolean consumed){
    if (!consumed) {
      dispatchNestedFling(0,velocityY,true);
      fling((int)velocityY);
      return true;
    }
    return false;
  }
  public boolean onNestedPreFling(  View target,  float velocityX,  float velocityY){
    return dispatchNestedPreFling(velocityX,velocityY);
  }
  public int getNestedScrollAxes(){
    return mParentHelper.getNestedScrollAxes();
  }
  public void addView(  View child){
    if (getChildCount() > 0) {
      throw new IllegalStateException("ScrollView can host only one direct child");
    }
    super.addView(child);
  }
  public void addView(  View child,  int index){
    if (getChildCount() > 0) {
      throw new IllegalStateException("ScrollView can host only one direct child");
    }
    super.addView(child,index);
  }
  public void addView(  View child,  ViewGroup.LayoutParams params){
    if (getChildCount() > 0) {
      throw new IllegalStateException("ScrollView can host only one direct child");
    }
    super.addView(child,params);
  }
  public void addView(  View child,  int index,  ViewGroup.LayoutParams params){
    if (getChildCount() > 0) {
      throw new IllegalStateException("ScrollView can host only one direct child");
    }
    super.addView(child,index,params);
  }
  public void setOnScrollChangeListener(  OnScrollChangeListener l){
    mOnScrollChangeListener=l;
  }
  public boolean isFillViewport(){
    return mFillViewport;
  }
  public void setFillViewport(  boolean fillViewport){
    if (fillViewport != mFillViewport) {
      mFillViewport=fillViewport;
      requestLayout();
    }
  }
  protected void onMeasure(  int widthMeasureSpec,  int heightMeasureSpec){
    super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    if (!mFillViewport) {
      return;
    }
    final int heightMode=MeasureSpec.getMode(heightMeasureSpec);
    if (heightMode == MeasureSpec.UNSPECIFIED) {
      return;
    }
    if (getChildCount() > 0) {
      View child=getChildAt(0);
      final LayoutParams lp=(LayoutParams)child.getLayoutParams();
      int childSize=child.getMeasuredHeight();
      int parentSpace=getMeasuredHeight() - getPaddingTop() - getPaddingBottom()- lp.topMargin- lp.bottomMargin;
      if (childSize < parentSpace) {
        int childWidthMeasureSpec=getChildMeasureSpec(widthMeasureSpec,getPaddingLeft() + getPaddingRight() + lp.leftMargin+ lp.rightMargin,lp.width);
        int childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(parentSpace,MeasureSpec.EXACTLY);
        child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
      }
    }
  }
  int getScrollRange(){
    int scrollRange=0;
    if (getChildCount() > 0) {
      View child=getChildAt(0);
      LayoutParams lp=(LayoutParams)child.getLayoutParams();
      int childSize=child.getHeight() + lp.topMargin + lp.bottomMargin;
      int parentSpace=getHeight() - getPaddingTop() - getPaddingBottom();
      scrollRange=Math.max(0,childSize - parentSpace);
    }
    return scrollRange;
  }
  public int computeVerticalScrollRange(){
    final int count=getChildCount();
    final int parentSpace=getHeight() - getPaddingBottom() - getPaddingTop();
    if (count == 0) {
      return parentSpace;
    }
    View child=getChildAt(0);
    LayoutParams lp=(LayoutParams)child.getLayoutParams();
    int scrollRange=child.getBottom() + lp.bottomMargin;
    final int scrollY=getScrollY();
    final int overscrollBottom=Math.max(0,scrollRange - parentSpace);
    if (scrollY < 0) {
      scrollRange-=scrollY;
    }
 else     if (scrollY > overscrollBottom) {
      scrollRange+=scrollY - overscrollBottom;
    }
    return scrollRange;
  }
  public int computeVerticalScrollOffset(){
    return Math.max(0,super.computeVerticalScrollOffset());
  }
  public int computeVerticalScrollExtent(){
    return super.computeVerticalScrollExtent();
  }
  public int computeHorizontalScrollRange(){
    return super.computeHorizontalScrollRange();
  }
  public int computeHorizontalScrollOffset(){
    return super.computeHorizontalScrollOffset();
  }
  public int computeHorizontalScrollExtent(){
    return super.computeHorizontalScrollExtent();
  }
  protected void measureChild(  View child,  int parentWidthMeasureSpec,  int parentHeightMeasureSpec){
    ViewGroup.LayoutParams lp=child.getLayoutParams();
    int childWidthMeasureSpec;
    int childHeightMeasureSpec;
    childWidthMeasureSpec=getChildMeasureSpec(parentWidthMeasureSpec,getPaddingLeft() + getPaddingRight(),lp.width);
    childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
    child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
  }
  protected void measureChildWithMargins(  View child,  int parentWidthMeasureSpec,  int widthUsed,  int parentHeightMeasureSpec,  int heightUsed){
    final MarginLayoutParams lp=(MarginLayoutParams)child.getLayoutParams();
    final int childWidthMeasureSpec=getChildMeasureSpec(parentWidthMeasureSpec,getPaddingLeft() + getPaddingRight() + lp.leftMargin+ lp.rightMargin+ widthUsed,lp.width);
    final int childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(lp.topMargin + lp.bottomMargin,MeasureSpec.UNSPECIFIED);
    child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
  }
  public void requestLayout(){
    mIsLayoutDirty=true;
    super.requestLayout();
  }
  public NestedScrollView(){
    mParentHelper=new NestedScrollingParentHelper(this);
    mChildHelper=new NestedScrollingChildHelper(this);
    setNestedScrollingEnabled(true);
  }
  private void scrollBy(  int i,  int dyUnconsumed){
  }
  private void fling(  int velocityY){
  }
  private int originalRightPadding=0;
  public void adjustPaddingIfScrollBarPresent(  int widthMeasureSpec,  int heightMeasureSpec,  int thumbWidth){
    if (getChildCount() > 0) {
      final View child=getChildAt(0);
      final int widthPadding;
      final int heightPadding;
      final int targetSdkVersion=getContext().getApplicationInfo().targetSdkVersion;
      final FrameLayout.LayoutParams lp=(LayoutParams)child.getLayoutParams();
      if (targetSdkVersion >= r.android.os.Build.VERSION_CODES.M) {
        widthPadding=mPaddingLeft + mPaddingRight + lp.leftMargin+ lp.rightMargin;
        heightPadding=mPaddingTop + mPaddingBottom + lp.topMargin+ lp.bottomMargin;
      }
 else {
        widthPadding=mPaddingLeft + mPaddingRight;
        heightPadding=mPaddingTop + mPaddingBottom;
      }
      final int desiredHeight=getMeasuredHeight() - heightPadding;
      if (desiredHeight >= child.getMeasuredHeight()) {
        if (mPaddingRight == thumbWidth && originalRightPadding != thumbWidth) {
          mPaddingRight=originalRightPadding;
          measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
      }
 else {
        if (mPaddingRight < thumbWidth) {
          originalRightPadding=mPaddingRight;
          mPaddingRight=thumbWidth;
          measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
      }
    }
  }
  public void setOnTouchListener(  OnTouchListener onTouchListener){
  }
  public void setSmoothScrollingEnabled(  boolean objValue){
  }
  public OnScrollChangeListener getOnScrollChangeListener(){
    return mOnScrollChangeListener;
  }
}
