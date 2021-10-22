package net.adamja.kotlintutorials.Threading.T3_threadpoolexecutor;

import android.util.Log;

import net.adamja.kotlintutorials.Threading.T3_threadpoolexecutor.model.Comment;
import net.adamja.kotlintutorials.Threading.T3_threadpoolexecutor.model.Friend;
import net.adamja.kotlintutorials.Threading.T3_threadpoolexecutor.model.Like;
import net.adamja.kotlintutorials.Threading.T3_threadpoolexecutor.model.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Source https://www.youtube.com/watch?v=0m683RQ24B4&list=PL6nth5sRD25hCXGGnG3LXMTgWAwnS31r8&index=5&ab_channel=MindOrks
 */
public class RemoteService {
    private static final String TAG = "RemoteService";

    private static int cores = Runtime.getRuntime().availableProcessors();
    private static ExecutorService executor = Executors.newFixedThreadPool(cores + 1);

    public void stop() {
        executor.shutdown();
    }

    public void getUserRecentActivities(ResultCallback callback) {
        executor.execute(() -> {
            List<Like> likes = new ArrayList<>();
            List<Post> posts = new ArrayList<>();
            List<Comment> comments = new ArrayList<>();
            List<Friend> friends = new ArrayList<>();

            Future<List<Like>> futureLikes = executor.submit(getLikes("https://dummy.com/likes"));
            Future<List<Post>> futurePosts = executor.submit(getPosts("https://dummy.com/posts"));
            Future<List<Comment>> futureComments = executor.submit(getComments("https://dummy.com/comments"));
            Future<List<Friend>> futureFriends = executor.submit(getFriends("https://dummy.com/friends"));

            try {
                likes = futureLikes.get();
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "getUserRecentActivities: ", e);
            }

            try {
                posts = futurePosts.get();
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "getUserRecentActivities: ", e);
            }

            try {
                comments = futureComments.get();
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "getUserRecentActivities: ", e);
            }

            try {
                friends = futureFriends.get();
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "getUserRecentActivities: ", e);
            }

            List<Activity> activities = new ArrayList<>();
            activities.addAll(likes);
            activities.addAll(posts);
            activities.addAll(comments);
            activities.addAll(friends);

            // Sort by date
            Collections.sort(activities,
                    (activity1, activity2) -> activity1.getCreatedDate().compareTo(activity2.getCreatedDate()));

            callback.onResult(activities);
        });
    }

    private Callable<List<Like>> getLikes(String url) {
        return () -> {
            Log.i(TAG, "getLikes: called");
            Thread.sleep(2000);
            // Current millis: https://currentmillis.com/tutorials/system-currentTimeMillis.html
            return Arrays.asList(new Like(new Date(1634635090101L)), new Like(new Date(1634645080101L)));
        };
    }

    private Callable<List<Post>> getPosts(String url) {
        return () -> {
            Log.i(TAG, "getPosts: called");
            Thread.sleep(1000);
            // Current millis: https://currentmillis.com/tutorials/system-currentTimeMillis.html
            return Arrays.asList(new Post(new Date(1634635095101L)), new Post(new Date(1634645030101L)));
        };
    }

    private Callable<List<Comment>> getComments(String url) {
        return () -> {
            Log.i(TAG, "getComments: called");
            Thread.sleep(2500);
            // Current millis: https://currentmillis.com/tutorials/system-currentTimeMillis.html
            return Arrays.asList(new Comment(new Date(1634635090201L)), new Comment(new Date(1634445080101L)));
        };
    }

    private Callable<List<Friend>> getFriends(String url) {
        return () -> {
            Log.i(TAG, "getFriends: called");
            Thread.sleep(800);
            // Current millis: https://currentmillis.com/tutorials/system-currentTimeMillis.html
            return Arrays.asList(new Friend(new Date(1631635090201L)), new Friend(new Date(1634445480101L)));
        };
    }
}