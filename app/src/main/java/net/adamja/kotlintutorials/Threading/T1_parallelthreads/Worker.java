package net.adamja.kotlintutorials.Threading.T1_parallelthreads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class Worker implements Runnable {
    private Thread thread;
    private Runnable task;
    private AtomicBoolean started;
    private CountDownLatch latch;

    public Worker(Runnable task, CountDownLatch latch) {
        this.task = task;
        thread = new Thread(this);
        this.latch = latch;
        started = new AtomicBoolean(false);
    }

    public void start() {
        if (!started.getAndSet(true)) {
            thread.start();
        }
    }

    @Override
    public void run() {
        task.run();
        latch.countDown();
    }
}