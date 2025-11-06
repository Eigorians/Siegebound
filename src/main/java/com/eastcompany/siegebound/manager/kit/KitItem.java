package com.eastcompany.siegebound.manager.kit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class KitItem {

	private final ItemStack item;
	private final Material material;
	private final String name;
	private final int point;

	public KitItem(Material material, String name, int point) {
		this.material = material;
		this.item = new ItemStack(material);
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

	public Material getMaterial() {
		return material;
	}
}
