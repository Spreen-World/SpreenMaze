package im.thatneko.spreenmaze.command.impl.bukkit;

import im.thatneko.spreenmaze.SpreenMaze;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SaveChestsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("spreenmaze.command.savechests")) {
                saveChest(player);
            }
        } else {
            sender.sendMessage("Comediante.");
        }
        return true;
    }

    public void saveChest(Player p) {
        ArrayList<ItemStack> list = new ArrayList<>();

        FileConfiguration inv = SpreenMaze.getInstance().getConfig();
        ItemStack[] contents = p.getInventory().getContents();
        for (int i = 0; i < contents.length; ++i) {
            ItemStack item = contents[i];
            if (item != null) {
                list.add(item);
            }
        }
        inv.set("Items", list);
        SpreenMaze.getInstance().saveConfig();
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cLos cofres han sido guardados."));
    }
}
