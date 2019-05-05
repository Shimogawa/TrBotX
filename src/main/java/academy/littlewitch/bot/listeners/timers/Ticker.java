package academy.littlewitch.bot.listeners.timers;

import academy.littlewitch.utils.Action;

public abstract class Ticker implements Runnable {

    protected final long tickInterval;

    protected boolean isRunning = false;

    protected long lastExecuted;

    protected long nextInterval;

    protected long executionDelta = 0;

    /**
     * Create a ticker which ticks (does tasks) every given milliseconds.
     * @param milliseconds The interval.
     */
    public Ticker(long milliseconds) {
        this.tickInterval = milliseconds;
        this.nextInterval = milliseconds;
    }

    /**
     * Adds a task at the last.
     * @param task
     * @return
     */
    public abstract Ticker addTask(Action task);

    public abstract void stop();

    protected void sleepAndRecalculate() throws InterruptedException {
        executionDelta = System.currentTimeMillis() - lastExecuted;
        Thread.sleep(nextInterval - executionDelta);
        long thisInterval = System.currentTimeMillis() - lastExecuted;
        long delta = thisInterval + executionDelta;
        nextInterval = tickInterval - delta;
    }
}
