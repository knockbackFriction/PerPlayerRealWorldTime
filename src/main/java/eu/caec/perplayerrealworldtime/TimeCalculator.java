package eu.caec.perplayerrealworldtime;

import java.time.Instant;

public class TimeCalculator {
    float TIME_OFFSET_1_LONGITUDE = 43200/180.0f;
    int SECONDS_1_DAY = 86400;

    public int naiveGpsToTime(float lon) {
        int timesnapshot = (int) (Instant.now().getEpochSecond() % SECONDS_1_DAY);
        float t = timesnapshot + (TIME_OFFSET_1_LONGITUDE*lon) + SECONDS_1_DAY;
        t /= 3.6f;
        t -= 6000; //no clue why i am offset by 6 hours
        return (int) t;
    }
}
