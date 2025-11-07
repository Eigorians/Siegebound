package com.eastcompany.siegebound.manager.kit.kits;

import java.util.List;

import org.bukkit.Material;

import com.eastcompany.siegebound.manager.kit.ItemCreator;
import com.eastcompany.siegebound.manager.kit.KitItem;

import net.kyori.adventure.text.format.NamedTextColor;

public class Sword extends DefaultKit {

	private static final String NAME = "sword";

	private static final List<KitItem> SWORD_KITS = List.of(
			new KitItem(Material.BARRIER, "素手", 0, ItemCreator.setitem(Material.AIR, NAME)),
			new KitItem(Material.STONE_AXE, "石の斧", 10, ItemCreator.setitem(Material.STONE_SWORD, NAME)),
			new KitItem(Material.IRON_SWORD, "鉄の剣", 20, ItemCreator.setitem(Material.IRON_SWORD, NAME)),
			new KitItem(Material.DIAMOND_SWORD, "ダイヤの剣", 30, ItemCreator.setitem(Material.DIAMOND_SWORD, NAME)));

	private static final NamedTextColor namedTextColor = NamedTextColor.GREEN;

	public Sword() {
		super(NAME, SWORD_KITS, namedTextColor);
	}
}
