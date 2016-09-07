package com.bebehp.mc.simpleforgevotelistener.vote;

import com.bebehp.mc.simpleforgevotelistener.ConfigurationHandler;

public class VoteOffline extends AbstractVoteEvent {

	public VoteOffline(final String name) {
		super(name);
	}

	@Override
	public void onVote() {
		super.onVote();
		if (ConfigurationHandler.offlineVoteEnable) {

		}
	}

}
