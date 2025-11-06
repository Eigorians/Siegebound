package com.eastcompany.siegebound.manager.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.eastcompany.siegebound.SiegeboundPlugin;

public class PlayerManager {

	private final Map<UUID, PlayerInstance> playerDataMap = new HashMap<>();

	public void addPlayer(PlayerInstance data) {
		playerDataMap.put(data.getUuid(), data);
		SiegeboundPlugin.getSiegeManager().getStartingManager().setMaxPlayer(playerDataMap.size());
	}

	public PlayerInstance getPlayer(UUID uuid) {
		return playerDataMap.get(uuid);
	}

	public PlayerInstance getPlayer(Player player) {
		return playerDataMap.get(player.getUniqueId());
	}

	public void removePlayer(UUID uuid) {
		playerDataMap.remove(uuid);
		SiegeboundPlugin.getSiegeManager().getStartingManager().setMaxPlayer(playerDataMap.size());
	}

	public boolean contains(UUID uuid) {
		return playerDataMap.containsKey(uuid);
	}

	public List<Player> getAllPlayers() {
		List<Player> players = new ArrayList<Player>();

		for (UUID uuid : playerDataMap.keySet()) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null) {
				players.add(player);
			}
		}

		return players;
	}

}
