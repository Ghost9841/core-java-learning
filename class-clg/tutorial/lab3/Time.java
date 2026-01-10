public class Time{
    private long hours;
    private long minutes;
    private long seconds;
    public long getHours() {
        return hours;
    }
    public long getMinutes() {
        return minutes;
    }
    public long getSeconds() {
        return seconds;
    }
    Time(){
        long totalSeconds = System.currentTimeMillis() / 1000;
        this.hours = (totalSeconds / 3600) % 24;
        this.minutes = (totalSeconds / 60) % 60;
        this.seconds = totalSeconds % 60;
    }
    @Override
    public String toString() {
        return hours + ":" + minutes + ":" + seconds;
    }
}
