package im.thatneko.spreenmaze.tasks;

import im.thatneko.spreenmaze.SpreenMaze;
import im.thatneko.spreenmaze.chest.NyaChestData;
import im.thatneko.spreenmaze.utils.LocationUtils;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class ChestCheckerTask extends BukkitRunnable {
    private Map<Location, NyaChestData> chests = new ConcurrentHashMap<>();

    @Override
    public void run() {
        chests.forEach((location, nyaChestData) -> {
            long expireTime = System.currentTimeMillis() - nyaChestData.getLastRefill();
            if (expireTime >= 0) {
                nyaChestData.setLastRefill(System.currentTimeMillis() + 300_000L);
                nyaChestData.refill();
            }
        });
    }

    public void addChest(Location location) {
        if (!chests.containsKey(location)) {
            addToMap(location);
            List<String> chestzzz = SpreenMaze.getInstance().getConfig().getStringList("chests");
            chestzzz.add(LocationUtils.locationToString(location));
            SpreenMaze.getInstance().getConfig().set("chests", chestzzz);
            SpreenMaze.getInstance().saveConfig();
        }
    }

    public void addToMap(Location location) {
        NyaChestData nyaChestData = new NyaChestData(location, 0);
        nyaChestData.setLastRefill(System.currentTimeMillis() + 300_000L);
        nyaChestData.refill();
        chests.put(location, nyaChestData);
    }
}
