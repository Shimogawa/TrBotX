package academy.littlewitch.bot.listeners.qungui;

import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.utils.Pair;
import cc.moecraft.icq.event.events.message.EventGroupMessage;

import java.util.ArrayDeque;

public class FayanRecord {

    private ArrayDeque<Pair<Long, String>> repeatFayanTimes;
    private ArrayDeque<Long> fayanTimes;

    private boolean isJinyaned;

    public FayanRecord() {
        this.repeatFayanTimes = new ArrayDeque<>();
        this.fayanTimes = new ArrayDeque<>();
        isJinyaned = false;
    }

    public void setJinyaned(boolean jinyaned) {
        isJinyaned = jinyaned;
    }

    public boolean isJinyaned() {
        return isJinyaned;
    }

    public boolean fayanShuapinCheck(long currentTime) {
        if (fayanTimes.isEmpty() || currentTime - fayanTimes.peek() > 5) {
            isJinyaned = false;
        }
        while (!fayanTimes.isEmpty() && currentTime - fayanTimes.peek() >
                Configuration.config.monitorConfig.cycleTime) {
            fayanTimes.poll();
        }
        fayanTimes.add(currentTime);
        return fayanTimes.size() > Configuration.config.monitorConfig.maxText;
    }

    /**
     *
     * @param egm
     * @return true if should be banned. False otherwise.
     */
    public boolean repeatFayanCheck(EventGroupMessage egm) {
        if (repeatFayanTimes.isEmpty() || egm.time - repeatFayanTimes.peek().elem1 > 5) {
            isJinyaned = false;
        }
        repeatFayanTimes.removeIf(pair -> egm.time - pair.elem1 >
                Configuration.config.monitorConfig.checkSingleRepeatCycle ||
                !egm.rawMessage.equals(pair.elem2));

        repeatFayanTimes.add(new Pair<>(egm.time, egm.rawMessage));
        return repeatFayanTimes.size() > Configuration.config.monitorConfig.maxSingleRepeatNumber;
    }
}
