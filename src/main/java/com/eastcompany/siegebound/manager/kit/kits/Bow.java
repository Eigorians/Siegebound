package com.eastcompany.siegebound.manager.kit.kits;

import java.util.List;

import org.bukkit.Material;

import com.eastcompany.siegebound.manager.kit.KitItem;

import net.kyori.adventure.text.format.NamedTextColor;

public class Bow extends DefaultKit {

	private static final String NAME = "bow";

	private static final List<KitItem> SWORD_KITS = List.of(
			new KitItem(Material.BARRIER, "なし", 0),
			new KitItem(Material.ARROW, "弓", 10));

	private static final NamedTextColor namedTextColor = NamedTextColor.GOLD;

	public Bow() {
		super(NAME, SWORD_KITS, namedTextColor);
	}
}
