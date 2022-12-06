package im.thatneko.spreenmaze.tasks;

import im.thatneko.spreenmaze.SpreenMaze;
import im.thatneko.spreenmaze.command.impl.bukkit.ToggleTeamFaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class TeamFaseTask extends BukkitRunnable {
    @Override
    public void run() {
        if (!ToggleTeamFaseCommand.TEAM_FASE) {
            return;
        }
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
                SpreenMaze.getInstance().getTeamManager().tryAdd(player);
            }
        });
    }
}
