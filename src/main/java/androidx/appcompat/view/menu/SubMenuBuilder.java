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
