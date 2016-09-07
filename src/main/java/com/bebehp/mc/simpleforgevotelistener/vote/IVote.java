package com.bebehp.mc.simpleforgevotelistener.vote;

public interface IVote {

	void onVote(String name);

	String parseArgs(String name, String raw, String args);
}
