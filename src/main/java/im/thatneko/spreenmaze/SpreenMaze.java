package im.thatneko.spreenmaze;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import im.thatneko.spreenmaze.command.MazeCommandManager;
import im.thatneko.spreenmaze.command.impl.bukkit.ArmorProtectionCommand;
import im.thatneko.spreenmaze.command.impl.bukkit.BlocksCommand;
import im.thatneko.spreenmaze.command.impl.bukkit.LanguageCommand;
import im.thatneko.spreenmaze.command.impl.bukkit.MineroCommand;
import im.thatneko.spreenmaze.command.impl.bukkit.SaveChestsCommand;
import im.thatneko.spreenmaze.command.impl.bukkit.SpectateAllToPlayerCommand;
import im.thatneko.spreenmaze.command.impl.bukkit.ToggleTeamFaseCommand;
import im.thatneko.spreenmaze.command.impl.bukkit.UnSpectateAllCommand;
import im.thatneko.spreenmaze.dialogs.DialogManager;
import im.thatneko.spreenmaze.listener.BreakListener;
import im.thatneko.spreenmaze.listener.ChestRefillListener;
import im.thatneko.spreenmaze.listener.ConnectionListener;
import im.thatneko.spreenmaze.listener.FurnaceListener;
import im.thatneko.spreenmaze.listener.PlaceListener;
import im.thatneko.spreenmaze.tasks.ChestCheckerTask;
import im.thatneko.spreenmaze.tasks.TeamFaseTask;
import im.thatneko.spreenmaze.team.TeamManager;
import im.thatneko.spreenmaze.user.UserManager;
import im.thatneko.spreenmaze.utils.LocationUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

@Getter
public final class SpreenMaze extends JavaPlugin {
    @Getter
    private static SpreenMaze instance;
    private Gson fancyGson;

    private UserManager userManager;
    private DialogManager dialogManager;
    private TeamManager teamManager;

    private ChestCheckerTask chestCheckerTask;

    private MazeCommandManager mazeCommandManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        fancyGson = new GsonBuilder().setPrettyPrinting().create();

        Arrays.asList(
                new BreakListener(), new ChestRefillListener(), new ConnectionListener(this),
                new FurnaceListener(), new PlaceListener()
        ).forEach(listener -> Bukkit.getServer().getPluginManager().registerEvents(listener, this));

        userManager = new UserManager(this);
        dialogManager = new DialogManager();
        teamManager = new TeamManager(this);

        mazeCommandManager = new MazeCommandManager();
        getCommand("savechests").setExecutor(new SaveChestsCommand());
        getCommand("spreenmaze-blocks").setExecutor(new BlocksCommand());
        getCommand("lang").setExecutor(new LanguageCommand());
        getCommand("spectatealltoplayer").setExecutor(new SpectateAllToPlayerCommand());
        getCommand("unspectateall").setExecutor(new UnSpectateAllCommand());
        getCommand("minero").setExecutor(new MineroCommand());
        getCommand("spreenmaze-armorprotection").setExecutor(new ArmorProtectionCommand());
        getCommand("spreenmaze-toggleteamfase").setExecutor(new ToggleTeamFaseCommand());

        new TeamFaseTask().runTaskTimer(this, 20L, 20L);
        (chestCheckerTask = new ChestCheckerTask()).runTaskTimer(this, 20L, 20L);

        List<String> chestzzz = SpreenMaze.getInstance().getConfig().getStringList("chests");
        chestzzz.forEach(s -> chestCheckerTask.addToMap(LocationUtils.stringToLocation(s)));

        Bukkit.getOnlinePlayers().forEach(player -> userManager.createUser(player).load());
    }

    @Override
    public void onDisable() {
        mazeCommandManager.unload();
        Bukkit.getOnlinePlayers().forEach(player -> userManager.getUserOrDie(player.getUniqueId()).save());
    }
}
