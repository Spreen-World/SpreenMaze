package im.thatneko.spreenmaze.command.impl.bukkit;

import im.thatneko.spreenmaze.SpreenMaze;
import im.thatneko.spreenmaze.utils.LocationUtils;
import me.gatogamer.spreencore.listeners.BlockListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class BlocksCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("spreenmaze.block")) {
            switch (args[0].toLowerCase()) {
                case "toggle": {
                    BlockListener.BREAK = !BlockListener.BREAK;
                    BlockListener.PLACE = BlockListener.BREAK;
                    sender.sendMessage((BlockListener.BREAK ? "" : "des") + "activao");
                    break;
                }
                case "toneko": {
                    String loc = LocationUtils.locationToString(((Player) sender).getTargetBlock(null, 5).getLocation());
                    TextComponent sexComponent = new TextComponent("¡Clic para copiar! " + loc);
                    sexComponent.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, loc));
                    TextComponent click = new TextComponent();
                    click.setColor(ChatColor.GREEN);
                    sexComponent.setHoverEvent(new HoverEvent(
                            HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&aClic para copiar n.n")).create()
                    ));
                    sexComponent.setColor(ChatColor.GREEN);
                    sender.spigot().sendMessage(sexComponent);
                    break;
                }
                case "chests": {
                    Location location = ((Player) sender).getTargetBlock(null, 5).getLocation();
                    if (!location.getBlock().getType().equals(Material.CHEST)) {
                        return true;
                    }
                    SpreenMaze.getInstance().getChestCheckerTask().addChest(location);
                    sender.sendMessage("c guardó :v");
                    break;
                }
                case "cheststofile": {
                    Location location = ((Player) sender).getTargetBlock(null, 5).getLocation();
                    if (!location.getBlock().getType().equals(Material.CHEST)) {
                        return true;
                    }
                    List<ItemStack> itemStacks = new ArrayList<>();
                    SpreenMaze.getInstance().getConfig().getList("Items").forEach(rare -> itemStacks.add((ItemStack) rare));

                    Chest chest = (Chest) location.getBlock().getState();
                    for (ItemStack content : chest.getInventory().getContents()) {
                        if (content != null && !content.getType().isAir()) {
                            sender.sendMessage(content.getType() + " x" + content.getAmount());
                            itemStacks.add(content);
                        }
                    }
                    SpreenMaze.getInstance().getConfig().set("Items", itemStacks);
                    SpreenMaze.getInstance().saveConfig();
                    sender.sendMessage("c guardó :v");
                    break;
                }
            }
        }
        return false;
    }
}
