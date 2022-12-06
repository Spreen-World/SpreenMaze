package im.thatneko.spreenmaze.gui;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public abstract class GUI {
    public ItemStack create(String name, Material material) {
        ItemStack itemStack = new ItemStack(material);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void setHead(ItemStack itemStack, String texture) {
        itemStack.setItemMeta(getSkull(texture, "Language", itemStack));
    }

    private SkullMeta getSkull(String url, String name, ItemStack itemStack) {
        if (!url.isEmpty() && !name.isEmpty()) {
            SkullMeta headMeta = (SkullMeta) itemStack.getItemMeta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), name);
            profile.getProperties().put("textures", new Property("textures", url));

            try {
                assert headMeta != null;
                Field profileField = headMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(headMeta, profile);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return headMeta;
        } else {
            return null;
        }
    }
}