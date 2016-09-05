package com.bebehp.mc.simpleforgevotelistener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class VoteListener {
	public static final VoteListener INSTANCE = new VoteListener();

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		final Vote vote = event.getVote();

	}
}
