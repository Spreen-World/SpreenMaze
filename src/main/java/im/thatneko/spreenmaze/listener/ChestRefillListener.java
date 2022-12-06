package im.thatneko.spreenmaze.listener;

import im.thatneko.spreenmaze.SpreenMaze;
import im.thatneko.spreenmaze.utils.LocationUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class ChestRefillListener implements Listener {
    @EventHandler (priority = EventPriority.LOW)
    public void onBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            if (event.getBlock().getType().equals(Material.CHEST)) {
                List<String> chestzzz = SpreenMaze.getInstance().getConfig().getStringList("chests");
                chestzzz.remove(LocationUtils.locationToString(event.getBlock().getLocation()));
                SpreenMaze.getInstance().getConfig().set("chests", chestzzz);
                SpreenMaze.getInstance().saveConfig();
            }
        }
    }
}