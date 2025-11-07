package com.eastcompany.siegebound.manager.kit.kits;

import java.util.List;

import org.bukkit.Material;

import com.eastcompany.siegebound.manager.kit.ItemCreator;
import com.eastcompany.siegebound.manager.kit.KitItem;

import net.kyori.adventure.text.format.NamedTextColor;

public class Bow extends DefaultKit {

	private static final String NAME = "bow";

	private static final List<KitItem> KITS = List.of(
			new KitItem(Material.BARRIER, "素手", 0, ItemCreator.setitem(Material.AIR, NAME)),
			new KitItem(Material.ARROW, "弓", 10, ItemCreator.setitem(Material.BOW, NAME)));

	private static final NamedTextColor namedTextColor = NamedTextColor.GOLD;

	public Bow() {
		super(NAME, KITS, namedTextColor);
	}
}
