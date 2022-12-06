package im.thatneko.spreenmaze.command.impl.bukkit;

import im.thatneko.spreenmaze.SpreenMaze;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class MineroCommand implements CommandExecutor {
    private final List<String> used = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (used.contains(player.getName())) {
            return true;
        }
        used.add(player.getName());
        sendMessage(player, "&bMinero &8> &e¡Hola &b" + player.getName() + "&e! Te doy la bienvenida a la mina del Maze Runner.", 0);
        sendMessage(player, "&bMinero &8> &eLastimosamente este será mi último día ya que cambiaré de trabajo...", 40);
        sendMessage(player, "&bMinero &8> &eSiempre hay que probar cosas nuevas, ¿no?", 80);
        sendMessage(player, "&bMinero &8> &eEn fin... ¡Toma tu kit para iniciar!", 100);
        Bukkit.getScheduler().runTaskLater(SpreenMaze.getInstance(), () -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit basic " + player.getName());
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }, 120);
        return true;
    }

    public void sendMessage(Player player, String s, long time) {
        Bukkit.getScheduler().runTaskLater(SpreenMaze.getInstance(), () -> {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
        }, time);
    }
}
