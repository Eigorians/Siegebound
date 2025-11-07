package com.eastcompany.siegebound.manager.kit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.eastcompany.siegebound.Config;
import com.eastcompany.siegebound.command.LocationCommand;
import com.eastcompany.siegebound.manager.kit.kits.Bow;
import com.eastcompany.siegebound.manager.kit.kits.DefaultKit;
import com.eastcompany.siegebound.manager.kit.kits.Sword;
import com.eastcompany.siegebound.manager.player.PlayerManager;
import com.eastcompany.siegebound.manager.ui.TextDisplayManager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class KitManager {

	static final List<DefaultKit> kits = new ArrayList<DefaultKit>();

	public static void setup() {

		Location kitSelectorLocation = getLocation("kitselector");
		Location kitCreatorLocation = getLocation("kitcreator");

		if (kitSelectorLocation != null) {
			TextDisplayManager.adddisplaylocation("kitselector", kitSelectorLocation);
		}

		if (kitCreatorLocation != null) {
			TextDisplayManager.adddisplaylocation("kitcreator", kitCreatorLocation);
		}

		kits.add(new Sword());
		kits.add(new Bow());
		for (DefaultKit kit : kits) {
			LocationCommand.addLocation(kit.getName());
			Location kitlocation = getLocation(kit.getName());
			if (kitlocation != null) {
				TextDisplayManager.adddisplaylocation(kit.getName(), kitlocation);
			}
		}
	}

	public static void loadKitSelector() {

		Location kitlocation = getLocation("kitselector");

		if (kitlocation != null) {
			ItemStack kititem = new ItemStack(Material.BOOK);
			for (Player player : PlayerManager.getAllPlayers()) {
				TextDisplayManager.create("kitselector", player, kitlocation,
						Component.text("装備選択", NamedTextColor.YELLOW),
						kititem);
			}
		}

		Location kitCreatorLocation = getLocation("kitcreator");

		if (kitCreatorLocation != null) {
			ItemStack kititem = new ItemStack(Material.IRON_SWORD);
			for (Player player : PlayerManager.getAllPlayers()) {
				TextDisplayManager.create("kitcreator", player, kitCreatorLocation,
						Component.text("装備作成", NamedTextColor.GREEN),
						kititem);
			}
		}
	}

	public static void loadKit(Player player) {

		for (DefaultKit kit : kits) {
			KitItem item = kit.getKitForPlayer(player);
			Location location = getLocation(kit.getName());
			if (item != null && location != null) {
				TextDisplayManager.create(item.getName(), player, location,
						Component.text(item.getName(), kit.getColor()),
						item.getIcon());
			}
		}
	}

	private static Location getLocation(String string) {
		return Config.getLocation(string);

	}
}
