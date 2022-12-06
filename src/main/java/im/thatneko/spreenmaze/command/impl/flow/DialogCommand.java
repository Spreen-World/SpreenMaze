package im.thatneko.spreenmaze.command.impl.flow;

import im.thatneko.spreenmaze.SpreenMaze;
import im.thatneko.spreenmaze.dialogs.Dialog;
import im.thatneko.spreenmaze.dialogs.DialogManager;
import im.thatneko.spreenmaze.dialogs.DialogPart;
import im.thatneko.spreenmaze.dialogs.DialogPartType;
import im.thatneko.spreenmaze.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Named;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Command(names = {"dialog", "dialogos"})
public class DialogCommand implements CommandClass {
    @Command(names = {"code"})
    public void onCode(@Sender Player player, @Named("code") String code) {
        if (!player.hasPermission("spreenmaze.admin")) {
            return;
        }
        DialogManager dialogManager = SpreenMaze.getInstance().getDialogManager();

        User user = SpreenMaze.getInstance().getUserManager().getUserOrDie(player.getUniqueId());
        Dialog dialog = dialogManager.getDialog(user.computeLanguage() + "_" + code);

        if (dialog != null) {
            dialog.execute(player);
        }
    }

    @Command(names = {"code-for-everyone"})
    public void onCodeForEveryone(@Sender Player player, @Named("code") String code) {
        if (!player.hasPermission("spreenmaze.admin")) {
            return;
        }
        Bukkit.getOnlinePlayers().forEach(online -> {
            User user = SpreenMaze.getInstance().getUserManager().getUserOrDie(online.getUniqueId());
            DialogManager dialogManager = SpreenMaze.getInstance().getDialogManager();
            Dialog dialog = dialogManager.getDialog(user.computeLanguage() + "_" + code);

            if (dialog != null) {
                dialog.execute(online);
            }
        });
    }

    @Command(names = {"create"}, permission = "otto.admin", permissionMessage = "§cNo tienes permiso.")
    public void onCreate(@Sender Player player, @Named("name") String name) {
        DialogManager dialogManager = SpreenMaze.getInstance().getDialogManager();
        if (dialogManager.existsDialog(name)) {
            player.sendMessage(c("&cEste diálogo ya existe."));
            return;
        }

        dialogManager.create(name);

        player.sendMessage(c("&7Se ha creado con éxito el diálogo &c" + name + "&7."));
    }

    @Command(names = {"addaction"}, permission = "otto.admin", permissionMessage = "§cNo tienes permiso.")
    public void addAction(@Sender Player player, @Named("name") String name, @Named("type") String type, @Named("executeTime") Integer executeTime, @Text @Named("args") String args) {
        DialogManager dialogManager = SpreenMaze.getInstance().getDialogManager();

        Dialog dialog = dialogManager.getDialog(name);

        DialogPartType dialogPartType = DialogPartType.valueOf(type);
        long executeTimeLong = executeTime.longValue();

        if (dialog != null) {
            player.sendMessage(c("&7Agregando una parte de diálogo en &c" + name + "&7..."));
            dialog.getParts().add(new DialogPart(dialogPartType, args, executeTimeLong));
            player.sendMessage(c("&7Se ha agregado una parte de diálogo en &c" + name + "&7. Guardando..."));
            dialog.save();
            player.sendMessage(c("&7Se ha guardado el diálogo &c" + name + "&7 con éxito."));
        }
    }

    public String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}