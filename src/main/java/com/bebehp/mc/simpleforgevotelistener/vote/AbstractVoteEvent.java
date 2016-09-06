package com.bebehp.mc.simpleforgevotelistener.vote;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

// TODO
public abstract class AbstractVoteEvent implements IVote {

	@Override
	public String parse(final String name, final String raw, final String args) {
		if (!raw.contains("%s"))
			return raw;

		final String[] astring = args.split(" ");
		final List<String> list = new ArrayList<String>();
		for (final String line : astring) {
			if (StringUtils.equalsIgnoreCase(line, "username")) {
				list.add(name);
				break;
			} else if (StringUtils.equalsIgnoreCase(line, "")) {

			}
		}
		return null;
	}

}
