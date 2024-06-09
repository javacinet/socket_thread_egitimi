package net.javaci.training.socketThread._07_simpleCrawler;

import java.util.Scanner;
import java.util.ArrayList;

public class WebCrawler {

    static ArrayList<String> listOfPendingURLs = new ArrayList<>();
    static ArrayList<String> listOfTraversedURLs = new ArrayList<>();
    static String startingURL;

    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a URL: ");
        startingURL = "https://t24.com.tr/"; //input.nextLine();
        crawler(); // Traverse the Web from the a starting url
    }

    public static void crawler() throws InterruptedException {

        int counter = 1;
        listOfPendingURLs.add(startingURL);
        while (!listOfPendingURLs.isEmpty() &&
                listOfTraversedURLs.size() <= 100) {
            new GetURL().start();
            Thread.sleep(50);
        }
    }



    public static ArrayList<String> getSubURLs(String urlString, String startingURL) {
        ArrayList<String> list = new ArrayList<>();

        try {
            java.net.URL url = new java.net.URL(urlString);
            Scanner input = new Scanner(url.openStream());
            int current = 0;
            while (input.hasNext()) {
                String line = input.nextLine();
                current = line.indexOf("https:", current);
                while (current > 0) {
                    int endIndex = line.indexOf("\"", current);
                    if (endIndex > 0) { // Ensure that a correct URL is found
                        String link = line.substring(current, endIndex);
                        if (link.contains(startingURL)) {
                            list.add(link);
                        }
                        current = line.indexOf("https:", endIndex);
                    } else
                        current = -1;
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return list;
    }

    static class GetURL extends Thread {
        @Override
        public void run() {
            String urlString;
            synchronized (listOfPendingURLs) {
                urlString = listOfPendingURLs.remove(0);
            }
            if (!listOfTraversedURLs.contains(urlString)) {
                listOfTraversedURLs.add(urlString);

                for (String s : getSubURLs(urlString, startingURL)) {
                    synchronized (listOfPendingURLs) {
                        if (!listOfTraversedURLs.contains(s))
                            listOfPendingURLs.add(s);
                    }
                }
            }
        }
    }
}

