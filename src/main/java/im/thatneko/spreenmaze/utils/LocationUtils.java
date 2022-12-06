package im.thatneko.spreenmaze.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {
    public static String locationToString(Location location) {
        return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ();
    }

    public static String blockLocationToString(Location location) {
        return location.getWorld().getName() + ":" + location.getBlockX() + ":" + location.getBlockY() + ":" + location.getBlockZ();
    }

    public static Location stringToLocation(String string) {
        return stringToLocation(string.split(":"));
    }

    public static Location stringToLocation(String[] strings) {
        try {
            World world = Bukkit.getWorld(strings[0]);
            double x = Double.parseDouble(strings[1]);
            double y = Double.parseDouble(strings[2]);
            double z = Double.parseDouble(strings[3]);
            if (strings.length > 4) {
                float yaw = Float.parseFloat(strings[4]);
                float pitch = Float.parseFloat(strings[5]);
                return new Location(world, x, y, z, yaw, pitch);
            } else {
                return new Location(world, x, y, z);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while creating a location for " + String.join(", ", strings), e);
        }
    }
}