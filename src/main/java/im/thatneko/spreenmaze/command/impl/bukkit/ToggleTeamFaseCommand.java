package im.thatneko.spreenmaze.command.impl.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class ToggleTeamFaseCommand implements CommandExecutor {
    public static boolean TEAM_FASE = false;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("spreenmaze.admin")) {
            TEAM_FASE = !TEAM_FASE;
            sender.sendMessage((TEAM_FASE ? "" : "des") + "activao");
        }
        return false;
    }
}
