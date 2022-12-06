package im.thatneko.spreenmaze.listener;

import com.destroystokyo.paper.event.player.PlayerStopSpectatingEntityEvent;
import im.thatneko.spreenmaze.SpreenMaze;
import im.thatneko.spreenmaze.command.impl.bukkit.SpectateAllToPlayerCommand;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@RequiredArgsConstructor
public class ConnectionListener implements Listener {
    private final SpreenMaze spreenMaze;

    @EventHandler
    public void onConnection(PlayerJoinEvent event) {
        spreenMaze.getUserManager().createUser(event.getPlayer());
    }

    @EventHandler
    public void onDisconnection(PlayerQuitEvent event) {
        spreenMaze.getUserManager().deleteUserOrDie(event.getPlayer());
    }

    @EventHandler
    public void onDismount(PlayerStopSpectatingEntityEvent event) {
        if (SpectateAllToPlayerCommand.TARGET != null) {
            event.setCancelled(true);
        }
    }
}