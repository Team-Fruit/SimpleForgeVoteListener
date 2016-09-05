package com.bebehp.mc.simpleforgevotelistener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.server.MinecraftServer;

public class VoteListener {
	public static final VoteListener INSTANCE = new VoteListener();

	private VoteListener() {
	}

	private final MinecraftServer mc = MinecraftServer.getServer();

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		final Vote vote = event.getVote();
		final String name = vote.getUsername();
	}

	private boolean checkOnline(final String username) {
		for(final String line : this.mc.getAllUsernames()) {
			if (line == username)
				return true;
		}
		return false;
	}
}