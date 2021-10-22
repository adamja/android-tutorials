package net.adamja.kotlintutorials.Threading;

public class ULog {

    public static void print(String tag, String message, Exception e) {
        System.out.println(tag + " | " + message);
        e.printStackTrace();
    }

    public static void print(String message, Exception e) {
        System.out.println(message);
        e.printStackTrace();
    }

    public static void print(Exception e) {
        e.printStackTrace();
    }

    public static void print(String tag, String message) {
        System.out.println(tag + " | " + message);
    }

    public static void print(String message) {
        System.out.println(message);
    }
}