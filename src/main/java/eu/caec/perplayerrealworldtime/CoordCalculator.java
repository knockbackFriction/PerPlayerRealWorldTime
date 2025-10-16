package eu.caec.perplayerrealworldtime;

import org.bukkit.configuration.file.FileConfiguration;

public class CoordCalculator {
    FileConfiguration config = Main.getInstance().getConfig();
    int mapNorthernmostZ = config.getInt("map-northernmost-z");
    int mapWesternmostX = config.getInt("map-westernmost-x");
    int mapWidth = config.getInt("map-width");
    int mapHeight = config.getInt("map-height");

    public float getLongitude(int x) {
        x -= mapWesternmostX;
        float divider = mapWidth/360.0f;
        float lon = x/divider;
        lon -= 180;

        if (lon <= -180.0f) return -179.999f;
        if (lon >= 180.0f) return 179.999f;

        return lon;
    }

    public float getLatitude(int z) {
        z -= mapNorthernmostZ;
        float divider = mapWidth/360.0f;
        float lat = z/divider;
        lat = 90 - lat;

        if (lat <= -90.0f) return -89.999f;
        if (lat >= 90.0f) return 89.999f;

        return lat;
    }
}
