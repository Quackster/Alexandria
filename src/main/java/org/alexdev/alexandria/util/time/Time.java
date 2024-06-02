package org.alexdev.alexandria.util.time;

public class Time {
    private final long hours;
    private final long minutes;
    private final long seconds;

    public Time(long hours, long minutes, long seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public long getSeconds() {
        return seconds;
    }
}
