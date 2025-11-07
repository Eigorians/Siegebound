package com.eastcompany.siegebound.manager.kit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class KitItem {

	private final ItemStack item;
	private final Material icon;
	private final String name;
	private final int point;

	public KitItem(Material icon, String name, int point, ItemStack item) {
		this.icon = icon;
		this.item = item;
		this.name = name;
		this.point = point;
	}

	public ItemStack getItem() {
		return item;
	}

	public String getName() {
		return name;
	}

	public int getPoint() {
		return point;
	}

	public ItemStack getIcon() {
		return new ItemStack(icon);
	}

}
