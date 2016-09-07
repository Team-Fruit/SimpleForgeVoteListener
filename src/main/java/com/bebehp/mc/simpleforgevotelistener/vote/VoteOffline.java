package com.bebehp.mc.simpleforgevotelistener.vote;

import com.bebehp.mc.simpleforgevotelistener.ConfigurationHandler;

public class VoteOffline extends AbstractVoteEvent {


	public VoteOffline() {
		super();
	}

	@Override
	public void onVote(final String name) {
		if (ConfigurationHandler.offlineVoteEnable) {

		}
	}

}
