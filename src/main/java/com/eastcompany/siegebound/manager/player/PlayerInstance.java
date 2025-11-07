package com.eastcompany.siegebound.manager.player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import com.eastcompany.siegebound.SiegeboundPlugin;

public class PlayerInstance {
	private final UUID uuid;
	private String kit;
	private boolean alive;
	private GameMode gameMode;

	public PlayerInstance(Player player) {
		this.uuid = player.getUniqueId();
		this.alive = true;
		clearstatus();
		SiegeboundPlugin.getSiegeManager().getTeamManager().getTeamWithLessPlayers().addEntity(player);
		PlayerManager.addPlayer(this);
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

	public void setGamemode(GameMode gameMode) {
		this.gameMode = gameMode;

		Player player = getplayer();

		if (player != null && player.isOnline()) {
			player.setGameMode(gameMode);
		}
	}

	public void loadGamemode() {
		Player player = getplayer();

		if (player != null && player.isOnline()) {
			player.setGameMode(gameMode);
		}
	}

	private Player getplayer() {
		Player player = Bukkit.getPlayer(uuid);
		return player;

	}

	public void clearstatus() {
		Player player = Bukkit.getPlayer(uuid);
		if (player != null && player.isOnline()) {
			player.getInventory().clear();
			player.clearActivePotionEffects();
			player.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.10000000149011612);
			player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(20);
			player.setFoodLevel(20); // 満腹度を最大に保つ
			player.setSaturation(20f);
			player.setHealth(20f);
		}
	}

}
