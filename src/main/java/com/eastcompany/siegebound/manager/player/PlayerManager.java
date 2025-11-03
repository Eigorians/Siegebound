package com.eastcompany.siegebound.manager.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerManager {
    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public void addPlayer(PlayerData data) {
        playerDataMap.put(data.getUuid(), data);
    }

    public PlayerData getPlayer(UUID uuid) {
        return playerDataMap.get(uuid);
    }
    
    public PlayerData getPlayer(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }

    public void removePlayer(UUID uuid) {
        playerDataMap.remove(uuid);
    }

    public boolean contains(UUID uuid) {
        return playerDataMap.containsKey(uuid);
    }

    public Map<UUID, PlayerData> getAllPlayers() {
        return playerDataMap;
    }
}
