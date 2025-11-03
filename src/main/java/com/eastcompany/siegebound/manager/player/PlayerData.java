package com.eastcompany.siegebound.manager.player;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.eastcompany.siegebound.SiegeboundPlugin;

public class PlayerData {
    private final UUID uuid;
    private String kit;
    private boolean alive;

    public PlayerData(Player player) {
        this.uuid = player.getUniqueId();
        this.alive = true;
        SiegeboundPlugin.getSiegeManager().getTeamManager().getTeamWithLessPlayers().addEntity(player);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getKit() {
        return kit;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
