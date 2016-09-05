package com.bebehp.mc.simpleforgevotelistener.handler;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class VoteJson {
	public List<Commands> commands;
	public List<GlobalChat> global_chat;
	public List<PrivateChat> private_chat;
	public class Commands {
		public String command;
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
		public String json;
	}
}
