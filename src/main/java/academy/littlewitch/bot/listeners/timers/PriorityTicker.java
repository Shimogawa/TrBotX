package academy.littlewitch.bot.listeners.timers;

import academy.littlewitch.utils.Action;

import java.util.ArrayList;
import java.util.TreeMap;

@Deprecated
public class PriorityTicker extends Ticker {

    private TreeMap<Integer, ArrayList<Action>> taskPool;

    /**
     * Create a priority ticker which ticks (does tasks) every given milliseconds.
     * The priority is higher when its level given is higher.
     *
     * @param milliseconds The interval.
     */
    public PriorityTicker(long milliseconds) {
        super(milliseconds);
        this.taskPool = new TreeMap<>();
    }

    /**
     * Adds a task to highest priority.
     * @param task
     * @return
     */
    @Override
    public PriorityTicker addTask(Action task) {
        return addTask(task, 0);
    }

    public PriorityTicker addTask(Action task, int priority) {
        if (!taskPool.containsKey(priority)) {
            taskPool.put(priority, new ArrayList<>());
        }
        taskPool.get(priority).add(task);

        return this;
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            lastExecuted = System.currentTimeMillis();
            for (Integer priority : taskPool.keySet()) {
                for (Action a : taskPool.get(priority)) {
                    a.exec();
                }
            }
            try {
                sleepAndRecalculate();
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void stop() {
        isRunning = false;
    }
}
