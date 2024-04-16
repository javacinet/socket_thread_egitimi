package net.javaci.training.socketThread._03_simpleThread;

public class PrintUtil {
    public static void printThreadName() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void print100TimesThreadName() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " - " + i);
        }
    }


}
