package net.adamja.kotlintutorials.Threading.T4_android_simple_handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import net.adamja.kotlintutorials.R;


public class TutorialMainSimpleWorker extends DialogFragment {
    private static final String TAG = "TutorialMainLooper";
    private Activity mActivity;
    private Dialog mDialog;

    private SimpleWorker worker;
    private TextView txtMessage;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            txtMessage.setText(msg.obj.toString());
        }
    };


    // --- Constructor
    public TutorialMainSimpleWorker(Activity mActivity) {
        this.mActivity = mActivity;
    }


    // --- Overrides
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);

        worker = new SimpleWorker();

        worker
                .execute(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = Message.obtain();
                    message.obj = "Task 1 completed";
                    handler.sendMessage(message);
                })

                .execute(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = Message.obtain();
                    message.obj = "Task 2 completed";
                    handler.sendMessage(message);
                })

                .execute(() -> {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Message message = Message.obtain();
                    message.obj = "Task 3 completed";
                    handler.sendMessage(message);
                });

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(mActivity).inflate(R.layout.single_text_component_dialog, null);

        txtMessage = view.findViewById(R.id.txtMessage);
        txtMessage.setText("Message");

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setTitle("Android Looper Tutorial")
                .setView(view)
                .setPositiveButton("Close", (dialog, which) -> {
                    dialog.dismiss();
                });

        mDialog = builder.create();

        return mDialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        worker.quit();
    }
}