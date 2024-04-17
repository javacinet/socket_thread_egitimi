package net.javaci.training.socketThread._06_threadProblems.threadSynchronization;

class FactorialTask extends Thread {
    private int number;
    private long result;

    public FactorialTask(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        result = 1;
        for (int i = 2; i <= number; i++) {
            result *= i;
        }
    }

    public long getResult() {
        return result;
    }
}

class FibonacciTask extends Thread {
    private int term;
    private long result;

    public FibonacciTask(int term) {
        this.term = term;
    }

    @Override
    public void run() {
        if (term <= 1)
            result = term;
        else {
            long fib = 1;
            long prevFib = 1;

            for (int i = 2; i < term; i++) {
                long temp = fib;
                fib += prevFib;
                prevFib = temp;
            }

            result = fib;
        }
    }

    public long getResult() {
        return result;
    }
}

class SquareRootTask extends Thread {
    private double number;
    private double result;

    public SquareRootTask(double number) {
        this.number = number;
    }

    @Override
    public void run() {
        result = Math.sqrt(number);
    }

    public double getResult() {
        return result;
    }
}

class CubeTask extends Thread {
    private double number;
    private double result;

    public CubeTask(double number) {
        this.number = number;
    }

    @Override
    public void run() {
        result = Math.pow(number, 3);
    }

    public double getResult() {
        return result;
    }
}

class LogarithmTask extends Thread {
    private double number;
    private double result;

    public LogarithmTask(double number) {
        this.number = number;
    }

    @Override
    public void run() {
        result = Math.log(number);
    }

    public double getResult() {
        return result;
    }
}

public class ComplexMathTaskMain {
    public static void main(String[] args) {
        FactorialTask factorialTask = new FactorialTask(5);
        FibonacciTask fibonacciTask = new FibonacciTask(10);
        SquareRootTask squareRootTask = new SquareRootTask(25);
        CubeTask cubeTask = new CubeTask(4);
        LogarithmTask logarithmTask = new LogarithmTask(100);

        factorialTask.start();
        fibonacciTask.start();
        squareRootTask.start();
        cubeTask.start();
        logarithmTask.start();

        try {
            factorialTask.join();
            fibonacciTask.join();
            squareRootTask.join();
            cubeTask.join();
            logarithmTask.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Faktöriyel Sonucu: " + factorialTask.getResult());
        System.out.println("Fibonacci Sonucu: " + fibonacciTask.getResult());
        System.out.println("Karekök Sonucu: " + squareRootTask.getResult());
        System.out.println("Küp Sonucu: " + cubeTask.getResult());
        System.out.println("Logaritma Sonucu: " + logarithmTask.getResult());
    }
}
