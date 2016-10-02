package com.bebehp.mc.simpleforgevotelistener;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bebehp.mc.simpleforgevotelistener.handler.VoteHandler;
import com.bebehp.mc.simpleforgevotelistener.setting.JsonSetting;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class SFVLCommand extends CommandBase {
	public static final SFVLCommand INSTANCE = new SFVLCommand();

	private SFVLCommand() {
	}

	@Override
	public String getCommandName() {
		return "simpleforgevotelistener";
	}

	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList("sfvl");
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public String getCommandUsage(final ICommandSender icommandsender) {
		return "sfvl <command>";
	}

	@Override
	public void processCommand(final ICommandSender icommandsender, final String[] astring) {
		if (astring.length >= 1 && StringUtils.equalsIgnoreCase(astring[0], "vote")) {
			String name;
			if (astring.length == 1)
				name = icommandsender.getCommandSenderName();
			else
				name = astring[1];
			final Vote vote = new Vote();
			vote.setUsername(name);
			vote.setServiceName(Reference.MODID);
			VoteHandler.INSTANCE.onVoteEvent(new VotifierEvent(vote));
		} else if (astring.length >= 1 && StringUtils.equalsIgnoreCase(astring[0], "reload")) {
			JsonSetting.load(SimpleForgeVoteListener.getModDataDir());
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender icommandsender, final String[] astring) {
		if (astring.length <= 1) {
			return getListOfStringsMatchingLastWord(astring, "vote", "reload");
		} else if (astring.length <= 2 && StringUtils.equalsIgnoreCase(astring[0], "reload")) {
			return getListOfStringsMatchingLastWord(astring, MinecraftServer.getServer().getAllUsernames());
		} else {
			return null;
		}
	}
}
