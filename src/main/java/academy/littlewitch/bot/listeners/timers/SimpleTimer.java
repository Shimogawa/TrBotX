package academy.littlewitch.bot.listeners.timers;

import academy.littlewitch.utils.Action;

public class SimpleTimer implements Runnable {

    private long refreshInterval;
    private long tickInterval;

    private boolean isRunning = false;

    private Action task;

    /**
     * A timer that checks time every 1 second and runs a action every interval.
     * @param tickInterval
     */
    public SimpleTimer(long tickInterval) {
        this(tickInterval, 1000L, null);
    }

    public SimpleTimer(long tickInterval, long refreshInterval) {
        this(tickInterval, refreshInterval, null);
    }

    public SimpleTimer(long tickInterval, long refreshInterval, Action task) {
        this.refreshInterval = refreshInterval;
        this.tickInterval = tickInterval;
        this.task = task;
    }

    public void addTask(Action action) {
        if (task == null) {
            task = action;
        }
        task = task.join(action);
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            long lastChecked = System.currentTimeMillis();
            task.exec();
            while (tickInterval - (System.currentTimeMillis() - lastChecked)
                    > refreshInterval) {
                try {
                    Thread.sleep(refreshInterval);
                } catch (InterruptedException e) {
                    isRunning = false;
                    break;
                }
            }
            while (isRunning && System.currentTimeMillis() - lastChecked < tickInterval) {}
        }
    }

    public void stop() {
        this.isRunning = false;
    }
}
