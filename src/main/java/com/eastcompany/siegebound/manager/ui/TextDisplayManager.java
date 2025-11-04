package com.eastcompany.siegebound.manager.ui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;

public class TextDisplayManager {

	private static final Map<String, TextDisplayInstance> displays = new HashMap<>();

	/**
	 * 新しいDisplayを生成・登録
	 * 
	 * @param id        一意のID（重複時は上書き）
	 * @param location  表示位置
	 * @param message   テキスト
	 * @param itemStack アイテム（null可）
	 * @return 作成されたTextDisplayInstance
	 */

	public static TextDisplayInstance create(String id, Location location, Component message, ItemStack itemStack) {

		// 既に同じIDが存在する場合は削除して置き換え
		if (displays.containsKey(id)) {
			displays.get(id).remove();
		}
		TextDisplayInstance display = new TextDisplayInstance(location, message, itemStack);
		displays.put(id, display);
		return display;
	}

	/** ID指定で削除 */
	public static void remove(String id) {
		TextDisplayInstance display = displays.remove(id);
		if (display != null) {
			display.remove();
		}
	}

	/** インスタンス指定で削除（IDがわかっている場合は上を使う） */
	public static void remove(TextDisplayInstance display) {
		displays.values().remove(display);
		display.remove();
	}

	/** 全削除 */
	public static void removeAll() {
		for (TextDisplayInstance display : displays.values()) {
			display.remove();
		}
		displays.clear();
	}

	/** IDで取得 */
	public static TextDisplayInstance get(String id) {
		return displays.get(id);
	}

	/** 全件取得（読み取り専用） */
	public static Map<String, TextDisplayInstance> getAll() {
		return Collections.unmodifiableMap(displays);
	}

	/** 全体を移動（相対オフセット） */
	public static void moveAll(double x, double y, double z) {
		for (TextDisplayInstance display : displays.values()) {
			Location newLoc = display.getBaseLocation().add(x, y, z);
			display.moveTo(newLoc);
		}
	}

	/** 登録数を取得 */
	public static int size() {
		return displays.size();
	}

	/** 指定IDが存在するか */
	public static boolean contains(String id) {
		return displays.containsKey(id);
	}
}
