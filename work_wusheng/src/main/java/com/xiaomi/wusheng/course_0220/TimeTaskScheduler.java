package com. xiaomi. wusheng. course_0220;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TimeTaskScheduler {

    static class ScheduledTask implements Delayed, Runnable {
        private final Runnable task;
        private final long executeTime;

        public ScheduledTask(Runnable task, long delayMillis) {
            this.task = task;
            this.executeTime = System.currentTimeMillis() + delayMillis;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long delay = executeTime - System.currentTimeMillis();
            return unit.convert(delay, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(this.executeTime, ((ScheduledTask) o).executeTime);
        }

        @Override
        public void run() {
            task.run();
        }
    }

    static class TaskScheduler {
        private final DelayQueue<ScheduledTask> queue = new DelayQueue<>();

        public void schedule(Runnable task, long delayMillis) {
            ScheduledTask scheduledTask = new ScheduledTask(task, delayMillis);
            queue.put(scheduledTask);
        }

        public void start() {
            Thread schedulerThread = new Thread(() -> {
                while (true) {
                    try {
                        ScheduledTask task = queue.take();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            schedulerThread.start();
        }
    }

    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        scheduler.schedule(() -> System.out.println("Task 1 executed"), 3000);
        scheduler.schedule(() -> System.out.println("Task 2 executed"), 1000);
        scheduler.schedule(() -> System.out.println("Task 3 executed"), 5000);

        scheduler.start();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Scheduler finished.");
    }
}
