package androidx.fragment.app;

import r.android.content.Context;
import r.android.os.Bundle;
import r.android.view.LayoutInflater;
import r.android.view.View;
import r.android.view.ViewGroup;

public class Fragment {

	private Bundle bundle;

	public void onAttach(Context context) {
	}

	public void onCreate(Bundle savedInstanceState) {
	}

	public void onResume() {
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		return null;
	}

	public void onPause() {
	}

	public void onViewCreated(View view, Bundle savedInstanceState) {
	}

	public void onDetach() {
	}

	public void onDestroy() {
	}

	public void onActivityCreated(Bundle savedInstanceState) {
	}
	
    public void setArguments(Bundle bundle) {
    	this.bundle = bundle;
	}
    
    public Bundle getArguments() {
		return this.bundle;
	}

}
