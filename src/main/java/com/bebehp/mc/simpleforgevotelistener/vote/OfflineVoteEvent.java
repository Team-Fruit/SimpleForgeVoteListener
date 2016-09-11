package com.bebehp.mc.simpleforgevotelistener.vote;

import java.util.UUID;

import com.bebehp.mc.simpleforgevotelistener.ConfigurationHandler;
import com.bebehp.mc.simpleforgevotelistener.vote.data.VoteDataIO;

public class OfflineVoteEvent extends AbstractVoteEvent {

	public OfflineVoteEvent(final VoteDataIO voteDataIO, final String name, final UUID uuid) {
		super(voteDataIO, name, uuid);
	}

	@Override
	public void onVote() {
		super.onVote();

		sendOfflineChat();
		if (this.uuid != null) {
			if (ConfigurationHandler.offlineVoteEnable) {
				int offlineVoteCount = Integer.parseInt(this.data.getVote_offline());
				this.data.setVote_offline(offlineVoteCount++);
			}
			this.voteDataIO.save(this.data);
		}
	}
}
