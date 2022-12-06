package im.thatneko.spreenmaze.team;

import im.thatneko.spreenmaze.SpreenMaze;
import im.thatneko.spreenmaze.utils.Cuboid;
import im.thatneko.spreenmaze.utils.LocationUtils;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
public class MazeTeam {
    private final String name;
    private final List<Player> players;
    private final int maxPlayers;
    private final Cuboid cuboid;
    private final Location rejected;
    private final Location accepted;

    public MazeTeam(String name) {
        FileConfiguration config = SpreenMaze.getInstance().getConfig();
        this.name = name;
        this.players = new ArrayList<>();
        this.cuboid = new Cuboid(
                LocationUtils.stringToLocation(config.getString("team." + name + ".door.pos1")),
                LocationUtils.stringToLocation(config.getString("team." + name + ".door.pos2"))
        );
        this.rejected = LocationUtils.stringToLocation(config.getString("team." + name + ".rejected"));
        this.accepted = LocationUtils.stringToLocation(config.getString("team." + name + ".accepted"));
        this.maxPlayers = config.getInt("max-per-team");
    }

    public boolean isFull() {
        return players.size() >= maxPlayers;
    }

    public void reject(Player player) {
        player.setVelocity(
                rejected.toVector().subtract(
                        player.getLocation().toVector()
                ).setY(0.3)
        );
    }

    public void accept(Player player) {
        player.setVelocity(
                accepted.toVector().subtract(
                        player.getLocation().toVector()
                ).setY(0.3)
        );
    }
}