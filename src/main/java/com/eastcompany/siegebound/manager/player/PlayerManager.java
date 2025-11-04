package com.eastcompany.siegebound.manager.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerManager {
    private final Map<UUID, PlayerInstance> playerDataMap = new HashMap<>();

    public void addPlayer(PlayerInstance data) {
        playerDataMap.put(data.getUuid(), data);
    }

    public PlayerInstance getPlayer(UUID uuid) {
        return playerDataMap.get(uuid);
    }
    
    public PlayerInstance getPlayer(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }

    public void removePlayer(UUID uuid) {
        playerDataMap.remove(uuid);
    }

    public boolean contains(UUID uuid) {
        return playerDataMap.containsKey(uuid);
    }

    public Map<UUID, PlayerInstance> getAllPlayers() {
        return playerDataMap;
    }
}
