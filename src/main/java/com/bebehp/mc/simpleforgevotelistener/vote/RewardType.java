package com.bebehp.mc.simpleforgevotelistener.vote;

import com.bebehp.mc.simpleforgevotelistener.player.VoterPlayer;

public enum RewardType {
	ONLINE {
		@Override
		public void reward(final VoterPlayer player) {
			new OnlineVoteEvent(player).onVote();
		}
	},
	OFFLINE {
		@Override
		public void reward(final VoterPlayer player) {
			new OfflineVoteEvent(player).onVote();
		}
	},
	DUMMY {
		@Override
		public void reward(final VoterPlayer player) {
			new DummyVoteEvent(player).onVote();
		}
	},
	;

	public abstract void reward(VoterPlayer player);
}
