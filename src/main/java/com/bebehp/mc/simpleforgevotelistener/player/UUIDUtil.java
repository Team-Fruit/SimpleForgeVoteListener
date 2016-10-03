package com.bebehp.mc.simpleforgevotelistener.player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.bebehp.mc.simpleforgevotelistener.Reference;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.server.FMLServerHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class UUIDUtil {
	public static final String API_PATH = "https://api.mojang.com/users/profiles/minecraft/";
	public static final String CACHE_FILENAME = "usernamecache.json";

	public static UUID getUUIDfromUsername(final String name) {
		if (name == null)
			return null;
		final EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);
		return getUUIDfromEntityPlayerMP(player);
	}

	public static UUID getUUIDfromEntityPlayerMP(final EntityPlayerMP player) {
		if (player == null)
			return null;
		return getUUIDfromGameProfile(player.getGameProfile());
	}

	public static UUID getUUIDfromGameProfile(final GameProfile profile) {
		if (profile == null || !profile.isComplete())
			return null;
		return profile.getId();
	}

	public static UUID getUUIDfromUsernameCache(final String name) {
		JsonReader jr = null;
		BiMap<String, String> map = HashBiMap.create();
		try {
			final File file = new File(FMLServerHandler.instance().getSavesDirectory(), CACHE_FILENAME);
			final BufferedReader br = new BufferedReader(new FileReader(file));
			jr = new JsonReader(br);
			map = new Gson().fromJson(jr, map.getClass());
		} catch (final FileNotFoundException fnfe) {
			Reference.logger.error("The usernamecache.json file not found.");
			return null;
		} catch (final JsonSyntaxException jse) {
			Reference.logger.error(jse);
			return null;
		} finally {
			IOUtils.closeQuietly(jr);
		}

		final String id = map.inverse().get(name);
		if (id == null)
			return null;
		return fromString(id);
	}

	public static UUID getUUIDfromMojangAPI(final String name) {
		if (StringUtils.isBlank(name))
			return null;

		JsonReader jr;
		try {
			final URL url = new URL(API_PATH + name);
			final URLConnection connection = url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			final BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			jr = new JsonReader(br);
			final MojangAPI mojangAPI = new Gson().fromJson(jr, MojangAPI.class);
			final UUID uuid = fromString(mojangAPI.id);
			return uuid;
		} catch (final Exception e) {
			Reference.logger.error(e);
			return null;
		}
	}

	public static UUID fromString(final String name) {
		if (name != null) {
			try {
				return UUID.fromString(name);
			} catch (final IllegalArgumentException e) {
			}
		}
		return null;
	}

	class MojangAPI {
		String id;
		String name;
	}

}
