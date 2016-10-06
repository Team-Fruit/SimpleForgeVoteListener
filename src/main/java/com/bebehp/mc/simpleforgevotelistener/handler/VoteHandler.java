package com.bebehp.mc.simpleforgevotelistener.handler;

import java.util.UUID;

import com.bebehp.mc.simpleforgevotelistener.player.UUIDUtil;
import com.bebehp.mc.simpleforgevotelistener.vote.RewardType;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class VoteHandler {
	public static final VoteHandler INSTANCE = new VoteHandler();

	private VoteHandler() {
	}

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		final Vote vote = event.getVote();
		final String name = vote.getUsername();
		RewardType type;

		UUID uuid = UUIDUtil.getUUIDfromUsername(name);
		if (uuid != null) {
			type = RewardType.ONLINE;
		} else {
			uuid = UUIDUtil.getUUIDfromUsernameCache(name);
			if (uuid == null)
				uuid = UUIDUtil.getUUIDfromMojangAPI(name);
			if (uuid != null)
				type = RewardType.OFFLINE;
			else
				type = RewardType.DUMMY;
		}
		type.reward(name, uuid);
	}

}
