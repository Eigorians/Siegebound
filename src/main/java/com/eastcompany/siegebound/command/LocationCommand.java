package com.eastcompany.siegebound.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eastcompany.siegebound.Config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class LocationCommand implements CommandExecutor, org.bukkit.command.TabCompleter {

	private final List<String> validKeys = Arrays.asList("lobby", "attackerbase", "defenderbase", "ready", "kit");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Component.text()
					.append(Config.PREFIX)
					.append(Component.text("このコマンドはプレイヤーのみ実行可能です。", NamedTextColor.RED)));
			return true;
		}

		if (!sender.hasPermission("siegebound.admin")) {
			sender.sendMessage(Component.text()
					.append(Config.PREFIX)
					.append(Component.text("権限がありません。", NamedTextColor.RED)));
			return true;
		}

		if (args.length < 1) {
			sender.sendMessage(Component.text()
					.append(Config.PREFIX)
					.append(Component.text("使い方: /siegebound setlocation <key>", NamedTextColor.YELLOW)));
			return true;
		}

		String key = args[0].toLowerCase();

		if (!validKeys.contains(key)) {
			sender.sendMessage(Component.text()
					.append(Config.PREFIX)
					.append(Component.text("無効なキーです。", NamedTextColor.RED)));
			return true;
		}

		Player player = (Player) sender;
		Location loc = player.getLocation();

		Config.setLocation(key, loc);

		sender.sendMessage(Component.text()
				.append(Config.PREFIX)
				.append(Component.text(key + " の座標を設定しました。", NamedTextColor.GREEN)));

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> completions = new ArrayList<>();

		if (args.length == 1) {
			for (String key : validKeys) {
				if (key.startsWith(args[0].toLowerCase())) {
					completions.add(key);
				}
			}
		}

		return completions;
	}
}
