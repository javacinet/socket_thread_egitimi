package net.javaci.training.socketThread._06_threadProblems.dataSynchronation;

public class DataSynchronizationProblem {
    public static volatile int counter = 0;

    static Object lockObject = new Object();

    public static void main(String[] args) throws InterruptedException {
        int n = 10000;

        Thread t = new Thread(() -> increment(n));
        t.start();

        // Thread.sleep(1000);

        increment(n);

        t.join();
        System.out.println("sonra: " + counter);
    }

    public static void increment(int n)  {
        for (int i = 0; i < n; i++) {
            synchronized (lockObject) {
                counter++;
            }
        }
    }
}
