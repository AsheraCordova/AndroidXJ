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
 * Copyright (C) 2006 The Android Open Source Project
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
package androidx.appcompat.view.menu;
import r.android.content.Context;
import r.android.graphics.drawable.Drawable;
import r.android.view.Menu;
import r.android.view.MenuItem;
import r.android.view.SubMenu;
public class SubMenuBuilder extends MenuBuilder implements SubMenu {
  private MenuBuilder mParentMenu;
  private MenuItemImpl mItem;
  public SubMenuBuilder(  Context context,  MenuBuilder parentMenu,  MenuItemImpl item){
    //super(context);
    mParentMenu=parentMenu;
    mItem=item;
  }
  public MenuItem getItem(){
    return mItem;
  }
  public void setCallback(  Callback callback){
    mParentMenu.setCallback(callback);
  }
  boolean dispatchMenuItemSelected(  MenuBuilder menu,  MenuItem item){
    return super.dispatchMenuItemSelected(menu,item) || mParentMenu.dispatchMenuItemSelected(menu,item);
  }
  public SubMenu setHeaderTitle(  CharSequence title){
    return (SubMenu)super.setHeaderTitleInt(title);
  }
  public SubMenu setHeaderTitle(  int titleRes){
    return (SubMenu)super.setHeaderTitleInt(titleRes);
  }
}
