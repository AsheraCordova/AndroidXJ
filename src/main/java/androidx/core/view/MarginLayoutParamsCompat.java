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
