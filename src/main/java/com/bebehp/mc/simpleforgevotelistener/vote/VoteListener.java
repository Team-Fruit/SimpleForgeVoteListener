package com.bebehp.mc.simpleforgevotelistener.vote;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.server.MinecraftServer;

public class VoteListener {

	private final VoteOnline voteOnline;
	private final VoteOffline voteOffline;

	public VoteListener(final VoteOnline voteOline, final VoteOffline voteOffline) {
		this.voteOnline = voteOline;
		this.voteOffline = voteOffline;
	}

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		final Vote vote = event.getVote();
		final String name = vote.getUsername();
		if (checkOnline(name)) {
			this.voteOnline.onVote(name);
		} else {
			this.voteOffline.onVote(name);
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
