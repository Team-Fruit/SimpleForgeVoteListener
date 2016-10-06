package com.bebehp.mc.simpleforgevotelistener.vote;

import java.util.UUID;

import com.bebehp.mc.simpleforgevotelistener.player.VoterPlayer;

public enum RewardType {
	ONLINE {
		@Override
		public void reward(final String name, final UUID uuid) {
			new OnlineVoteEvent(new VoterPlayer(name, uuid)).onVote();
		}
	},
	OFFLINE {
		@Override
		public void reward(final String name, final UUID uuid) {
			new OfflineVoteEvent(new VoterPlayer(name, uuid)).onVote();
		}
	},
	DUMMY {
		@Override
		public void reward(final String name, final UUID uuid) {
			new DummyVoteEvent(name).onVote();
		}
	},
	;

	public abstract void reward(String name, UUID uuid);

}
