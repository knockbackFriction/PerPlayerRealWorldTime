package eu.caec.perplayerrealworldtime;

import java.time.Instant;
import java.time.LocalDate;

public class TimeCalculator {
    float TIME_OFFSET_1_LONGITUDE = 43200/180.0f;
    int SECONDS_1_DAY = 86400;

    public boolean pastNoon(float lon) {
        int timesnapshot = (int) (Instant.now().getEpochSecond() % SECONDS_1_DAY);
        float t = timesnapshot + (TIME_OFFSET_1_LONGITUDE*lon) + SECONDS_1_DAY;
        t %= SECONDS_1_DAY;
        return (t > 43200);
    }

    public double calculateMeanSolarTime(float longitude) {
        int timesnapshot = (int) (Instant.now().getEpochSecond() % SECONDS_1_DAY);
        double t = timesnapshot + (TIME_OFFSET_1_LONGITUDE*longitude);
        if (t < 0) {
            t += SECONDS_1_DAY;
        }
        return t / 3600d;
    }

    //i asked the ai to do it because im bad at math
    public double calculateSunAltitude(float latitude, float longitude) {
        double meanSolarTime = calculateMeanSolarTime(longitude);
        int dayOfYear = LocalDate.now().getDayOfYear();

        // Calculate declination of the sun (in degrees)
        double declination = 23.44 * Math.sin(Math.toRadians(360.0 / 365.0 * (dayOfYear - 81)));

        double solarTime = meanSolarTime + longitude / 60.0;

        // Calculate hour angle
        double hourAngle = 15 * (solarTime - 12);

        // Calculate altitude
        double altitude = Math.toDegrees(Math.asin(
                Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(declination)) +
                        Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(declination)) * Math.cos(Math.toRadians(hourAngle))
        ));

        return altitude; // Return altitude in degrees
    }

    int FULL_MINECRAFT_DAY = 24000;
    int MINECRAFT_HORIZON_TO_ZENITH_RANGE = 6785;
    int MINECRAFT_SUNRISE_NEG = -785; // 0 degrees
    int MINECRAFT_SUNSET = 12785; // 0 degrees
    int MINECRAFT_MOONPEAK_TO_HORIZON = 5215;

    public int minecraftSunAltitude(double alt, boolean noonPassed) {
        int newalt;
        if (noonPassed) { // Noon, sunset, midnight
            if (alt > 0) {
                newalt = (int) ( MINECRAFT_SUNSET + ((alt/-90)*MINECRAFT_HORIZON_TO_ZENITH_RANGE) );
            } else {
                newalt = (int) ( MINECRAFT_SUNSET + ((alt/-90)*MINECRAFT_MOONPEAK_TO_HORIZON) );
            }
        } else { // Midnight, sunrise, noon
            if (alt > 0) {
                newalt = (int) ( MINECRAFT_SUNRISE_NEG + ((alt/90)*MINECRAFT_HORIZON_TO_ZENITH_RANGE) );
            } else {
                newalt = (int) ( MINECRAFT_SUNRISE_NEG + ((alt/90)*MINECRAFT_MOONPEAK_TO_HORIZON) );
            }
        }
        return (newalt + FULL_MINECRAFT_DAY);
    }
}
