package im.thatneko.spreenmaze.listener;

import me.gatogamer.spreencore.listeners.BlockListener;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class PlaceListener implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!BlockListener.PLACE) {
            return;
        }
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }

        Block block = event.getBlock();
        if (event.getBlock().getY() > 100) {
            event.setCancelled(true);
        }
    }
}