package com.eastcompany.siegebound.manager.kit.kits;

import java.util.List;

import org.bukkit.Material;

import com.eastcompany.siegebound.manager.kit.KitItem;

import net.kyori.adventure.text.format.NamedTextColor;

public class Sword extends DefaultKit {

	private static final String NAME = "sword";

	private static final List<KitItem> SWORD_KITS = List.of(
			new KitItem(Material.BARRIER, "なし", 0),
			new KitItem(Material.STONE_AXE, "石の斧", 10),
			new KitItem(Material.IRON_SWORD, "鉄の剣", 20),
			new KitItem(Material.DIAMOND_SWORD, "ダイヤの剣", 30));

	private static final NamedTextColor namedTextColor = NamedTextColor.GREEN;

	public Sword() {
		super(NAME, SWORD_KITS, namedTextColor);
	}
}
