package androidx.appcompat.view.menu;
import r.android.content.res.ColorStateList;
import r.android.graphics.drawable.Drawable;
import r.android.view.MenuItem;
import r.android.view.View;
import r.android.widget.LinearLayout;
public final class MenuItemImpl implements SupportMenuItem {
  private static final int SHOW_AS_ACTION_MASK=SHOW_AS_ACTION_NEVER | SHOW_AS_ACTION_IF_ROOM | SHOW_AS_ACTION_ALWAYS;
  private final int mId;
  private final int mGroup;
  private final int mCategoryOrder;
  private final int mOrdering;
  private CharSequence mTitle;
  private Drawable mIconDrawable;
  private int mIconResId=NO_ICON;
  MenuBuilder mMenu;
  private SubMenuBuilder mSubMenu;
  private CharSequence mTooltipText;
  private ColorStateList mIconTintList=null;
  private boolean mHasIconTint=false;
  private boolean mHasIconTintMode=false;
  private boolean mNeedToApplyIconTint=false;
  private int mFlags=ENABLED;
  private static final int CHECKABLE=0x00000001;
  private static final int CHECKED=0x00000002;
  private static final int EXCLUSIVE=0x00000004;
  private static final int HIDDEN=0x00000008;
  private static final int ENABLED=0x00000010;
  private static final int IS_ACTION=0x00000020;
  private int mShowAsAction=SHOW_AS_ACTION_NEVER;
  private boolean mIsActionViewExpanded=false;
  static final int NO_ICON=0;
  MenuItemImpl(  MenuBuilder menu,  int group,  int id,  int categoryOrder,  int ordering,  CharSequence title,  int showAsAction){
    mMenu=menu;
    mId=id;
    mGroup=group;
    mCategoryOrder=categoryOrder;
    mOrdering=ordering;
    mTitle=title;
    mShowAsAction=showAsAction;
  }
  public boolean isEnabled(){
    return (mFlags & ENABLED) != 0;
  }
  public MenuItem setEnabled(  boolean enabled){
    if (enabled) {
      mFlags|=ENABLED;
    }
 else {
      mFlags&=~ENABLED;
    }
    mMenu.onItemsChanged(false);
    return this;
  }
  public int getGroupId(){
    return mGroup;
  }
  public int getItemId(){
    return mId;
  }
  public int getOrdering(){
    return mOrdering;
  }
  public SubMenuBuilder getSubMenu(){
    return mSubMenu;
  }
  public boolean hasSubMenu(){
    return mSubMenu != null;
  }
  public void setSubMenu(  SubMenuBuilder subMenu){
    mSubMenu=subMenu;
    subMenu.setHeaderTitle(getTitle());
  }
  public MenuItem setIcon(  Drawable icon){
    mIconResId=NO_ICON;
    mIconDrawable=icon;
    mNeedToApplyIconTint=true;
    mMenu.onItemsChanged(false);
    return this;
  }
  public MenuItem setIcon(  int iconResId){
    mIconDrawable=null;
    mIconResId=iconResId;
    mNeedToApplyIconTint=true;
    mMenu.onItemsChanged(false);
    return this;
  }
  public MenuItem setIconTintList(  ColorStateList iconTintList){
    mIconTintList=iconTintList;
    mHasIconTint=true;
    mNeedToApplyIconTint=true;
    mMenu.onItemsChanged(false);
    return this;
  }
  public boolean isCheckable(){
    return (mFlags & CHECKABLE) == CHECKABLE;
  }
  public MenuItem setCheckable(  boolean checkable){
    final int oldFlags=mFlags;
    mFlags=(mFlags & ~CHECKABLE) | (checkable ? CHECKABLE : 0);
    if (oldFlags != mFlags) {
      mMenu.onItemsChanged(false);
    }
    return this;
  }
  public void setExclusiveCheckable(  boolean exclusive){
    mFlags=(mFlags & ~EXCLUSIVE) | (exclusive ? EXCLUSIVE : 0);
  }
  public boolean isExclusiveCheckable(){
    return (mFlags & EXCLUSIVE) != 0;
  }
  public boolean isChecked(){
    return (mFlags & CHECKED) == CHECKED;
  }
  public MenuItem setChecked(  boolean checked){
    if ((mFlags & EXCLUSIVE) != 0) {
      mMenu.setExclusiveItemChecked(this);
    }
 else {
      setCheckedInt(checked);
    }
    return this;
  }
  void setCheckedInt(  boolean checked){
    final int oldFlags=mFlags;
    mFlags=(mFlags & ~CHECKED) | (checked ? CHECKED : 0);
    if (oldFlags != mFlags) {
      mMenu.onItemsChanged(false);
    }
  }
  public boolean isVisible(){
    if (mActionProvider != null && mActionProvider.overridesItemVisibility()) {
      return (mFlags & HIDDEN) == 0 && mActionProvider.isVisible();
    }
    return (mFlags & HIDDEN) == 0;
  }
  boolean setVisibleInt(  boolean shown){
    final int oldFlags=mFlags;
    mFlags=(mFlags & ~HIDDEN) | (shown ? 0 : HIDDEN);
    return oldFlags != mFlags;
  }
  public MenuItem setVisible(  boolean shown){
    if (setVisibleInt(shown))     mMenu.onItemVisibleChanged(this);
    return this;
  }
  public boolean isActionButton(){
    return (mFlags & IS_ACTION) == IS_ACTION;
  }
  public boolean requestsActionButton(){
    return (mShowAsAction & SHOW_AS_ACTION_IF_ROOM) == SHOW_AS_ACTION_IF_ROOM;
  }
  public boolean requiresActionButton(){
    return (mShowAsAction & SHOW_AS_ACTION_ALWAYS) == SHOW_AS_ACTION_ALWAYS;
  }
  public void setIsActionButton(  boolean isActionButton){
    if (isActionButton) {
      mFlags|=IS_ACTION;
    }
 else {
      mFlags&=~IS_ACTION;
    }
  }
  public void setShowAsAction(  int actionEnum){
switch (actionEnum & SHOW_AS_ACTION_MASK) {
case SHOW_AS_ACTION_ALWAYS:
case SHOW_AS_ACTION_IF_ROOM:
case SHOW_AS_ACTION_NEVER:
      break;
default :
    throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM," + " and SHOW_AS_ACTION_NEVER are mutually exclusive.");
}
mShowAsAction=actionEnum;
mMenu.onItemActionRequestChanged(this);
}
public SupportMenuItem setActionView(View view){
mActionView=view;
mActionProvider=null;
if (view != null && view.getId() == View.NO_ID && mId > 0) {
  view.setId(mId);
}
mMenu.onItemActionRequestChanged(this);
return this;
}
public View getActionView(){
if (mActionView != null) {
  return mActionView;
}
 else if (mActionProvider != null) {
  mActionView=mActionProvider.onCreateActionView(this);
  return mActionView;
}
 else {
  return null;
}
}
public boolean hasCollapsibleActionView(){
if ((mShowAsAction & SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW) != 0) {
  if (mActionView == null && mActionProvider != null) {
    mActionView=mActionProvider.onCreateActionView(this);
  }
  return mActionView != null;
}
return false;
}
public boolean isActionViewExpanded(){
return mIsActionViewExpanded;
}
public CharSequence getTooltipText(){
return mTooltipText;
}
private r.android.view.View mActionView;
private ActionProvider mActionProvider;
public String getTitle(){
return (String)mTitle;
}
public Drawable getIcon(){
return mIconDrawable;
}
interface ActionProvider {
boolean overridesItemVisibility();
View onCreateActionView(MenuItemImpl menuItemImpl);
boolean isVisible();
}
public boolean invoke(){
if (mMenu.dispatchMenuItemSelected(mMenu,this)) {
  return true;
}
return false;
}
}
