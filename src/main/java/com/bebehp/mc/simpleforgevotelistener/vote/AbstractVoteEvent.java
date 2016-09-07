package com.bebehp.mc.simpleforgevotelistener.vote;

public abstract class AbstractVoteEvent implements IVote {

	protected String name;
	protected Data data;

	public AbstractVoteEvent(final VoteDataIO voteDataIO, final String name) {
		this.name = name;
		this.data = voteDataIO.load();
	}

}
