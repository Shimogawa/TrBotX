package academy.littlewitch.bot.listeners.timers;

import academy.littlewitch.utils.Action;

public abstract class Ticker implements Runnable {

    protected long tickInterval;

    protected boolean isRunning = false;

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
    public abstract Ticker addTask(Action task);

    public abstract void stop();
}
