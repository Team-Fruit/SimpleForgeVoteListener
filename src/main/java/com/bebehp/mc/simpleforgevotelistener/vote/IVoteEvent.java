package com.bebehp.mc.simpleforgevotelistener.vote;

public interface IVoteEvent {

	void onVote();

	void executeCommand();

	void sendChat();

	void sendPrivateChat();

	void sendOfflineChat();
}
