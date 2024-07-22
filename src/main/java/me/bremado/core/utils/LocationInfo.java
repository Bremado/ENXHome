package me.bremado.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationInfo {

    private String worldName;

    private Double x;
    private Double y;
    private Double z;
    private Float yaw;
    private Float pitch;

    public LocationInfo(Location location) {
        this.worldName = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    public Location toLocation() {
        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }

    @Override
    public String toString() {
        return worldName + " : " + x + " : " + y + " : " + z + " : " + yaw + " : " + pitch;
    }

    public static LocationInfo fromString(String string) {
        var parts = string.split(" : ");
        var worldName = parts[0];
        var x = Double.parseDouble(parts[1]);
        var y = Double.parseDouble(parts[2]);
        var z = Double.parseDouble(parts[3]);
        var yaw = Float.parseFloat(parts[4]);
        var pitch = Float.parseFloat(parts[5]);
        return new LocationInfo(new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch));
    }
}
