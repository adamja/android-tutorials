package net.adamja.kotlintutorials.Threading.T2_synchronization;

import net.adamja.kotlintutorials.Threading.ULog;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Class to demonstrate locks
 */
public class Store {
    private static final String TAG = "Store";

    private List<Item> items = Arrays.asList(
      new Item("Chair", 20),
      new Item("Table", 15),
      new Item("Lamp", 10)
    );

    private ExecutorService executor = Executors.newFixedThreadPool(2);
    private Callback callback;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public Store(Callback callback) {
        this.callback = callback;
    }

    public void syncLatestPrice() {
        Lock writeLock = lock.writeLock();
        executor.execute(() -> {
            writeLock.lock();
            try {
                Thread.sleep(500);
                items.get(0).setValue(35);
                items.get(1).setValue(20);
                items.get(2).setValue(10);
                ULog.print(TAG, "syncLatestPrice | Price Hiked: " + getTotal());
                callback.onInvoiceSync();

            } catch (InterruptedException e) {
                ULog.print(e);
            }
            writeLock.unlock();
        });
    }

    public void prepareInvoice() {
        Lock readLock = lock.readLock();
        executor.execute(() -> {
            readLock.lock();
            int total = getTotal();
            ULog.print(TAG, "prepareInvoice | Your invoice is for the amount: " + total);
            callback.onInvoicePrepared(total);
            readLock.unlock();
        });
    }

    private int getTotal() {
        int total = 0;
        for (Item item: items) {
            total += item.getValue();
        }

        return total;
    }

    public interface Callback {
        void onInvoiceSync();
        void onInvoicePrepared(int total);
    }
}