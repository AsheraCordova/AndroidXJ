package androidx.fragment.app;

import java.util.ArrayList;
import java.util.List;

public class FragmentManager {
	private List<FragmentTransaction> transactions = new ArrayList<>();
	public List<FragmentTransaction> getTransactions() {
		return transactions;
	}
	public FragmentTransaction beginTransaction() {
		FragmentTransaction fragmentTransaction = new FragmentTransaction();
		transactions.add(fragmentTransaction);
		return fragmentTransaction;
	}

}
