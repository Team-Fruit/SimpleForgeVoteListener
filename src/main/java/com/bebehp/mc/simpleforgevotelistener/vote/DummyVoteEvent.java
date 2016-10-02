package com.bebehp.mc.simpleforgevotelistener.vote;

import java.util.UUID;

import com.bebehp.mc.simpleforgevotelistener.player.VoteDataIO;

public class DummyVoteEvent extends AbstractVoteEvent {

	private DummyVoteEvent(final VoteDataIO voteDataIO, final String name, final UUID uuid) {
		super(voteDataIO, name, uuid);
	}

	public DummyVoteEvent(final String name) {
		this(null, name, null);
	}

	@Override
	public void onVote() {
		sendOfflineChat();
	}
}
