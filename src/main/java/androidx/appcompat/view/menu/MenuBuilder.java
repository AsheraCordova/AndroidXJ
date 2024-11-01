package androidx.appcompat.view.menu;
import r.android.content.Context;
import r.android.content.res.Resources;
import r.android.graphics.drawable.Drawable;
import r.android.view.MenuItem;
import r.android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
public class MenuBuilder {
  private static final int[] sCategoryToOrder=new int[]{1,4,5,3,2,0};
  private final Context mContext;
  private final Resources mResources;
  private boolean mQwertyMode;
  private boolean mShortcutsVisible;
  private Callback mCallback;
  private ArrayList<MenuItemImpl> mItems;
  private ArrayList<MenuItemImpl> mVisibleItems;
  private boolean mIsVisibleItemsStale;
  private ArrayList<MenuItemImpl> mActionItems;
  private ArrayList<MenuItemImpl> mNonActionItems;
  private boolean mIsActionItemsStale;
  private int mDefaultShowAsAction=SupportMenuItem.SHOW_AS_ACTION_NEVER;
  CharSequence mHeaderTitle;
  Drawable mHeaderIcon;
  View mHeaderView;
  private boolean mPreventDispatchingItemsChanged=false;
  private boolean mItemsChangedWhileDispatchPrevented=false;
  private boolean mStructureChangedWhileDispatchPrevented=false;
  private boolean mOptionalIconsVisible=false;
  private boolean mIsClosing=false;
  private CopyOnWriteArrayList<WeakReference<MenuPresenter>> mPresenters=new CopyOnWriteArrayList<WeakReference<MenuPresenter>>();
  private MenuItemImpl mExpandedItem;
  private boolean mGroupDividerEnabled=false;
  private boolean mOverrideVisibleItems;
public interface Callback {
    boolean onMenuItemSelected(    MenuBuilder menu,    MenuItem item);
    void onMenuModeChange(    MenuBuilder menu);
  }
  public void addMenuPresenter(  MenuPresenter presenter){
    addMenuPresenter(presenter,mContext);
  }
  public void addMenuPresenter(  MenuPresenter presenter,  Context menuContext){
    mPresenters.add(new WeakReference<MenuPresenter>(presenter));
    presenter.initForMenu(menuContext,this);
    mIsActionItemsStale=true;
  }
  private void dispatchPresenterUpdate(  boolean cleared){
    if (mPresenters.isEmpty())     return;
    stopDispatchingItemsChanged();
    for (    WeakReference<MenuPresenter> ref : mPresenters) {
      final MenuPresenter presenter=ref.get();
      if (presenter == null) {
        mPresenters.remove(ref);
      }
 else {
        presenter.updateMenuView(cleared);
      }
    }
    startDispatchingItemsChanged();
  }
  public void setCallback(  Callback cb){
    mCallback=cb;
  }
  protected MenuItem addInternal(  int group,  int id,  int categoryOrder,  CharSequence title){
    final int ordering=getOrdering(categoryOrder);
    final MenuItemImpl item=createNewMenuItem(group,id,categoryOrder,ordering,title,mDefaultShowAsAction);
    if (false) {
      //item.setMenuInfo(mCurrentMenuInfo);
    }
    mItems.add(findInsertIndex(mItems,ordering),item);
    onItemsChanged(true);
    return item;
  }
  private MenuItemImpl createNewMenuItem(  int group,  int id,  int categoryOrder,  int ordering,  CharSequence title,  int defaultShowAsAction){
    return new MenuItemImpl(this,group,id,categoryOrder,ordering,title,defaultShowAsAction);
  }
  public MenuItem add(  CharSequence title){
    return addInternal(0,0,0,title);
  }
  public MenuItem add(  int titleRes){
    return addInternal(0,0,0,mResources.getString(titleRes));
  }
  public MenuItem add(  int group,  int id,  int categoryOrder,  CharSequence title){
    return addInternal(group,id,categoryOrder,title);
  }
  public MenuItem add(  int group,  int id,  int categoryOrder,  int title){
    return addInternal(group,id,categoryOrder,mResources.getString(title));
  }
  public void clearAll(){
    mPreventDispatchingItemsChanged=true;
    clear();
    clearHeader();
    mPresenters.clear();
    mPreventDispatchingItemsChanged=false;
    mItemsChangedWhileDispatchPrevented=false;
    mStructureChangedWhileDispatchPrevented=false;
    onItemsChanged(true);
  }
  public void clear(){
    if (mExpandedItem != null) {
      collapseItemActionView(mExpandedItem);
    }
    mItems.clear();
    onItemsChanged(true);
  }
  void setExclusiveItemChecked(  MenuItem item){
    final int group=item.getGroupId();
    final int N=mItems.size();
    stopDispatchingItemsChanged();
    for (int i=0; i < N; i++) {
      MenuItemImpl curItem=mItems.get(i);
      if (curItem.getGroupId() == group) {
        if (!curItem.isExclusiveCheckable())         continue;
        if (!curItem.isCheckable())         continue;
        curItem.setCheckedInt(curItem == item);
      }
    }
    startDispatchingItemsChanged();
  }
  public boolean hasVisibleItems(){
    if (mOverrideVisibleItems) {
      return true;
    }
    final int size=size();
    for (int i=0; i < size; i++) {
      MenuItemImpl item=mItems.get(i);
      if (item.isVisible()) {
        return true;
      }
    }
    return false;
  }
  public MenuItem findItem(  int id){
    final int size=size();
    for (int i=0; i < size; i++) {
      MenuItemImpl item=mItems.get(i);
      if (item.getItemId() == id) {
        return item;
      }
 else       if (item.hasSubMenu()) {
        MenuItem possibleItem=item.getSubMenu().findItem(id);
        if (possibleItem != null) {
          return possibleItem;
        }
      }
    }
    return null;
  }
  public int size(){
    return mItems.size();
  }
  public MenuItem getItem(  int index){
    return mItems.get(index);
  }
  private static int getOrdering(  int categoryOrder){
    final int index=(categoryOrder & CATEGORY_MASK) >> CATEGORY_SHIFT;
    if (index < 0 || index >= sCategoryToOrder.length) {
      throw new IllegalArgumentException("order does not contain a valid category.");
    }
    return (sCategoryToOrder[index] << CATEGORY_SHIFT) | (categoryOrder & USER_MASK);
  }
  boolean dispatchMenuItemSelected(  MenuBuilder menu,  MenuItem item){
    return mCallback != null && mCallback.onMenuItemSelected(menu,item);
  }
  private static int findInsertIndex(  ArrayList<MenuItemImpl> items,  int ordering){
    for (int i=items.size() - 1; i >= 0; i--) {
      MenuItemImpl item=items.get(i);
      if (item.getOrdering() <= ordering) {
        return i + 1;
      }
    }
    return 0;
  }
  public void onItemsChanged(  boolean structureChanged){
    if (!mPreventDispatchingItemsChanged) {
      if (structureChanged) {
        mIsVisibleItemsStale=true;
        mIsActionItemsStale=true;
      }
      dispatchPresenterUpdate(structureChanged);
    }
 else {
      mItemsChangedWhileDispatchPrevented=true;
      if (structureChanged) {
        mStructureChangedWhileDispatchPrevented=true;
      }
    }
  }
  public void stopDispatchingItemsChanged(){
    if (!mPreventDispatchingItemsChanged) {
      mPreventDispatchingItemsChanged=true;
      mItemsChangedWhileDispatchPrevented=false;
      mStructureChangedWhileDispatchPrevented=false;
    }
  }
  public void startDispatchingItemsChanged(){
    mPreventDispatchingItemsChanged=false;
    if (mItemsChangedWhileDispatchPrevented) {
      mItemsChangedWhileDispatchPrevented=false;
      onItemsChanged(mStructureChangedWhileDispatchPrevented);
    }
  }
  void onItemVisibleChanged(  MenuItemImpl item){
    mIsVisibleItemsStale=true;
    onItemsChanged(true);
  }
  void onItemActionRequestChanged(  MenuItemImpl item){
    mIsActionItemsStale=true;
    onItemsChanged(true);
  }
  public ArrayList<MenuItemImpl> getVisibleItems(){
    if (!mIsVisibleItemsStale)     return mVisibleItems;
    mVisibleItems.clear();
    final int itemsSize=mItems.size();
    MenuItemImpl item;
    for (int i=0; i < itemsSize; i++) {
      item=mItems.get(i);
      if (item.isVisible())       mVisibleItems.add(item);
    }
    mIsVisibleItemsStale=false;
    mIsActionItemsStale=true;
    return mVisibleItems;
  }
  public void flagActionItems(){
    final ArrayList<MenuItemImpl> visibleItems=getVisibleItems();
    if (!mIsActionItemsStale) {
      return;
    }
    boolean flagged=false;
    for (    WeakReference<MenuPresenter> ref : mPresenters) {
      final MenuPresenter presenter=ref.get();
      if (presenter == null) {
        mPresenters.remove(ref);
      }
 else {
        flagged|=presenter.flagActionItems();
      }
    }
    if (flagged) {
      mActionItems.clear();
      mNonActionItems.clear();
      final int itemsSize=visibleItems.size();
      for (int i=0; i < itemsSize; i++) {
        MenuItemImpl item=visibleItems.get(i);
        if (item.isActionButton()) {
          mActionItems.add(item);
        }
 else {
          mNonActionItems.add(item);
        }
      }
    }
 else {
      mActionItems.clear();
      mNonActionItems.clear();
      mNonActionItems.addAll(getVisibleItems());
    }
    mIsActionItemsStale=false;
  }
  public ArrayList<MenuItemImpl> getActionItems(){
    flagActionItems();
    return mActionItems;
  }
  public ArrayList<MenuItemImpl> getNonActionItems(){
    flagActionItems();
    return mNonActionItems;
  }
  public void clearHeader(){
    mHeaderIcon=null;
    mHeaderTitle=null;
    mHeaderView=null;
    onItemsChanged(false);
  }
  private void setHeaderInternal(  final int titleRes,  final CharSequence title,  final int iconRes,  final Drawable icon,  final View view){
    //final Resources r=getResources();
    if (view != null) {
      mHeaderView=view;
      mHeaderTitle=null;
      mHeaderIcon=null;
    }
 else {
      if (titleRes > 0) {
        //mHeaderTitle=r.getText(titleRes);
      }
 else       if (title != null) {
        mHeaderTitle=title;
      }
      if (iconRes > 0) {
        //mHeaderIcon=ContextCompat.getDrawable(getContext(),iconRes);
      }
 else       if (icon != null) {
        mHeaderIcon=icon;
      }
      mHeaderView=null;
    }
    onItemsChanged(false);
  }
  protected MenuBuilder setHeaderTitleInt(  CharSequence title){
    setHeaderInternal(0,title,0,null,null);
    return this;
  }
  protected MenuBuilder setHeaderTitleInt(  int titleRes){
    setHeaderInternal(titleRes,null,0,null,null);
    return this;
  }
  final static int USER_MASK=65535;
  final static int USER_SHIFT=0;
  final static int CATEGORY_MASK=-65536;
  final static int CATEGORY_SHIFT=16;
  final static int SUPPORTED_MODIFIERS_MASK=69647;
  final static int FLAG_KEEP_OPEN_ON_SUBMENU_OPENED=4;
  public MenuBuilder(){
    mResources=null;
    mItems=new ArrayList<MenuItemImpl>();
    mVisibleItems=new ArrayList<MenuItemImpl>();
    mActionItems=new ArrayList<MenuItemImpl>();
    mNonActionItems=new ArrayList<MenuItemImpl>();
    this.mIsVisibleItemsStale=true;
    mContext=null;
  }
  private void collapseItemActionView(  MenuItem mExpandedItem){
  }
  public void setActionMenuPresenter(  MenuPresenter mPresenter){
    mPresenters.add(new WeakReference<MenuPresenter>(mPresenter));
  }
interface SupportMenuItem extends MenuItem {
  }
class ContextMenu {
  }
  public boolean performItemAction(  MenuItem item,  MenuPresenter presenter,  int i){
    MenuItemImpl itemImpl=(MenuItemImpl)item;
    if (itemImpl == null || !itemImpl.isEnabled()) {
      return false;
    }
    boolean invoked=itemImpl.invoke();
    return invoked;
  }
}
