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
