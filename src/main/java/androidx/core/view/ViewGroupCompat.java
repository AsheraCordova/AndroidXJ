package androidx.core.view;

import r.android.view.ViewGroup;

public class ViewGroupCompat {

	public static int getLayoutMode(ViewGroup view) {
		return view.getLayoutDirection();
	}

}
