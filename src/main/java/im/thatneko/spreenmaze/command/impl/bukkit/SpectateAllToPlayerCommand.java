package im.thatneko.spreenmaze.command.impl.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class SpectateAllToPlayerCommand implements CommandExecutor {
    public static final Map<Player, GameMode> GAMEMODES = new ConcurrentHashMap<>();
    public static Player TARGET;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("spreencore.admin")) {
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        TARGET = target;
        if (target != null) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (player == target) {
                    return;
                }
                GAMEMODES.put(player, player.getGameMode());
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(target);
                player.setSpectatorTarget(target);
            });
        }
        return false;
    }
}
