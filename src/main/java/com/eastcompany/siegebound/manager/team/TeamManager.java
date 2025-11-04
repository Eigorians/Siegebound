package com.eastcompany.siegebound.manager.team;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.kyori.adventure.text.format.NamedTextColor;

public class TeamManager {

	private final Random random = new Random();
	private final Scoreboard scoreboard;
	private Team teamAttacker;
	private Team teamDefender;

	public TeamManager() {
		this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		createTeams();
	}

	public void createTeams() {

		if (scoreboard.getTeam("teamAttacker") != null) {
			scoreboard.getTeam("teamAttacker").unregister();
		}
		if (scoreboard.getTeam("teamDefender") != null) {
			scoreboard.getTeam("teamDefender").unregister();
		}

		teamAttacker = scoreboard.registerNewTeam("teamAttacker");
		teamDefender = scoreboard.registerNewTeam("teamDefender");

		teamAttacker.color(NamedTextColor.RED);
		teamDefender.color(NamedTextColor.BLUE);

		teamAttacker.setAllowFriendlyFire(false);
		teamDefender.setAllowFriendlyFire(false);

		teamAttacker.setCanSeeFriendlyInvisibles(true);
		teamDefender.setCanSeeFriendlyInvisibles(true);

		// プレフィックスを追加（色付きの文字列）
		teamAttacker.prefix(net.kyori.adventure.text.Component.text("[攻撃] ").color(NamedTextColor.RED));
		teamDefender.prefix(net.kyori.adventure.text.Component.text("[防衛] ").color(NamedTextColor.BLUE));

	}

	public Team getTeamAttacker() {
		return teamAttacker;
	}

	public Team getTeamDefender() {
		return teamDefender;
	}

	public Team getTeamWithLessPlayers() {
		int attackerSize = teamAttacker.getSize();
		int defenderSize = teamDefender.getSize();

		if (attackerSize < defenderSize) {
			return teamAttacker;
		} else if (defenderSize < attackerSize) {
			return teamDefender;
		} else {
			return random.nextBoolean() ? teamAttacker : teamDefender;
		}
	}

	public boolean isAttacker(Player player) {
		return isAttacker(player.getUniqueId());
	}

	public boolean isAttacker(UUID uuid) {
		if (teamAttacker.hasEntry(uuid.toString())) {
			return true;
		}
		return false;
	}

	public boolean isDefender(Player player) {
		return isDefender(player.getUniqueId());
	}

	public boolean isDefender(UUID uuid) {
		if (teamDefender.hasEntry(uuid.toString())) {
			return true;
		}
		return false;
	}
}
