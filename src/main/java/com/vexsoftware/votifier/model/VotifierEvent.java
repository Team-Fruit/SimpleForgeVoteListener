package com.vexsoftware.votifier.model;

import com.vexsoftware.votifier.model.Vote;

import cpw.mods.fml.common.eventhandler.Event;

public class VotifierEvent extends Event {
	private final Vote vote;

	public VotifierEvent(final Vote vote) {
		this.vote = vote;
	}

	public Vote getVote() {
		return this.vote;
	}
}
