package ir.ProxyChecker;

import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class NewClass {

    public static void sendPingRequest(String ipAddress)
            throws IOException {
        InetAddress geek = InetAddress.getByName(ipAddress);
        List<String> ipReachable = new ArrayList<>();

        System.out.println("Sending Ping Request to " + ipAddress);
        if (geek.isReachable(5000)) {
            System.out.println("Host is reachable");

        } else
            System.out.println("Sorry ! We can't reach to this host");
    }

    public static void main(String[] args)
            throws IOException {

        List<String> ipList = fileToList();
        System.out.println(ipList);

        assert ipList != null;
        ipList.forEach(s -> {
            try {
                sendPingRequest(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

//        String ipAddress = "127.0.0.1";
//        sendPingRequest(ipAddress);
//
//        ipAddress = "133.192.31.42";
//        sendPingRequest(ipAddress);
//
//        ipAddress = "145.154.42.58";
//        sendPingRequest(ipAddress);
    }

    private static List<String> fileToList() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter file path");
        try {

            File file = Path.of(scanner.nextLine()).toFile();
            FileReader fr = new FileReader(file); 
            BufferedReader br = new BufferedReader(fr);
            List<String> stringList = new ArrayList<>();

            String line;

            while ((line = br.readLine()) != null) {
                stringList.add(line.split(":")[0]);
            }

            fr.close();
            
            return stringList;
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}