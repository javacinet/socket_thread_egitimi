package net.javaci.training.socketThread._03_simpleThread;

public class HowToCreateThread {
    public static void main(String[] args) {

        PrintUtil.printThreadName();

        // type 1
        new Thread(PrintUtil::printThreadName).start();

        // type 2
        new Thread(() -> PrintUtil.printThreadName()).start();

        // type 3
        new Thread(new Runnable() {
            @Override
            public void run() {
                PrintUtil.printThreadName();
            }
        }).start();

        // type 4
        new Thread(new MyRunnable()).start();

        // type 5
        new MyThread().start();

    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        PrintUtil.printThreadName();
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        PrintUtil.printThreadName();
    }
}