package com.bebehp.mc.simpleforgevotelistener.handler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.bebehp.mc.simpleforgevotelistener.SimpleForgeVoteListener;
import com.bebehp.mc.simpleforgevotelistener.player.UUIDUtil;
import com.bebehp.mc.simpleforgevotelistener.player.VoterPlayer;
import com.bebehp.mc.simpleforgevotelistener.vote.RewardType;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class VoteHandler {
	public static final VoteHandler INSTANCE = new VoteHandler();
	public static final File dataDir = new File(SimpleForgeVoteListener.getModDataDir(), "player");

	public static Map<String, VoterPlayer> player = new HashMap<String, VoterPlayer>();

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
		type.reward(new VoterPlayer(name, uuid));
	}

}
