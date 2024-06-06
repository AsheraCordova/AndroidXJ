package androidx.fragment.app;

import java.util.List;

import com.ashera.core.IFragmentContainer;

public class FragmentTransaction {
	public static class Ops {
		public IFragmentContainer fragmentContainer;
		public String layout;
		public String navGraphId;
		public String tag;
		public String name;
		public int type;
		public Ops(int type, IFragmentContainer fragmentContainer, String name, String layout, String navGraphId, String tag) {
			super();
			this.type = type;
			this.fragmentContainer = fragmentContainer;
			this.navGraphId = navGraphId;
			this.layout = layout;
			this.tag = tag;
		}
		
	}
	private List<Ops> operations = new java.util.ArrayList<>();
	public List<Ops> getOperations() {
		return operations;
	}

	public void add(IFragmentContainer fragmentContainer, String name, String layout, String navGraphId, String tag) {
		operations.add(new Ops(0, fragmentContainer, name, layout, navGraphId, tag));
	}

	public void replace(IFragmentContainer fragmentContainer, String name, String layout, String navGraphId, String tag) {
		operations.add(new Ops(1, fragmentContainer, name, layout, navGraphId, tag));
	}

	public void commit() {
		
	}

}
