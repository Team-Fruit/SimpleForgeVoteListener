package com.bebehp.mc.simpleforgevotelistener.setting;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Setting {
	public List<Commands> commands;
	public List<GlobalChat> global_chat;
	public List<PrivateChat> private_chat;
	public List<Offline_GlobalChat> offline_global_chat;
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
		public String args;
		public String json;
	}
}