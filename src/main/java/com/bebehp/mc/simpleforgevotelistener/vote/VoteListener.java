package com.bebehp.mc.simpleforgevotelistener.vote;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.server.MinecraftServer;

public class VoteListener {
	public static final VoteListener INSTANCE = new VoteListener();

	private VoteListener() {
	}

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		final Vote vote = event.getVote();
		final String name = vote.getUsername();
		if (checkOnline(name)) {
			final VoteOnline online = new VoteOnline(name);
		} else {
			final VoteOffline offline = new VoteOffline(name);
		}
	}

	public boolean checkOnline(final String username) {
		for(final String line :  MinecraftServer.getServer().getAllUsernames()) {
			if (line == username)
				return true;
		}
		return false;
	}
}
