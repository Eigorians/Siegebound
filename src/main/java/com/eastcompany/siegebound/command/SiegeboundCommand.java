package com.eastcompany.siegebound.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.eastcompany.siegebound.Config;
import com.eastcompany.siegebound.SiegeboundPlugin;
import com.eastcompany.siegebound.manager.player.PlayerData;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class SiegeboundCommand implements CommandExecutor, TabCompleter {

    private final List<String> mainSubCommands = Arrays.asList("call", "end", "setlocation");

    // setlocation用に別クラスを用意しておく
    private final LocationCommand locationCommand = new LocationCommand();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Component.text()
                .append(Config.PREFIX)
                .append(Component.text("使い方: /siegebound <call|end|setlocation>", NamedTextColor.YELLOW)));
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
        case "call":
            sender.sendMessage(Component.text()
                .append(Config.PREFIX)
                .append(Component.text("Siegeboundの準備を開始します。", NamedTextColor.GREEN)));

            SiegeboundPlugin.createnewgame();
            
            if (sender instanceof Player player) {
                Location lobby = Config.lobbylocation;  // もしくは Config.lobbylocation
                if (lobby == null) {
                    player.sendMessage(Component.text()
                        .append(Config.PREFIX)
                        .append(Component.text("ロビーの座標が設定されていません。", NamedTextColor.RED)));
                    return true;
                }
                SiegeboundPlugin.getSiegeManager().getPlayerManager().addPlayer(new PlayerData(player));
                
                player.teleport(lobby);
                player.sendMessage(Component.text()
                    .append(Config.PREFIX)
                    .append(Component.text("ロビーにテレポートしました。", NamedTextColor.GREEN)));
            } else {
                sender.sendMessage(Component.text()
                    .append(Config.PREFIX)
                    .append(Component.text("このコマンドはプレイヤーからのみ実行可能です。", NamedTextColor.RED)));
            }
            break;

            case "end":
                // endの処理
                sender.sendMessage(Component.text()
                    .append(Config.PREFIX)
                    .append(Component.text("Siegeboundの試合を終了します。", NamedTextColor.GREEN)));
                break;
            case "setlocation":
                // setlocationの処理は別クラスに委譲
                // argsの0番目は"setlocation"なので、argsを1個減らして渡す
                String[] subArgs = new String[args.length - 1];
                System.arraycopy(args, 1, subArgs, 0, args.length - 1);
                return locationCommand.onCommand(sender, command, label, subArgs);
            default:
                sender.sendMessage(Component.text()
                    .append(Config.PREFIX)
                    .append(Component.text("無効なサブコマンドです。", NamedTextColor.RED)));
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            for (String sub : mainSubCommands) {
                if (sub.startsWith(args[0].toLowerCase())) {
                    completions.add(sub);
                }
            }
            return completions;
        } else if (args.length > 1 && args[0].equalsIgnoreCase("setlocation")) {
            // setlocation以下の補完はLocationCommandに委譲
            String[] subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, args.length - 1);
            return locationCommand.onTabComplete(sender, command, alias, subArgs);
        }
        return List.of();
    }
}
