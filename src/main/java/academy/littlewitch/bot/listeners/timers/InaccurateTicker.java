package academy.littlewitch.bot.listeners.timers;

import academy.littlewitch.utils.Action;

public class InaccurateTicker implements Runnable {

    private long interval;

    private Action task;

    private boolean isRunning;

    private final boolean sleepFirst;

    public InaccurateTicker(long ms, Action action) {
        this(ms, action, true);
    }

    public InaccurateTicker(long ms, Action action, boolean sleepFirst) {
        if (ms <= 0)
            throw new IllegalArgumentException("Invalid interval");
        this.interval = ms;
        this.task = action;
        this.sleepFirst = sleepFirst;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        if (interval <= 0)
            throw new IllegalArgumentException("Invalid interval");
        this.interval = interval;
    }


    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            if (!sleepFirst) {
                task.exec();
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                isRunning = false;
            }
            if (sleepFirst) {
                task.exec();
            }
        }
    }

    public void stop() {
        this.isRunning = false;
    }
}
