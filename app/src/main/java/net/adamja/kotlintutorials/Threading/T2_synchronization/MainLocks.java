package net.adamja.kotlintutorials.Threading.T2_synchronization;


import net.adamja.kotlintutorials.Threading.ULog;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainLocks {
    private static final String TAG = "MainLocks";


        public static void main(String[] args) {
            ULog.print(TAG, "Program Started");
            AtomicBoolean syncing = new AtomicBoolean(true);
            AtomicBoolean processing = new AtomicBoolean(true);

            Store store = new Store(new Store.Callback() {
                @Override
                public void onInvoiceSync() {
                    syncing.set(false);
                }

                @Override
                public void onInvoicePrepared(int total) {
                    processing.set(false);
                }
            });

            ULog.print(TAG, "syncLatestPrice() called");
            store.syncLatestPrice();
            ULog.print(TAG, "prepareInvoice() called");
            store.prepareInvoice();

            while (syncing.get() || processing.get()) {
                // running
            }

            ULog.print(TAG, "Program Terminated");
        }
}