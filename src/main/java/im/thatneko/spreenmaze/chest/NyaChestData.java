package im.thatneko.spreenmaze.chest;

import im.thatneko.spreenmaze.SpreenMaze;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
public class NyaChestData {
    private Location location;
    private long lastRefill;

    public void refill() {
        FileConfiguration inv = SpreenMaze.getInstance().getConfig();

        List<?> list = inv.getList("Items");
        ItemStack[] contents = new ItemStack[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            contents[i] = (ItemStack) list.get(i);
        }
//        Bukkit.broadcastMessage("[DEBUG] Array: " + Arrays.toString(contents));
        if (location != null && location.getBlock().getType().equals(Material.CHEST)) {
            Chest chest = (Chest) location.getBlock().getState();
            chest.getInventory().clear();
            for (int i = 0; i<chest.getInventory().getSize(); i++) {
                chest.getInventory().setItem(i, new ItemStack(Material.AIR));
            }
            Random random = new Random();
            while (countItems(chest.getInventory()) < 3) {
                for (ItemStack content : contents) {
                    if (random.nextInt(100) > 50) {
                        chest.getInventory().setItem((new Random()).nextInt(chest.getInventory().getSize()), content);
                    }
                    if (countItems(chest.getInventory()) >= 3) break;
                }
            }
        }
    }

    private int countItems(Inventory paramInventory) {
        byte b = 0;
        for (ItemStack itemStack : paramInventory.getContents()) {
            if (itemStack != null && itemStack.getType() != Material.AIR)
                b++;
        }
        return b;
    }
}
