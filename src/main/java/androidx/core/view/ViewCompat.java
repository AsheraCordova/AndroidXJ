package androidx.core.view;

import r.android.view.View;
import r.android.view.ViewGroup;

public class ViewCompat {

	public static final int MEASURED_STATE_MASK = View.MEASURED_STATE_MASK;
	public static final int MEASURED_STATE_TOO_SMALL = View.MEASURED_STATE_TOO_SMALL;
	public static final int LAYOUT_DIRECTION_RTL = View.LAYOUT_DIRECTION_RTL;
	public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
	public static final int MEASURED_SIZE_MASK = 0x00ffffff;
	public static final int LAYOUT_DIRECTION_LTR = View.LAYOUT_DIRECTION_LTR;
	public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
	public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 0;

	public static int getMeasuredWidthAndState(View view) {
		return view.getMeasuredWidth();
	}

	public static int getMeasuredHeightAndState(View view) {
		return view.getMeasuredHeight();
	}

	public static int getLayoutDirection(View view) {
		return view.getLayoutDirection();
	}

	public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {		
		return View.resolveSizeAndState(size, measureSpec, childMeasuredState);
	}

	public static int combineMeasuredStates(int curState, int newState) {
		return View.combineMeasuredStates(curState, newState);
	}

	public static int getMeasuredState(View child) {
		return child.getMeasuredState();
	}

	public static int getPaddingStart(ViewGroup viewGroup) {
		return viewGroup.getPaddingStart();
	}

	public static int getPaddingEnd(ViewGroup viewGroup) {
		return viewGroup.getPaddingEnd();
	}

	public static void setLayoutDirection(ViewGroup viewGroup,
			int layoutDirection) {
		viewGroup.setLayoutDirection(layoutDirection);
	}

	public static void setPaddingRelative(View view, int start, int top, int end, int bottom) {
		view.setPaddingRelative(start, top, end, bottom);;
		
	}

	public static int getPaddingStart(View view) {
		return view.getPaddingStart();
	}

	public static int getPaddingEnd(View view) {
		return view.getPaddingEnd();
	}

	public static void setX(View child, int x) {
	}

	public static void setY(View child, int y) {
		
	}

	public static int getMinimumHeight(View view) {
		return view.getMinimumHeight();
	}

	public static void jumpDrawablesToCurrentState(View itemView) {
	}

	public static boolean getFitsSystemWindows(
			View view) {
		return false;
	}

	public static void setElevation(View child, float mDrawerElevation) {
		child.setElevation(mDrawerElevation);
	}

	public static float getElevation(View child) {
		return child.getElevation();
	}

	public static void dispatchApplyWindowInsets(View child, Object wi) {
		
	}

	public static Object getRootWindowInsets(Object drawerLayout) {
		return null;
	}

	public static int getMinimumWidth(View view) {
		return view.getMinimumWidth();
	}

	public static int getImportantForAccessibility(View itemView) {
		return 0;
	}

	public static void setImportantForAccessibility(View itemView, int importantForAccessibilityYes) {
		
	}

	public static void dispatchFinishTemporaryDetach(View child) {
		
	}

	public static void dispatchStartTemporaryDetach(View view) {
		
	}

	public static boolean hasTransientState(View view) {
		return view.hasTransientState();
	}

	public static boolean isLayoutDirectionResolved(View view) {
		return view.isLayoutDirectionResolved();
	}

}
