package com.bebehp.mc.simpleforgevotelistener.handler;

import java.util.Queue;
import java.util.UUID;

import com.bebehp.mc.simpleforgevotelistener.player.UUIDUtil;
import com.bebehp.mc.simpleforgevotelistener.vote.RewardType;
import com.google.common.collect.Queues;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class VoteHandler {
	public static final VoteHandler INSTANCE = new VoteHandler();

	private final Queue<VotifierEvent> update = Queues.newConcurrentLinkedQueue();

	private VoteHandler() {
	}

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		this.update.add(event);
	}

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		VotifierEvent line;
		while ((line = this.update.poll())!=null) {
			final Vote vote = line.getVote();
			final String name = vote.getUsername();
			RewardType type;

			UUID uuid = UUIDUtil.getUUIDfromUsername(name);
			if (uuid!=null) {
				type = RewardType.ONLINE;
			} else {
				uuid = UUIDUtil.getUUIDfromUsernameCache(name);
				if (uuid==null)
					uuid = UUIDUtil.getUUIDfromMojangAPI(name);
				if (uuid!=null)
					type = RewardType.OFFLINE;
				else
					type = RewardType.DUMMY;
			}
			type.reward(name, uuid);
		}
	}

}
