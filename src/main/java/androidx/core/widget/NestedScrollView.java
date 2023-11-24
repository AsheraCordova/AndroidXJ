package androidx.core.widget;

public class NestedScrollView extends r.android.view.View{

	public void setOnTouchListener(OnTouchListener onTouchListener) {
		
	}

	public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
		// TODO Auto-generated method stub
		
	}
	
	public static interface OnScrollChangeListener {
		public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
	}

}
