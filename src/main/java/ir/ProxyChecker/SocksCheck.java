package ir.ProxyChecker;

import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class SocksCheck {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("enter file path");
        List<String> allIp = fileToList(Path.of(scanner.nextLine()));

        List<String> successIp = addSuccessesIpPinged(allIp);

        String output = scanner.nextLine();
        ipsToFile(successIp, output);

        System.out.println("DONE");
    }

    private static List<String> fileToList(Path path) {

        try {

            File file = path.toFile();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            List<String> stringList = new ArrayList<>();

            String line;

            while ((line = br.readLine()) != null) {
                stringList.add(line);
            }

            fr.close();

            return stringList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> addSuccessesIpPinged(List<String> ipList) {
        List<String> successful = new ArrayList<>();
        ipList.forEach(ip -> {
            try {
                successful.add(sendPingRequest(ip));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return successful;
    }

    private static String sendPingRequest(String ipAddress)
            throws IOException {

        InetAddress ip = InetAddress.getByName(ipAddress.split(":")[0]);

        System.out.println("Sending Ping Request to " + ipAddress);

        if (ip.isReachable(5000)) {

            System.out.println("Host is reachable");
            return ipAddress;

        } else {

            System.out.println("Sorry ! We can't reach to this host");
            return null;

        }
    }

    private static void ipsToFile(List<String> ips, String path) {

        FileWriter writer;

        try {

            writer = new FileWriter(path, true);

            for (String ip : ips)
                writer.append(ip).append("\n");

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}