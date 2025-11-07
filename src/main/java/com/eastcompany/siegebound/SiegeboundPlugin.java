package com.eastcompany.siegebound;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.eastcompany.siegebound.command.SiegeboundCommand;
import com.eastcompany.siegebound.manager.SiegeManager;
import com.eastcompany.siegebound.manager.kit.KitManager;
import com.eastcompany.siegebound.manager.kit.gui.KitGui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class SiegeboundPlugin extends JavaPlugin {

	static SiegeboundPlugin siegeboundPlugin;

	private static SiegeManager siegeManager;

	private static KitGui gui;

	public static boolean debug = true;

	@Override
	public void onEnable() {
		//Config 呼び出し

		siegeboundPlugin = this;
		gui = new KitGui();
		Config.getworld();
		Config.init(this);
		//コマンド呼び出し
		this.getCommand("siegebound").setExecutor(new SiegeboundCommand());
		//有効化メッセージ
		Bukkit.broadcast(Config.PREFIX.append(Component.text("シージバウンド有効化", NamedTextColor.WHITE)));
		KitManager.setup();
	}

	@Override
	public void onDisable() {

		if (siegeManager != null) {
			siegeManager.end();
		}

	}

	public static boolean isgamestarted() {
		if (siegeManager != null && (siegeManager.isgamestarted || siegeManager.InPreparation)) {
			return true;
		}
		return false;
	}

	public static SiegeManager getSiegeManager() {
		return siegeManager;
	}

	public static KitGui getGUI() {
		return gui;
	}

	public static void createNewGame() {
		if (siegeManager != null) {
			siegeManager.end();
		}
		siegeManager = new SiegeManager();
	}

	public static void endGame() {
		if (siegeManager != null) {
			siegeManager.end();
			siegeManager = null;
		}
	}

	public static SiegeboundPlugin getInstance() {
		return siegeboundPlugin;
	}

}
