package net.javaci.training.socketThread._06_threadProblems;

public class DataSynchronizationProblem {
    public static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        int n = 10000;

        Thread t = new Thread(() -> increment(n));
        t.start();

        increment(n);
        t.join();
        System.out.println(counter);
    }

    public static void increment(int n)  {
        for (int i = 0; i < n; i++) {
            counter++;
        }
    }
}
