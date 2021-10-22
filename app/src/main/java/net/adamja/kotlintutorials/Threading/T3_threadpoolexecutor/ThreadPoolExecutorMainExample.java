package net.adamja.kotlintutorials.Threading.T3_threadpoolexecutor;

import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPoolExecutorMainExample {
    private static final String TAG = "ThreadPoolExecutorMainExample";
    
    public static void main() {
        Log.i(TAG, "main: Program Started");
        AtomicBoolean processing = new AtomicBoolean(true);

        RemoteService service = new RemoteService();
        service.getUserRecentActivities(activities -> {
            for (Activity activity: activities) {
                Log.i(TAG, String.valueOf(activity));
            }
            processing.set(false);
        });

        while (processing.get()) {
            // keep running
        }

        service.stop();
        Log.i(TAG, "main: Program Terminated");
    }

}