package com.eastcompany.siegebound.manager.ui;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import com.eastcompany.siegebound.SiegeboundPlugin;
import com.eastcompany.siegebound.manager.SiegeManager;

import net.kyori.adventure.text.Component;

public class TextDisplayInstance {

	private final World world;
	private TextDisplay textDisplay;
	private ItemDisplay itemDisplay;
	private Location baseLocation;
	private UUID playeruuid;

	public TextDisplayInstance(Player player, Location baseLocation, Component message, @Nullable ItemStack itemStack) {
		this.world = baseLocation.getWorld();
		this.baseLocation = baseLocation.clone();
		this.playeruuid = player.getUniqueId();

		// テキストを1ブロック上にスポーン
		Location textLocation = baseLocation.clone().add(0, 2, 0);
		this.textDisplay = world.spawn(textLocation, TextDisplay.class, display -> {
			display.text(message);
			display.setBillboard(Display.Billboard.CENTER);
			display.setShadowed(true);
			display.setPersistent(false); // 永続化しない（再起動で残らない）
		});
		SiegeManager.addEntity(textDisplay);

		// アイテムがある場合、1ブロック下にスポーン
		if (itemStack != null) {
			Location itemLocation = baseLocation.clone().add(0, 1.5, 0);
			this.itemDisplay = world.spawn(itemLocation, ItemDisplay.class, display -> {
				display.setItemStack(itemStack);
				display.setBillboard(Display.Billboard.CENTER);
				display.setPersistent(false);
			});
			SiegeManager.addEntity(itemDisplay);
		}

		hideFromAllExcept();
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
				display.setPersistent(false);
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

	/** 特定プレイヤーだけに表示 */
	public void hideFromAllExcept() {
		Player owner = getPlayer();
		if (owner == null)
			return;

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.equals(owner)) {
				showToPlayer(p);
			} else {
				hideFromPlayer(p);
			}
		}
	}

	public void hideFromPlayer(Player p) {
		if (p.getUniqueId() != playeruuid) {
			if (textDisplay != null && !textDisplay.isDead()) {
				p.hideEntity(SiegeboundPlugin.getInstance(), textDisplay);
			}
			if (itemDisplay != null && !itemDisplay.isDead()) {
				p.hideEntity(SiegeboundPlugin.getInstance(), itemDisplay);
			}
		}
	}

	/** この表示を特定プレイヤーに見せる */
	public void showToPlayer(Player player) {
		if (textDisplay != null && !textDisplay.isDead()) {
			player.showEntity(SiegeboundPlugin.getInstance(), textDisplay);
		}
		if (itemDisplay != null && !itemDisplay.isDead()) {
			player.showEntity(SiegeboundPlugin.getInstance(), itemDisplay);
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

	public Player getPlayer() {
		return Bukkit.getPlayer(playeruuid);
	}
}
