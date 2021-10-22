package net.adamja.kotlintutorials.Threading.T1_parallelthreads;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class Executor extends Thread {
    private static final String TAG = "Executor";

    private ConcurrentLinkedQueue<Worker> workers;
    private Callback callback;
    private CountDownLatch latch;

    public Executor(List<Runnable> tasks, Callback callback) {
        this.callback = callback;
        workers = new ConcurrentLinkedQueue<>();
        latch = new CountDownLatch(tasks.size());

        for (Runnable task : tasks) {
            workers.add(new Worker(task, latch));
        }
    }

    public void execute() {
        start();
    }

    @Override
    public void run() {
        // Start will call run down the chain
        while (true) {
            Worker worker = workers.poll();

            if (worker == null) {
                break;
            }
            worker.start();
        }
        try {
            latch.await();

        } catch (InterruptedException e) {
            Log.e(TAG, "run: ", e);
        }

        if (callback != null) {
            callback.onComplete();
        }
    }

    public static class Builder {
        private List<Runnable> tasks = new ArrayList<>();
        private Callback callback;

        public Builder add(Runnable task) {
            tasks.add(task);
            return this;
        }

        public Builder callback(Callback callback) {
            this.callback = callback;
            return this;
        }

        public Executor build() {
            return new Executor(tasks, callback);
        }
    }


    public interface Callback {
        void onComplete();
    }

}