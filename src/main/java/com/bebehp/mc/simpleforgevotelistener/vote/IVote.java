package com.bebehp.mc.simpleforgevotelistener.vote;

public interface IVote {

	void onVote(String name);

	abstract String parse(String name, String raw, String args);
}
