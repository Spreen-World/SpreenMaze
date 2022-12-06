package im.thatneko.spreenmaze.user;

import im.thatneko.spreenmaze.SpreenMaze;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;
import java.util.UUID;
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
public class UserManager {
    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    public UserManager (SpreenMaze spreenMaze) {
        File userFile = new File(spreenMaze.getDataFolder(), "users");

        if (!userFile.exists()) {
            userFile.mkdir();
        }
    }

    /**
     * Creates a new user, first checks if {@linkplain Player#getUniqueId()}
     * is on the , if not we create a new {@linkplain User}
     * and adds it into {@linkplain UserManager#users} map.
     * We make preconditions to prevent some errors.
     * Preconditions are:
     *   - Checks if {@linkplain UserManager#users} is null
     *
     * @param player: Bukkit's player: this is used to get UUID.
     */
    public User createUser(Player player) {
        User user = users.get(player.getUniqueId());
        if (user == null) {
            // Creates User using Player :3
            user = new User(player.getUniqueId());
            users.put(player.getUniqueId(), user);
        }
        return user;
    }

    /**
     * Deletes a user from {@linkplain UserManager#users} using
     * {@linkplain Player#getUniqueId()}
     *
     * @param player: Bukkit's player: this is used to get UUID.
     */
    public User deleteUserOrDie(Player player) {
        User user = users.get(player.getUniqueId());
        if (user != null) {
            users.remove(player.getUniqueId());
        }
        return user;
    }

    /**
     * Obtains a user from {@linkplain UserManager#users} using
     * {@linkplain Player#getUniqueId()}
     *
     * @param uuid: Player's uuid: this is used to get user.
     * @return {@linkplain User}: Otto's user.
     */
    public User getUserOrDie(UUID uuid) {
        return users.get(uuid);
    }
}
