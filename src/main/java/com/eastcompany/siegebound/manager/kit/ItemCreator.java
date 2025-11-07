package com.eastcompany.siegebound.manager.kit;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.eastcompany.siegebound.SiegeboundPlugin;

public class ItemCreator {
	public static ItemStack setitem(Material material, String value) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			if (item.getType().getMaxDurability() > 0) {
				meta.setUnbreakable(true);
			}
			NamespacedKey namespacedKey = new NamespacedKey(SiegeboundPlugin.getInstance(), "KitItem");
			meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, value);
			item.setItemMeta(meta);
		}
		return item;
	}
}
