package com.github.kyleryxn.imagefinder.crawler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Custom implementation of {@link ThreadPoolExecutor} that keeps track of the last execution time of a task in the pool.
 * The last execution time is updated whenever a task completes execution. This class extends ThreadPoolExecutor and
 * overrides the {@link ThreadPoolExecutor#afterExecute(Runnable, Throwable)} method to update the last execution time atomically.
 * It also provides a getter method to access the last execution time. This class is used as a manager for handling threads in the {@link WebCrawler} application.
 *
 * @author Kyle Schoenhardt
 * @version 1.0.0
 */
public class ThreadPoolManager extends ThreadPoolExecutor {
    private final AtomicLong lastExecutionTime;

    /**
     * Constructor for ThreadPoolManager.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if they are idle.
     * @param maximumPoolSize the maximum number of threads to allow in the pool
     * @param keepAliveTime the time limit for which threads may remain idle before being terminated.
     * @param unit the time unit for the keepAliveTime parameter.
     * @param workQueue the queue to use for holding tasks before they are executed.
     */
    public ThreadPoolManager(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.lastExecutionTime = new AtomicLong(System.nanoTime()); // Get elapsed time from JVM
    }

    /**
     * Returns the last execution time of a task in the pool.
     *
     * @return the last execution time in nanoseconds.
     */
    public long getLastExecutionTime() {
        return lastExecutionTime.get();
    }

    /**
     * Overrides the {@link ThreadPoolExecutor#afterExecute(Runnable, Throwable)} method of ThreadPoolExecutor.
     *
     * @param r the runnable that has completed.
     * @param t the exception that caused termination, or null if
     * execution completed normally.
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        long currentSysNano = System.nanoTime();

        // The while loop is used purely as a spin-wait loop to repeatedly attempt to update the 'lastExecutionTime' value
        // using 'compareAndSet()' until it succeeds. The actual work is being done in the 'compareAndSet()' operation,
        // and the loop body serves as a placeholder for the spin-wait mechanism.
        // Keep trying to update 'lastExecutionTime' atomically until it succeeds
        while (true) {
            long time = lastExecutionTime.get();
            if (currentSysNano < time) break;
            if (lastExecutionTime.compareAndSet(time, currentSysNano)) {
                break;
            }
        }
    }
}
