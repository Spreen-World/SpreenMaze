package im.thatneko.spreenmaze.dialogs;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import im.thatneko.spreenmaze.SpreenMaze;
import im.thatneko.spreenmaze.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an object of a dialog with a NPC.
 * <p>
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
@Setter
@RequiredArgsConstructor
@SuppressWarnings("all")
public class Dialog {
    private final String name;
    private List<DialogPart> parts = new ArrayList<>();

    public void execute(Player player) {
        User user = SpreenMaze.getInstance().getUserManager().getUserOrDie(player.getUniqueId());
        if (user.getDialog() != null) {
            return;
        }
        user.setDialog(this);

        parts.forEach(dialogPart -> dialogPart.execute(player));
    }

    public String toString() {
        return SpreenMaze.getInstance().getFancyGson().toJson(toJsonObject());
    }

    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();

        json.addProperty("name", name);

        JsonArray jsonArray = new JsonArray();
        parts.forEach(dialogPart -> jsonArray.add(dialogPart.toString()));

        json.add("parts", jsonArray);

        return json;
    }

    public static Dialog fromString(String json) {
        return fromJsonObject(SpreenMaze.getInstance().getFancyGson().fromJson(json, JsonObject.class));
    }

    public static Dialog fromJsonObject(JsonObject jsonObject) {
        JsonArray jsonArray = jsonObject.getAsJsonArray("parts");

        List<DialogPart> dialogParts = new ArrayList<>();

        jsonArray.forEach(jsonElement -> dialogParts.add(DialogPart.fromString(jsonElement.getAsString())));

        Dialog dialog = new Dialog(jsonObject.get("name").getAsString());
        dialog.setParts(dialogParts);

        return dialog;
    }

    @SneakyThrows
    public void save() {
        File dialogFile = new File(SpreenMaze.getInstance().getDataFolder(), "dialogs/" + name + ".json");
        if (!dialogFile.exists()) {
            dialogFile.createNewFile();
        }
        Writer writer = new FileWriter(dialogFile);

        try {
            SpreenMaze.getInstance().getFancyGson().toJson(toJsonObject(), writer);
        } finally {
            writer.close();
        }
    }

    @SneakyThrows
    public static Dialog load(String name) {
        File dialogFile = new File(SpreenMaze.getInstance().getDataFolder(), "dialogs/" + name + ".json");

        Reader reader = new FileReader(dialogFile);
        Dialog dialog = null;
        try {
            dialog = fromJsonObject(SpreenMaze.getInstance().getFancyGson().fromJson(reader, JsonObject.class));
        } finally {
            reader.close();
        }

        return dialog;
    }
}