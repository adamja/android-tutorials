package net.adamja.kotlintutorials.Threading.T1_parallelthreads;

import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Source: https://www.youtube.com/watch?v=v0ZSnISeyKE&ab_channel=MindOrks
 */
public class ParallelThreadsMainExample {
    private static final String TAG = "Main";

    public static void main() {

        AtomicBoolean processing = new AtomicBoolean(true);

        new Executor.Builder()
                .add(() -> {
                    Log.i(TAG, "main: TASK 1 Start");

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "main: TASK 1 Exception", e);
                    }
                    Log.i(TAG, "main: TASK 1 Complete");
                })
                .add(() -> {
                    Log.i(TAG, "main: TASK 2 Start");

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "main: TASK 2 Exception", e);
                    }
                    Log.i(TAG, "main: TASK 2 Complete");
                })
                .add(() -> {
                    Log.i(TAG, "main: TASK 3 Start");

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "main: TASK 3 Exception", e);
                    }
                    Log.i(TAG, "main: TASK 3 Complete");
                })
                .callback(() -> {
                    processing.set(false);
                })
                .build()
                .execute();

        while (processing.get()) {
            // keep running
        }

        Log.i(TAG, "main: Program Terminated");
    }
}