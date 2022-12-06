package im.thatneko.spreenmaze.dialogs;

import com.google.gson.JsonObject;
import im.thatneko.spreenmaze.SpreenMaze;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Represents a part of a dialog. <p>
 * Which can be: <p>
 * - {@linkplain DialogPartType#TEXT}: A simple text sent to the user. <p>
 * - {@linkplain DialogPartType#TITLE}: A simple title sent to the user. <p>
 * - {@linkplain DialogPartType#ACTION_BAR}: A text that'll be sent in actionbar to the user. <p>
 * - {@linkplain DialogPartType#AUDIO}: Play an audio to the user. <p>
 * - {@linkplain DialogPartType#PLAYER_COMMAND}: A command that the player will execute. <p>
 * - {@linkplain DialogPartType#CONSOLE_COMMAND}: A command that the server will execute. <p>
 * - {@linkplain DialogPartType#END}: Just ending the dialog, then player can use another dialog. <p>
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@RequiredArgsConstructor
@Getter
public class DialogPart {

    private final DialogPartType type;
    private final String args;
    private final long execute;

    /**
     * Executes the action at the indicated time.
     *
     * @param player: Player that'll be part of the action.
     */
    public void execute(Player player) {
        //String parsed = PlaceholderAPI.setPlaceholders(player, args);
        String parsed = args;
        switch (type) {
            case TEXT:
                Bukkit.getScheduler().runTaskLater(SpreenMaze.getInstance(), () -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', parsed)), execute);
                break;
            case TITLE: {
                String[] args = parsed.split("<:>");
                String title = args[0];
                String subtitle = args[1];
                int fadeIn = Integer.parseInt(args[2]);
                int stay = Integer.parseInt(args[3]);
                int fadeOut = Integer.parseInt(args[4]);

                Bukkit.getScheduler().runTaskLater(SpreenMaze.getInstance(), () ->
                        player.sendTitle(
                                ChatColor.translateAlternateColorCodes('&', title),
                                ChatColor.translateAlternateColorCodes('&', subtitle),
                                fadeIn, stay, fadeOut
                        ), execute
                );
                break;
            }

            case ACTION_BAR:
                Bukkit.getScheduler().runTaskLater(SpreenMaze.getInstance(), () -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', parsed))), execute);
                break;
            case AUDIO:
                Bukkit.getScheduler().runTaskLater(SpreenMaze.getInstance(), () -> player.playSound(player.getLocation(), parsed, 10, 1), execute);
                break;
            case PLAYER_COMMAND:
                Bukkit.getScheduler().runTaskLater(SpreenMaze.getInstance(), () -> Bukkit.dispatchCommand(player, parsed), execute);
                break;
            case CONSOLE_COMMAND:
                Bukkit.getScheduler().runTaskLater(SpreenMaze.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), parsed), execute);
                break;
            case END:
                Bukkit.getScheduler().runTaskLater(SpreenMaze.getInstance(), () -> SpreenMaze.getInstance().getUserManager().getUserOrDie(player.getUniqueId()).setDialog(null), execute);
                break;
            default:
                throw new IllegalArgumentException("How the actually fuck this happened? Type is " + type);
        }
    }

    @Override
    public String toString() {
        JsonObject json = new JsonObject();

        json.addProperty("type", type.toString());
        json.addProperty("args", args);
        json.addProperty("execute", execute);

        return SpreenMaze.getInstance().getFancyGson().toJson(json);
    }

    public static DialogPart fromString(String json) {
        return fromJsonObject(SpreenMaze.getInstance().getFancyGson().fromJson(json, JsonObject.class));
    }

    public static DialogPart fromJsonObject(JsonObject jsonObject) {
        DialogPartType dialogPartType = DialogPartType.valueOf(jsonObject.get("type").getAsString());
        String args = jsonObject.get("args").getAsString();
        long execute = jsonObject.get("execute").getAsLong();

        return new DialogPart(dialogPartType, args, execute);
    }
}