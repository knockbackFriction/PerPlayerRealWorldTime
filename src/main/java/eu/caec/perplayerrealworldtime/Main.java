package eu.caec.perplayerrealworldtime;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;

public final class Main extends JavaPlugin implements Listener {
    public static Main instance;
    public static Main getInstance() {
        return instance;
    }

    CoordCalculator coordCalculator;
    TimeCalculator timeCalculator;

    int updateInterval;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);

        coordCalculator = new CoordCalculator();
        timeCalculator = new TimeCalculator();

        updateInterval = this.getConfig().getInt("time-update-interval-ticks");
    }

    int tickCounter = 0;

    @EventHandler
    public void onTick(ServerTickEndEvent e) {
        if (tickCounter % updateInterval == 0) {
            for (Player p : getServer().getOnlinePlayers()) {
                float lon = coordCalculator.calcLongitude(p.getLocation().getBlockX());
                double altitude = timeCalculator.calculateSunAltitude(
                    coordCalculator.calcLatitude(p.getLocation().getBlockZ()),
                    lon );

                boolean pastNoon = timeCalculator.pastNoon( lon );
                p.setPlayerTime(timeCalculator.minecraftSunAltitude(altitude, pastNoon), false);
            }
        }
        tickCounter++;
    }
}
