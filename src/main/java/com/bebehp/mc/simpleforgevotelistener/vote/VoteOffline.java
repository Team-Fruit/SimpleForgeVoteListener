package com.bebehp.mc.simpleforgevotelistener.vote;

import com.bebehp.mc.simpleforgevotelistener.ConfigurationHandler;

public class VoteOffline extends AbstractVoteEvent {

	public VoteOffline(final VoteDataIO voteDataIO, final String name) {
		super(voteDataIO, name);
	}

	@Override
	public void onVote() {
		if (ConfigurationHandler.offlineVoteEnable) {

		}
	}

}
