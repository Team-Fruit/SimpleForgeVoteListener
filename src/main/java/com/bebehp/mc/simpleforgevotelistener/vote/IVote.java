package com.bebehp.mc.simpleforgevotelistener.vote;

public interface IVote {

	void onVote(String name);

	String parse(String name, String raw, String args);
}
