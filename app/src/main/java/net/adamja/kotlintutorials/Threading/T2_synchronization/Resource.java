package net.adamja.kotlintutorials.Threading.T2_synchronization;


import net.adamja.kotlintutorials.Threading.ULog;

import java.util.concurrent.atomic.AtomicBoolean;

public class Resource {
    private static final String TAG = "Resource";

    private AtomicBoolean disallow = new AtomicBoolean(false);

    synchronized public void setDisallow() {
        disallow.set(true);
        ULog.print(TAG, "setDisallow: Processing disallowed now");
    }

    synchronized public void process() {
        if (!disallow.get()) {
            try {
                Thread.sleep(2000);
                ULog.print(TAG, "process: I processed because it was allowed: " + disallow.get());
            } catch (InterruptedException e) {
                ULog.print(TAG, "process: ", e);
            }

        } else {
            ULog.print(TAG, "process: I could not process because it was not allowed");
        }
    }
}