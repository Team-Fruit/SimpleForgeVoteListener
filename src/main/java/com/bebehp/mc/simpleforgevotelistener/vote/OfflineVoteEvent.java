package com.bebehp.mc.simpleforgevotelistener.vote;

import java.util.UUID;

import com.bebehp.mc.simpleforgevotelistener.handler.ConfigurationHandler;
import com.bebehp.mc.simpleforgevotelistener.player.VoteDataIO;

public class OfflineVoteEvent extends AbstractVoteEvent {

	public OfflineVoteEvent(final VoteDataIO voteDataIO, final String name, final UUID uuid) {
		super(voteDataIO, name, uuid);
	}

	@Override
	public void onVote() {
		int voteCount = Integer.parseInt(this.data.getVote());
		this.data.setVote(++voteCount);
		if (this.data.getUuid() == null)
			this.data.setUuid(this.uuid.toString());

		sendOfflineChat();
		if (ConfigurationHandler.offlineVoteEnable) {
			int offlineVoteCount = Integer.parseInt(this.data.getVote_offline());
			this.data.setVote_offline(++offlineVoteCount);
		}
		this.voteDataIO.save(this.data);
	}
}
