package com.bebehp.mc.simpleforgevotelistener.vote;

import com.bebehp.mc.simpleforgevotelistener.player.VoterPlayer;

public class DummyVoteEvent extends AbstractVoteEvent {

	private DummyVoteEvent(final VoterPlayer voterPlayer) {
		super(voterPlayer);
	}

	public DummyVoteEvent(final String name) {
		this(new VoterPlayer(name, null));
	}

	@Override
	public void onVote() {
		sendOfflineChat();
	}
}
