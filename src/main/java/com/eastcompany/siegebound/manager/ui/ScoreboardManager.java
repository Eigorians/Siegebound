package com.eastcompany.siegebound.manager.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.kyori.adventure.text.Component;

public class ScoreboardManager {

	private Scoreboard scoreboard;
	private Objective objective;

	public ScoreboardManager() {
		setupScoreboard();
	}

	@SuppressWarnings("deprecation")
	private void setupScoreboard() {
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		objective = scoreboard.registerNewObjective(
				"siegebound",
				"dummy",
				Component.text("SiegeBound") // タイトルをつけるとわかりやすい
		);

		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public void updateReadyStatus(int readyCount, int totalPlayers) {
		int line = 3;
		objective.getScore("§7準備中").setScore(line--);
		objective.getScore("準備完了: §a" + readyCount + " §7/ " + totalPlayers).setScore(line--);
		objective.getScore(" ").setScore(line--); // 空行

		// 一番上にタイトルが表示される（objective名）
	}

	public void clearForPlayer(Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}

	public void deleteScoreboard() {
		if (objective != null) {
			objective.unregister();
		}
	}
}
