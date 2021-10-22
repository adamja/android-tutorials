package net.adamja.kotlintutorials.Threading.T4_android_simple_handler;

import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleWorker extends Thread {

    private static final String TAG = "SimpleWorker";

    private AtomicBoolean alive = new AtomicBoolean(true);
    private ConcurrentLinkedQueue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();

    // --- Constructor
    public SimpleWorker() {
        super(TAG);
        start();
    }

    // --- Overrides
    @Override
    public void run() {
        while(alive.get()) {
            Runnable task = taskQueue.poll();
            if (task != null) {
                Log.i(TAG, "run: Start");
                task.run();
                Log.i(TAG, "run: End");
            }
        }
    }

    // --- Functions
    public SimpleWorker execute(Runnable task) {
        taskQueue.add(task);
        return this;
    }

    public void quit() {
        alive.set(false);
    }
}