package net.javaci.training.socketThread._03_simpleThread;

public class RunThreadTogether {
    public static void main(String[] args) {
        new Thread(PrintUtil::print100TimesThreadName).start();

        PrintUtil.print100TimesThreadName();
    }
}
