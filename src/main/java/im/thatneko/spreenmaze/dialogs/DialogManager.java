package im.thatneko.spreenmaze.dialogs;

import im.thatneko.spreenmaze.SpreenMaze;
import lombok.Getter;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
@SuppressWarnings("all")
public class DialogManager {

    private final ConcurrentHashMap<String, Dialog> dialogs = new ConcurrentHashMap<>();

    public DialogManager() {
        File dialogFolder = new File(SpreenMaze.getInstance().getDataFolder(), "dialogs");
        if (!dialogFolder.exists()) {
            dialogFolder.mkdir();
        }

        for (File file : dialogFolder.listFiles()) {
            String name = file.getName();
            if (name.endsWith(".json")) {
                String realName = name.replace(".json", "");
                dialogs.put(realName, Dialog.load(realName));
            }
        }
    }

    public Dialog getDialog(String name) {
        return dialogs.get(name);
    }

    public Dialog create(String name) {
        Dialog dialog = new Dialog(name);
        dialog.save();
        dialogs.put(name, dialog);

        return dialog;
    }

    public boolean existsDialog(String name) {
        return getDialog(name) != null;
    }

}
