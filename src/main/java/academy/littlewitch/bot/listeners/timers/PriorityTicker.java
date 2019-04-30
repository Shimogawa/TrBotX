package academy.littlewitch.bot.listeners.timers;

import academy.littlewitch.utils.Action;
import cn.hutool.cron.task.Task;

import java.util.ArrayList;
import java.util.Hashtable;

@Deprecated
public class PriorityTicker extends Ticker {

    private int totalLevels;

    private ArrayList<Action>[] taskPool;

    /**
     * Create a priority ticker which ticks (does tasks) every given milliseconds.
     * The priority is higher when its level given is higher.
     *
     * @param milliseconds The interval.
     */
    public PriorityTicker(int totalLevels, long milliseconds) {
        super(milliseconds);
        this.totalLevels = totalLevels;
        this.taskPool = new ArrayList[totalLevels];
    }

    @Override
    public PriorityTicker addTask(Action task) {
        return addTask(task, 0);
    }

    public PriorityTicker addTask(Action task, int priority) {

        return this;
    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {

    }
}
