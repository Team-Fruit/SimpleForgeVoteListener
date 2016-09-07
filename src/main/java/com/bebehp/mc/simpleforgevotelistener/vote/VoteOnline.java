package com.bebehp.mc.simpleforgevotelistener.vote;

public class VoteOnline extends AbstractVoteEvent {

	public VoteOnline(final VoteDataIO voteDataIO, final String name) {
		super(voteDataIO, name);
	}

	@Override
	public void onVote() {

	}

}
