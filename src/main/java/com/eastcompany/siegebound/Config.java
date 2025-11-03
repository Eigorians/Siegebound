package com.eastcompany.siegebound;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Config {

    private static JavaPlugin plugin;

    public static final Component PREFIX = Component.text("[Siegebound] ", NamedTextColor.GOLD);
    public static Location lobbylocation;

    // 初期化：メインクラスから一度呼び出す
    public static void init(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        plugin.saveDefaultConfig();
        reloadconfig();
    }

    public static FileConfiguration get() {
        return plugin.getConfig();
    }

    public static void save() {
        plugin.saveConfig();
    }
    
    private static void reloadconfig() {
    	 lobbylocation = getLocation("lobby");
    }

    public static void setLocation(String path, Location loc) {
        FileConfiguration config = get();
        config.set(path + ".world", loc.getWorld().getName());
        config.set(path + ".x", loc.getX());
        config.set(path + ".y", loc.getY());
        config.set(path + ".z", loc.getZ());
        config.set(path + ".yaw", loc.getYaw());
        config.set(path + ".pitch", loc.getPitch());
        save();
        reloadconfig();
    }

    // 汎用的に座標を取得。存在しなければnull
    public static Location getLocation(String path) {
        FileConfiguration config = get();
        if (!config.contains(path + ".world")) return null;

        World world = plugin.getServer().getWorld(config.getString(path + ".world"));
        if (world == null) return null;

        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");
        float yaw = (float) config.getDouble(path + ".yaw", 0);
        float pitch = (float) config.getDouble(path + ".pitch", 0);

        return new Location(world, x, y, z, yaw, pitch);
    }

    // 文字列を取得（存在しなければデフォルト値を返す）
    public static String getString(String path, String def) {
        return get().getString(path, def);
    }

    // 文字列を保存
    public static void setString(String path, String value) {
        get().set(path, value);
        save();
    }

    // int取得（存在しなければデフォルト値）
    public static int getInt(String path, int def) {
        return get().getInt(path, def);
    }

    // int保存
    public static void setInt(String path, int value) {
        get().set(path, value);
        save();
    }

    // boolean取得
    public static boolean getBoolean(String path, boolean def) {
        return get().getBoolean(path, def);
    }

    // boolean保存
    public static void setBoolean(String path, boolean value) {
        get().set(path, value);
        save();
    }
}
