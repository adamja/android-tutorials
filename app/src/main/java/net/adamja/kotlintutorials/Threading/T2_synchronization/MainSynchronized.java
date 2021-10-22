package net.adamja.kotlintutorials.Threading.T2_synchronization;


import net.adamja.kotlintutorials.Threading.ULog;

public class MainSynchronized {
    private static final String TAG = "MainSynchronized";

    public static void main(String[] args) {
        ULog.print(TAG, "main: Program Started");
        Resource resource = new Resource();

        Thread t1 = new Thread(() -> {
            resource.process();
        });

        Thread t2 = new Thread(() -> {
            resource.setDisallow();
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            ULog.print("main:", e);
        }

        ULog.print(TAG, "main: Program Terminated");
    }
}