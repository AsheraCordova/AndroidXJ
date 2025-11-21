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

import r.android.graphics.Rect;
import r.android.view.Gravity;

public class GravityCompat {
   /** Raw bit controlling whether the layout direction is relative or not (START/END instead of
     * absolute LEFT/RIGHT).
     */
    public static final int RELATIVE_LAYOUT_DIRECTION = 0x00800000;
   /** Push object to x-axis position at the start of its container, not changing its size. */
    public static final int START = RELATIVE_LAYOUT_DIRECTION | Gravity.LEFT;

   /** Push object to x-axis position at the end of its container, not changing its size. */
    public static final int END = RELATIVE_LAYOUT_DIRECTION | Gravity.RIGHT;

   /**
     * Binary mask for the horizontal gravity and script specific direction bit.
     */
    public static final int RELATIVE_HORIZONTAL_GRAVITY_MASK = START | END;

	public static int getAbsoluteGravity(int gravity, int layoutDirection) {
		return Gravity.getAbsoluteGravity(gravity, layoutDirection);
	}

	public static void apply(int resolveGravity, int measuredWidth,
			int measuredHeight, Rect parent, Rect out, int layoutDirection) {
		Gravity.apply(resolveGravity, measuredWidth, measuredHeight, parent, out, layoutDirection);
	}
}
