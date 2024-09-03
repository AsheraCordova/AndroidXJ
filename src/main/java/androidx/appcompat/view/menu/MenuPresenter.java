package androidx.appcompat.view.menu;

import r.android.content.Context;

public interface MenuPresenter {

	boolean flagActionItems();
	void initForMenu(Context menuContext, MenuBuilder menuBuilder);
	void updateMenuView(boolean cleared);

}
