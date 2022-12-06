package im.thatneko.spreenmaze.user;

import com.google.gson.JsonObject;
import im.thatneko.spreenmaze.SpreenMaze;
import im.thatneko.spreenmaze.dialogs.Dialog;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.UUID;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
@Setter
@SuppressWarnings("all")
public class User {
    private UUID uuid;
    private Dialog dialog = null;
    private String language = null;

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void sendActionBar(String s) {
        getPlayer().spigot().sendMessage(
                ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', s))
        );
    }

    @SneakyThrows
    public void load() {
        File dialogFile = new File(SpreenMaze.getInstance().getDataFolder(), "users/" + uuid.toString() + ".json");

        if (!dialogFile.exists()) {
            return;
        }

        JsonObject json = null;

        try (FileReader reader = new FileReader(dialogFile)) {
            json = SpreenMaze.getInstance().getFancyGson().fromJson(reader, JsonObject.class);
        }

        if (json.has("language")) {
            language = json.get("language").getAsString();
        }
    }

    @SneakyThrows
    public void save() {
        File userFile = new File(SpreenMaze.getInstance().getDataFolder(), "users/" + uuid.toString() + ".json");

        if (!userFile.exists()) {
            userFile.createNewFile();
        }

        Writer writer = new FileWriter(userFile);

        try {
            SpreenMaze.getInstance().getFancyGson().toJson(toJsonObject(), writer);
        } finally {
            writer.close();
        }
    }

    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        //json.addProperty("mana", mana);
        if (language != null) {
            json.addProperty("language", language);
        }

        return json;
    }

    public String computeLanguage() {
        return language == null ? "es" :  language;
    }
}