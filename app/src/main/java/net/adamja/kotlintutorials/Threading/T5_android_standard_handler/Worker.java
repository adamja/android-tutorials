package net.adamja.kotlintutorials.Threading.T5_android_standard_handler;

import android.os.Handler;
import android.os.HandlerThread;

public class Worker extends HandlerThread {

    private Handler handler;

    private static final String TAG = "Worker";

    // --- Constructor
    public Worker() {
        super(TAG);

        // start the thread
        start();
        handler = new Handler(getLooper());
    }

    public Worker execute(Runnable task) {
        handler.post(task);
        return this;
    }
}