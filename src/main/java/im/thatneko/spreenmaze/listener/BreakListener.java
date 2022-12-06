package im.thatneko.spreenmaze.listener;

import im.thatneko.spreenmaze.SpreenMaze;
import me.gatogamer.spreencore.listeners.BlockListener;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class BreakListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!BlockListener.BREAK) {
            return;
        }
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }

        Block block = event.getBlock();
        if (event.getBlock().getY() <= 100) {
            return;
        }
        event.setCancelled(true);
        switch (block.getType()) {
            case COAL_ORE:
            case DIAMOND_ORE:
            case GOLD_ORE:
            case IRON_ORE:
                event.getBlock().getDrops(event.getPlayer().getItemInUse()).forEach(itemStack ->
                        event.getPlayer().getInventory().addItem(itemStack)
                );
                block.setType(Material.STONE);
                break;
            case STONE:
                event.getBlock().getDrops(event.getPlayer().getItemInUse()).forEach(itemStack ->
                        event.getPlayer().getInventory().addItem(itemStack)
                );
                block.setType(Material.COBBLESTONE);
                Bukkit.getScheduler().runTaskLater(SpreenMaze.getInstance(), () -> {
                    block.setType(Material.STONE);
                }, 60L);
                break;
        }
    }
}