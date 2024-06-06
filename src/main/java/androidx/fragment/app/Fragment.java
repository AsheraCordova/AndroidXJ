package androidx.fragment.app;

import java.util.List;

import com.ashera.core.IFragment;
import com.ashera.widget.IWidget;

import androidx.fragment.app.FragmentTransaction.Ops;
import r.android.content.Context;
import r.android.os.Bundle;
import r.android.view.LayoutInflater;
import r.android.view.View;
import r.android.view.ViewGroup;

public class Fragment {
	private FragmentManager childFragmentManager;
	private Bundle bundle;
	private IFragment parentFragment;
	private List<Fragment> childFragments;
	private boolean paused = false;

	public void setParentFragment(IFragment parentFragment) {
		setParentFragment(parentFragment, true);
	}
	
	public void setParentFragment(IFragment parentFragment, boolean manageLifeCycle) {
		this.parentFragment = parentFragment;
		
		if (manageLifeCycle) {
			if (((Fragment)parentFragment).childFragments == null) {
				((Fragment)parentFragment).childFragments = new java.util.ArrayList<>();
			}
			
			((Fragment)parentFragment).childFragments.add(this);
		}
	}

	public void onAttach(Context context) {
	}

	public void onCreate(Bundle savedInstanceState) {
	}

	public void onResume() {
		paused = false;
		
		if (childFragments != null) {
			
			
			for (Fragment fragment : childFragments) {
				IWidget rootWidget = ((IFragment) fragment).getRootWidget();
				com.ashera.widget.IWidget widget = ((com.ashera.core.IFragmentContainer) rootWidget.getParent()).getActiveRootWidget();
				if (rootWidget == widget) {
					fragment.onResume();
				}
			}
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		return null;
	}
	public void onPause() {
		paused = true;
		if (childFragments != null) {
			for (Fragment fragment : childFragments) {
				if (!fragment.paused) {
					fragment.onPause();
				}
			}
		}
	}

	public void onViewCreated(View view, Bundle savedInstanceState) {
	}

	public void onDetach() {
		if (childFragments != null) {
			List<Fragment> childFragmentsClone = new java.util.ArrayList<>(childFragments);
			for (Fragment fragment : childFragmentsClone) {
				fragment.onDetach();
			}			
		}

		if (parentFragment != null && ((Fragment)parentFragment).childFragments != null) {
			((Fragment)parentFragment).childFragments.remove(this);
		}
	}

	public void onDestroy() {
		if (childFragments != null) {
			for (Fragment fragment : childFragments) {
				fragment.onDestroy();
			}
		}
	}

	public void onActivityCreated(Bundle savedInstanceState) {
	}
	
    public void setArguments(Bundle bundle) {
    	this.bundle = bundle;
	}
    
    public Bundle getArguments() {
		return this.bundle;
	}

	public FragmentManager getChildFragmentManager() {
		if (childFragmentManager == null) {
			childFragmentManager = new FragmentManager();
		}
		
		return childFragmentManager;
	}

	public Fragment getParentFragment() {
		return (Fragment) this.parentFragment;
	}

	public void executePendingTransactions() {
		if (childFragmentManager != null) {
			List<FragmentTransaction> transactions = childFragmentManager.getTransactions();
			for (FragmentTransaction fragmentTransaction : transactions) {
				for (Ops ops : fragmentTransaction.getOperations()) {
					switch (ops.type) {
					case 0:
						ops.fragmentContainer.addOrReplaceFragment(ops.name, true, ops.layout, ops.navGraphId, ops.tag);
						break;
					case 1:
						ops.fragmentContainer.addOrReplaceFragment(ops.name, false, ops.layout, ops.navGraphId, ops.tag);
						break;
					}
				}
			}
			transactions.clear();
		}
	}

}
