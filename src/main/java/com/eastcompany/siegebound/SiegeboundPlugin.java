package com.eastcompany.siegebound;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.eastcompany.siegebound.command.SiegeboundCommand;
import com.eastcompany.siegebound.manager.SiegeManager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class SiegeboundPlugin extends JavaPlugin {

	static SiegeboundPlugin siegeboundPlugin;

	private static SiegeManager siegeManager;

	@Override
	public void onEnable() {
		//Config 呼び出し

		siegeboundPlugin = this;
		Config.init(this);
		//コマンド呼び出し
		this.getCommand("siegebound").setExecutor(new SiegeboundCommand());
		//有効化メッセージ
		Bukkit.broadcast(Config.PREFIX.append(Component.text("シージバウンド有効化", NamedTextColor.WHITE)));
	}

	@Override
	public void onDisable() {

		if (siegeManager != null) {
			siegeManager.end();
		}

	}

	public static SiegeManager getSiegeManager() {
		return siegeManager;
	}

	public static void createNewGame() {
		siegeManager = new SiegeManager();
	}

	public static SiegeboundPlugin getInstance() {
		return siegeboundPlugin;
	}

}
