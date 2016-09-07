package com.bebehp.mc.simpleforgevotelistener.vote;

public class Data {
	public String uuid;
	public String vote;
	public String vote_offline;

	public Data(final String uuid, final String vote, final String vote_offline) {
		this.uuid = uuid;
		this.vote = vote;
		this.vote_offline = vote_offline;
	}

	public Data(final String uuid,  final int vote, final int vote_offline) {
		this(uuid, Integer.toString(vote), Integer.toString(vote_offline));
	}

	public Data() {
		this(null, "0", "0");
	}
}
