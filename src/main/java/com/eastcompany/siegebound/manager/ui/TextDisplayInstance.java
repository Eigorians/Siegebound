package com.eastcompany.siegebound.manager.ui;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import com.eastcompany.siegebound.manager.SiegeManager;

import net.kyori.adventure.text.Component;

public class TextDisplayInstance {

	private final World world;
	private TextDisplay textDisplay;
	private ItemDisplay itemDisplay;
	private Location baseLocation;

	public TextDisplayInstance(Location baseLocation, Component message, @Nullable ItemStack itemStack) {
		this.world = baseLocation.getWorld();
		this.baseLocation = baseLocation.clone();

		// テキストを1ブロック上にスポーン
		Location textLocation = baseLocation.clone().add(0, 1, 0);
		this.textDisplay = world.spawn(textLocation, TextDisplay.class, display -> {
			display.text(message);
			display.setBillboard(Display.Billboard.CENTER);
			display.setShadowed(true);
			SiegeManager.addentity(textDisplay);
		});

		// アイテムがある場合、1ブロック下にスポーン
		if (itemStack != null) {
			Location itemLocation = baseLocation.clone();
			this.itemDisplay = world.spawn(itemLocation, ItemDisplay.class, display -> {
				display.setItemStack(itemStack);
				display.setBillboard(Display.Billboard.CENTER);
			});
			SiegeManager.addentity(itemDisplay);
		}
	}

	/** 表示テキストを更新 */
	public void rename(Component newMessage) {
		if (textDisplay != null && !textDisplay.isDead()) {
			textDisplay.text(newMessage);
		}
	}

	/** アイテムを更新（存在しない場合は新規生成） */
	public void setItem(@Nullable ItemStack newItem) {
		if (newItem == null) {
			if (itemDisplay != null && !itemDisplay.isDead()) {
				itemDisplay.remove();
				itemDisplay = null;
			}
			return;
		}

		if (itemDisplay == null || itemDisplay.isDead()) {
			Location itemLocation = baseLocation.clone();
			itemDisplay = world.spawn(itemLocation, ItemDisplay.class, display -> {
				display.setItemStack(newItem);
				display.setBillboard(Display.Billboard.CENTER);
			});
		} else {
			itemDisplay.setItemStack(newItem);
		}
	}

	/** 座標を変更（TextDisplayとItemDisplayを一緒に移動） */
	public void moveTo(Location newBase) {
		this.baseLocation = newBase.clone();

		if (textDisplay != null && !textDisplay.isDead()) {
			textDisplay.teleport(newBase.clone().add(0, 1, 0));
		}

		if (itemDisplay != null && !itemDisplay.isDead()) {
			itemDisplay.teleport(newBase.clone());
		}
	}

	/** 削除処理 */
	public void remove() {
		if (textDisplay != null && !textDisplay.isDead()) {
			textDisplay.remove();
		}
		if (itemDisplay != null && !itemDisplay.isDead()) {
			itemDisplay.remove();
		}
	}

	/** 現在のベース位置を取得 */
	public Location getBaseLocation() {
		return baseLocation.clone();
	}
}
