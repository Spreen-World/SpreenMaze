package im.thatneko.spreenmaze.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import im.thatneko.spreenmaze.SpreenMaze;
import im.thatneko.spreenmaze.user.User;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class LanguageSelectorGUI extends GUI {
    public void open(Player player) {
        ChestGui chestGui = new ChestGui(1, ChatColor.translateAlternateColorCodes('&', "&8Idioma / Language"));
        OutlinePane spanishPane = new OutlinePane(3, 0, 9, 1);
        OutlinePane englishPane = new OutlinePane(5, 0, 9, 1);

        ItemStack spanish = create("&aEspañol", Material.PLAYER_HEAD);
        ItemStack english = create("&cInglés", Material.PLAYER_HEAD);
        setHead(spanish, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJiZDQ1MjE5ODMzMDllMGFkNzZjMWVlMjk4NzQyODc5NTdlYzNkOTZmOGQ4ODkzMjRkYThjODg3ZTQ4NWVhOCJ9fX0=");
        setHead(english, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZDk5ZDljNDY0NzRlMjcxM2E3ZTg0YTk1ZTRjZTdlOGZmOGVhNGQxNjQ0MTNhNTkyZTQ0MzVkMmM2ZjlkYyJ9fX0=");

        User user = SpreenMaze.getInstance().getUserManager().getUserOrDie(player.getUniqueId());

        GuiItem spanishItem = new GuiItem(spanish, inventoryClickEvent -> {
            user.setLanguage("es");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aHas cambiado tu idioma a \"Español\", ahora los subtítulos estarán en este idioma"));
            inventoryClickEvent.setCancelled(true);
            player.closeInventory();
        });
        spanishPane.addItem(spanishItem);
        GuiItem englishItem = new GuiItem(english, inventoryClickEvent -> {
            user.setLanguage("en");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou've changed your language to \"English\", subtitles will be now on this language"));
            inventoryClickEvent.setCancelled(true);
            player.closeInventory();
        });
        englishPane.addItem(englishItem);

        chestGui.addPane(spanishPane);
        chestGui.addPane(englishPane);
        chestGui.show(player);
    }
}