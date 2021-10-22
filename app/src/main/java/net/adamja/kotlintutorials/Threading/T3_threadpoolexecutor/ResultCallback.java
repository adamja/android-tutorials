package net.adamja.kotlintutorials.Threading.T3_threadpoolexecutor;

import java.util.List;

public interface ResultCallback {

    void onResult(List<Activity> activities);
}
