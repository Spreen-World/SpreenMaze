package im.thatneko.spreenmaze.command.impl.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class UnSpectateAllCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("spreencore.admin")) {
            return true;
        }
        Player tempTarget = SpectateAllToPlayerCommand.TARGET;
        SpectateAllToPlayerCommand.TARGET = null;

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player == tempTarget) {
                return;
            }
            player.setSpectatorTarget(null);
            player.setGameMode(SpectateAllToPlayerCommand.GAMEMODES.getOrDefault(player, GameMode.SPECTATOR));
        });
        return false;
    }
}
