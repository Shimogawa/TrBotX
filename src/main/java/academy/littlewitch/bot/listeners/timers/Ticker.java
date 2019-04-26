package academy.littlewitch.bot.listeners.timers;

import cn.hutool.cron.task.Task;

public abstract class Ticker implements Runnable {

    protected long tickInterval;

    /**
     * Create a ticker which ticks (does tasks) every given milliseconds.
     * @param milliseconds The interval.
     */
    public Ticker(long milliseconds) {
        this.tickInterval = milliseconds;
    }

    /**
     * Adds a task at the last.
     * @param task
     * @return
     */
    public abstract Ticker addTask(Task task);
}
