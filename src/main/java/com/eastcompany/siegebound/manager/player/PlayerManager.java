package com.eastcompany.siegebound.manager.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.eastcompany.siegebound.SiegeboundPlugin;

public class PlayerManager {

	private final Map<UUID, PlayerInstance> playerDataMap = new HashMap<>();

	private static PlayerManager manager;

	public PlayerManager() {
		manager = this;
	}

	public static void addPlayer(PlayerInstance data) {
		manager.playerDataMap.put(data.getUuid(), data);
		SiegeboundPlugin.getSiegeManager().getStartingManager().setMaxPlayer(manager.playerDataMap.size());
	}

	public static PlayerInstance getPlayer(UUID uuid) {
		return manager.playerDataMap.get(uuid);
	}

	public static PlayerInstance getPlayer(Player player) {
		return manager.playerDataMap.get(player.getUniqueId());
	}

	public static void removePlayer(UUID uuid) {
		manager.playerDataMap.remove(uuid);
		SiegeboundPlugin.getSiegeManager().getStartingManager().setMaxPlayer(manager.playerDataMap.size());
	}

	public static boolean contains(UUID uuid) {
		return manager.playerDataMap.containsKey(uuid);
	}

	public static List<Player> getAllPlayers() {
		List<Player> players = new ArrayList<Player>();

		for (UUID uuid : manager.playerDataMap.keySet()) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null) {
				players.add(player);
			}
		}

		return players;
	}

	public boolean contains(@NotNull Player player) {
		contains(player.getUniqueId());
		return false;
	}

}
