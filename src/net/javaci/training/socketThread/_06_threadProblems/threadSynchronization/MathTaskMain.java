package net.javaci.training.socketThread._06_threadProblems.threadSynchronization;

class MathTask extends Thread {
    private int start;
    private int end;
    private int result;

    public MathTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        result = 0;
        for (int i = start; i <= end; i++) {
            result += i;
        }
    }

    public int getResult() {
        return result;
    }
}

public class MathTaskMain {
    public static void main(String[] args) {
        int total = 0;
        MathTask[] tasks = new MathTask[5];
        int start = 1;
        int end = 100;

        for (int i = 0; i < 5; i++) {
            tasks[i] = new MathTask(start, end);
            tasks[i].start();
            start = end + 1;
            end += 100;
        }

        for (MathTask task : tasks) {
            try {
                task.join();
                total += task.getResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Toplam Sonuç: " + total);
    }
}
