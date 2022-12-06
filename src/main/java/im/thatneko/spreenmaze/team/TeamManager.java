package im.thatneko.spreenmaze.team;

import im.thatneko.spreenmaze.SpreenMaze;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class TeamManager {
    private final Map<String, MazeTeam> teams = new ConcurrentHashMap<>();
    private final Map<Player, MazeTeam> playerTeams = new ConcurrentHashMap<>();

    public TeamManager(SpreenMaze spreenMaze) {
        if (!spreenMaze.getConfig().getBoolean("load-teams")) {
            System.out.println("ignoring teams load");
            return;
        }
        spreenMaze.getConfig().getConfigurationSection("team").getKeys(false).forEach(s -> {
            teams.put(s, new MazeTeam(s));
        });
    }

    public void tryAdd(Player player) {
        teams.forEach((s, mazeTeam) -> {
            if (mazeTeam.getCuboid().isIn(player.getLocation())) {
                if (mazeTeam.getPlayers().contains(player)) {
                    mazeTeam.accept(player);
                    return;
                }
                if (mazeTeam.isFull()) {
                    mazeTeam.reject(player);
                } else {
                    mazeTeam.accept(player);
                    mazeTeam.getPlayers().add(player);
                }
            }
        });
    }
}