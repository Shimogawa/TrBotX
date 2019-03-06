package academy.littlewitch.bot.listeners.qungui;

import academy.littlewitch.bot.config.Configuration;

import java.util.ArrayDeque;

public class FayanRecord {
    private long qq;

    private ArrayDeque<Long> fayanTimes;

    public FayanRecord(long qq, long time) {
        this.qq = qq;
        this.fayanTimes = new ArrayDeque<>();
        fayanTimes.add(time);
    }

    public long getQq() {
        return qq;
    }

    /**
     * Call when the user says anything.
     * @return true if the user should be banned, false otherwise.
     */
    public boolean fayanCheck(long currentTime) {
        while (!fayanTimes.isEmpty() && currentTime - fayanTimes.peek() > Configuration.config.monitorConfig.cycleTime) {
            fayanTimes.poll();
        }
        fayanTimes.add(currentTime);
        return fayanTimes.size() > Configuration.config.monitorConfig.maxText;
    }
}
