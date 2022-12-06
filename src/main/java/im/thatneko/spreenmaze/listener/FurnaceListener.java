package im.thatneko.spreenmaze.listener;

import im.thatneko.spreenmaze.SpreenMaze;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class FurnaceListener implements Listener {
    @EventHandler
    public void onFurnaceSmelt(FurnaceStartSmeltEvent e) {
        org.bukkit.block.Furnace furnace = (org.bukkit.block.Furnace) e.getBlock().getState();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (furnace.getCookTime() < 190) {
                    furnace.setCookTime((short) 200);
                    furnace.setBurnTime((short) 20);
                    furnace.update();
                }
            }
        }.runTaskLater(SpreenMaze.getInstance(), 1L);
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent e) {
        org.bukkit.block.Furnace furnace = (org.bukkit.block.Furnace) e.getBlock().getState();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (furnace.getCookTime() < 190) {
                    furnace.setCookTime((short) 200);
                    furnace.setBurnTime((short) 20);
                    furnace.update();
                }
            }
        }.runTaskLater(SpreenMaze.getInstance(), 1L);
    }
}