package androidx.core.view;

import r.android.view.ViewGroup.MarginLayoutParams;

public class MarginLayoutParamsCompat {

    public static void setMarginStart(MarginLayoutParams params, int start) {
        params.setMarginStart(start);
    }

    public static void setMarginEnd(MarginLayoutParams params, int end) {
        params.setMarginEnd(end);
    }

    public static int getMarginStart(MarginLayoutParams params) {
        return params.getMarginStart();
    }

    public static int getMarginEnd(MarginLayoutParams params) {
        return params.getMarginEnd();
    }

}
