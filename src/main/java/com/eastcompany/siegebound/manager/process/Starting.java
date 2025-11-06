package com.eastcompany.siegebound.manager.process;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.eastcompany.siegebound.SiegeboundPlugin;
import com.eastcompany.siegebound.manager.SiegeManager;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;

public class Starting {

	private int maxplayer = 10;
	private final List<UUID> ready = new ArrayList<>();

	private int countdownTaskId = -1;
	private int countdownSeconds = 10;

	public void setMaxPlayer(int maxplayer) {
		this.maxplayer = maxplayer;
	}

	public boolean addReady(Player player) {
		UUID uuid = player.getUniqueId();
		if (ready.contains(uuid))
			return false;

		ready.add(uuid);
		SiegeboundPlugin.getSiegeManager().updateScore(ready.size());

		checkStartCountdown();
		return true;
	}

	public boolean reduce(Player player) {
		UUID uuid = player.getUniqueId();
		if (!ready.contains(uuid))
			return false;

		ready.remove(uuid);
		SiegeboundPlugin.getSiegeManager().updateScore(ready.size());

		stopCountdownIfRunning();
		return true;
	}

	private void checkStartCountdown() {
		if (ready.size() >= maxplayer && countdownTaskId == -1) {
			startCountdown();
		}
	}

	private void startCountdown() {
		countdownSeconds = 10;

		countdownTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
				SiegeboundPlugin.getInstance(),
				() -> {

					// 準備が maxplayer 未満に戻ったら中止
					if (ready.size() < maxplayer) {
						stopCountdownIfRunning();

						for (Player p : SiegeboundPlugin.getSiegeManager().getPlayerManager().getAllPlayers()) {
							p.showTitle(Title.title(
									Component.text("カウントダウン中止").color(NamedTextColor.RED),
									Component.text("人数が減りました").color(NamedTextColor.GRAY)));
							playSound(p, "minecraft:block.anvil.land", 1f, 0.8f);
						}
						return;
					}

					// カウント終了で開始
					if (countdownSeconds <= 0) {
						stopCountdownIfRunning();

						for (Player p : SiegeboundPlugin.getSiegeManager().getPlayerManager().getAllPlayers()) {
							p.showTitle(Title.title(
									Component.text("開始！").color(NamedTextColor.GOLD),
									Component.empty()));
							playSound(p, "minecraft:entity.ender_dragon.growl", 1f, 1f);
						}

						return;
					}

					// カウントダウン表示
					// カウントダウン表示
					for (Player p : SiegeboundPlugin.getSiegeManager().getPlayerManager().getAllPlayers()) {

						// 10秒の瞬間だけメッセージ送信
						if (countdownSeconds == 10) {
							p.sendActionBar(
									Component.text("ゲーム開始まで 10 秒！").color(NamedTextColor.GREEN));
						}

						// 5秒以下はタイトル表示
						if (countdownSeconds <= 5) {
							p.showTitle(Title.title(
									Component.text(String.valueOf(countdownSeconds)).color(NamedTextColor.GOLD),
									Component.empty(),
									Title.Times.times(
											Duration.ZERO, // フェードインなし
											Duration.ofSeconds(1), // ← 2秒表示
											Duration.ZERO // フェードアウトなし
							)));
						}

						// 3秒間だけ pling
						if (countdownSeconds <= 5) {
							playSound(p, "minecraft:block.note_block.pling", 1f, 1.2f);
						}
					}

					countdownSeconds--;
				},
				0L, 20L);

		SiegeManager.addTask(countdownTaskId);
	}

	private void playSound(Player player, String key, float volume, float pitch) {
		player.playSound(Sound.sound(
				Key.key(key),
				Sound.Source.PLAYER,
				volume,
				pitch));
	}

	private void stopCountdownIfRunning() {
		if (countdownTaskId != -1) {
			Bukkit.getScheduler().cancelTask(countdownTaskId);
			countdownTaskId = -1;
		}
	}
}
