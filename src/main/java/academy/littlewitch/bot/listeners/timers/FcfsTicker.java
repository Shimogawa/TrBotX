package academy.littlewitch.bot.listeners.timers;

import cn.hutool.cron.task.Task;

import java.util.LinkedList;

/**
 * A ticker that can do tasks every given time.
 */
public class FcfsTicker extends Ticker {

    private LinkedList<Task> taskPool = new LinkedList<>();

    /**
     * Create a ticker which ticks (does tasks) every given milliseconds.
     *
     * @param milliseconds The interval.
     */
    public FcfsTicker(long milliseconds) {
        super(milliseconds);
    }

    /**
     * Create a ticker which ticks (does tasks) every given seconds.
     * @param seconds The interval.
     */
    public FcfsTicker(int seconds) {
        super(seconds * 1000L);
    }

    /**
     * Add a task that will do at the end of current task list.
     * @param task
     * @return
     */
    @Override
    public FcfsTicker addTask(Task task) {
        taskPool.add(task);
        return this;
    }

    /**
     * Add a task that will do at the first of current task list.
     * @param task
     * @return
     */
    public FcfsTicker addTaskFirstPriority(Task task) {
        taskPool.addFirst(task);
        return this;
    }

    @Override
    public void run() {
        while (true) {
            for (Task t : taskPool) {
                t.execute();
            }
            try {
                Thread.sleep(tickInterval);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
