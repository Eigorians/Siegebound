package com.eastcompany.siegebound.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.eastcompany.siegebound.Config;
import com.eastcompany.siegebound.manager.player.PlayerManager;
import com.eastcompany.siegebound.manager.team.TeamManager;
import com.eastcompany.siegebound.manager.ui.ScoreboardManager;
import com.eastcompany.siegebound.manager.ui.TextDisplayManager;

public class SiegeManager {

	private final PlayerManager playerManager;
	private final TeamManager teamManager;
	private final ScoreboardManager scoreboardManager;
	private TextDisplayManager textDisplayManager;

	public boolean isgamestarted = false;

	public boolean InPreparation = false;

	static List<UUID> entities = new ArrayList<UUID>();

	public SiegeManager() {
		this.playerManager = new PlayerManager();
		this.teamManager = new TeamManager();
		this.scoreboardManager = new ScoreboardManager();
		Config.getworld();
		Config.reloadconfig();
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public TeamManager getTeamManager() {
		return teamManager;
	}

	public ScoreboardManager getScoreboardManager() {
		return scoreboardManager;
	}

	public void updatescore(int playercount) {
		scoreboardManager.updateReadyStatus(playercount, playerManager.getAllPlayers().size());
	}

	public static void addentity(Entity entity) {
		entities.add(entity.getUniqueId());
	}

	public void end() {
		scoreboardManager.deleteScoreboard();
		if (teamManager.getTeamAttacker() != null) {
			teamManager.getTeamAttacker().unregister();
		}
		if (teamManager.getTeamDefender() != null) {
			teamManager.getTeamDefender().unregister();
		}

		if (Config.world != null) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getWorld().equals(Config.world)) {
					player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
				}
			}
		}

		for (UUID uuid : entities) {
			Entity entity = Bukkit.getEntity(uuid);
			if (entity != null) {
				entity.remove();
			}
		}

		Bukkit.unloadWorld(Config.getworld(), false);
	}
}
