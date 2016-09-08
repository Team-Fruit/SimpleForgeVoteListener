package com.bebehp.mc.simpleforgevotelistener.vote.data;

public class Data {
	private String uuid;
	private String vote;
	private String vote_offline;

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

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	public String getVote() {
		return this.vote;
	}

	public void setVote(final String vote) {
		this.vote = vote;
	}

	public void setVote(final int vote) {
		setVote(Integer.toString(vote));
	}

	public String getVote_offline() {
		return this.vote_offline;
	}

	public void setVote_offline(final String vote_offline) {
		this.vote_offline = vote_offline;
	}

	public void setVote_offline(final int vote_offline) {
		setVote_offline(Integer.toString(vote_offline));
	}

}
