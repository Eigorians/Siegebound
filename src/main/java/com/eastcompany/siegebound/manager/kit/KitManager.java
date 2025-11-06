package com.eastcompany.siegebound.manager.kit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.eastcompany.siegebound.Config;
import com.eastcompany.siegebound.SiegeboundPlugin;
import com.eastcompany.siegebound.manager.kit.kits.DefaultKit;
import com.eastcompany.siegebound.manager.kit.kits.Sword;
import com.eastcompany.siegebound.manager.ui.TextDisplayManager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class KitManager {

	static final List<DefaultKit> kits = new ArrayList<DefaultKit>();

	public static void setup() {
		kits.add(new Sword());
	}

	public static void createKit() {

		Location kitlocation = Config.getLocation("kit");

		ItemStack kititem = new ItemStack(Material.IRON_SWORD);

		for (Player player : SiegeboundPlugin.getSiegeManager().getPlayerManager().getAllPlayers()) {
			TextDisplayManager.create("kit", player, kitlocation, Component.text("装備選択", NamedTextColor.YELLOW),
					kititem);

			for (DefaultKit kit : kits) {
				KitItem item = kit.getKitForPlayer(player);
				Location location = Config.getLocation(kit.getName());
				if (item != null && location != null) {
					TextDisplayManager.create(item.getName(), player, location,
							Component.text(item.getName(), kit.getColor()),
							item.getItem());
				}
			}
		}

	}
}
