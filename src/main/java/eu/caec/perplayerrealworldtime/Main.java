package eu.caec.perplayerrealworldtime;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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

        new BukkitRunnable() {
            @Override
            public void run() {
                updateTime();
            }
        }.runTaskTimer(this, 0, updateInterval);
    }

    public void updateTime() {
        for (Player p : getServer().getOnlinePlayers()) {
            float lon = coordCalculator.calcLongitude(p.getLocation().getBlockX());
            double altitude = timeCalculator.calculateSunAltitude(
                    coordCalculator.calcLatitude(p.getLocation().getBlockZ()),
                    lon );

            boolean pastNoon = timeCalculator.pastNoon( lon );
            p.setPlayerTime(timeCalculator.minecraftSunAltitude(altitude, pastNoon), false);
        }
    }
}
