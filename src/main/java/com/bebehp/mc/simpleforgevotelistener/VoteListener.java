package com.bebehp.mc.simpleforgevotelistener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.server.MinecraftServer;

public class VoteListener {

	private final VoteOffline voteOffline;

	public VoteListener(final VoteOffline voteOffline) {
		this.voteOffline = voteOffline;
	}

	private final MinecraftServer mc = MinecraftServer.getServer();

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		final Vote vote = event.getVote();
		final String name = vote.getUsername();
		if (checkOnline(name)) {
			rewards(name);
		} else {
			this.voteOffline.onOfflineVote(name);
		}
	}

	public void rewards(final String name) {
		final int count = 1+this.voteOffline.collectVote(name);

	}

	private boolean checkOnline(final String username) {
		for(final String line : this.mc.getAllUsernames()) {
			if (line == username)
				return true;
		}
		return false;
	}
}
