package com.bebehp.mc.simpleforgevotelistener.vote;

public class Data {
	public String uuid;
	public String name;
	public String vote;
	public String vote_offline;

	public Data(final String uuid, final String name, final String vote, final String vote_offline) {
		this.uuid = uuid;
		this.name = name;
		this.vote = vote;
		this.vote_offline = vote_offline;
	}

	public Data(final String uuid, final String name, final int vote, final int vote_offline) {
		this(uuid, name, Integer.toString(vote), Integer.toString(vote_offline));
	}
}
