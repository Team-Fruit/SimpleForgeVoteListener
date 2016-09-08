package com.bebehp.mc.simpleforgevotelistener.setting;

import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Setting {
	public List<Commands> commands;
	public List<GlobalChat> global_chat;
	public List<PrivateChat> private_chat;
	public List<Offline_GlobalChat> offline_global_chat;
	public List<C_PrivateChat> on_cumulative_rewards_private_chat;

	public Setting() {
		this.commands = Collections.emptyList();
		this.global_chat = Collections.emptyList();
		this.private_chat = Collections.emptyList();
		this.offline_global_chat = Collections.emptyList();
		this.on_cumulative_rewards_private_chat = Collections.emptyList();
	}

	public class Commands {
		@SerializedName("string")
		public String str;
		public String args;
		public String option;
	}
	public class GlobalChat {
		@SerializedName("string")
		public String str;
		public String args;
		public String json;
	}
	public class PrivateChat {
		@SerializedName("string")
		public String str;
		public String args;
		public String json;
	}
	public class Offline_GlobalChat {
		@SerializedName("string")
		public String str;
		//		public String args;
		public String json;
	}
	public class C_PrivateChat {
		@SerializedName("string")
		public String str;
		public String args;
		public String json;
	}
}
