package kol1_2018_secondTry;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KVSServer {
    public static void main(String[] args) throws IOException {
        Map<String, String> keyValuePairs = new HashMap<>();
        ServerSocket serverSocket = new ServerSocket(0);
        System.out.println("KVSServer is listening on port " + serverSocket.getLocalPort() + "...");

        String key = login(serverSocket);
        //System.out.println(key);
        // logout(key);
        String kvsServer = requestForRandomKVSServer(key);
        System.out.println(kvsServer);

        takeTheListOfKeyValuePairs(kvsServer, keyValuePairs);
        System.out.println(keyValuePairs.size());


        while(true){
            Socket conn = serverSocket.accept();
            System.out.println("new request after accept");
            Scanner scanner = new Scanner(conn.getInputStream());

            if(scanner.hasNextLine()) {
                String request = scanner.nextLine();
                if(request.equals(Protocol.requestForKeyValuePairsList)){
                    System.out.println("new keyValuePairsList request");
                    handleKeyValuePairsList(conn, keyValuePairs);
                }
            }
        }
    }

    private static String login(ServerSocket serverSocket) throws IOException {
        Socket socket = new Socket(Protocol.centralServerAddress, Protocol.centralServerPort);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        Scanner scanner = new Scanner(socket.getInputStream());

        printWriter.println(Protocol.loginKVSServer + serverSocket.getLocalPort());
        printWriter.flush();

        String key = null;
        if(scanner.hasNextLine()){
            key = scanner.nextLine();
        }

        return key;
    }

    private static void logout(String key) throws IOException {
        Socket socket = new Socket(Protocol.centralServerAddress, Protocol.centralServerPort);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

        printWriter.println(Protocol.logoutKVSServer + key);
        printWriter.flush();
        printWriter.close();
    }

    private static String requestForRandomKVSServer(String key) throws IOException {
        Socket socket = new Socket(Protocol.centralServerAddress, Protocol.centralServerPort);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        Scanner scanner = new Scanner(socket.getInputStream());

        printWriter.println(Protocol.requestForRandomKVSServer + key);
        printWriter.flush();

        String kvsServer = null;
        if(scanner.hasNextLine()){
            kvsServer = scanner.nextLine();
            // System.out.println();
        }
        return kvsServer;
    }

    private static void takeTheListOfKeyValuePairs(String kvsServer, Map<String, String> keyValuePairs) throws IOException {
        String[] address_port = kvsServer.split(" ");
        String address = address_port[0];
        int port = Integer.parseInt(address_port[1]);

        Socket socket = new Socket(address, port);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println(Protocol.requestForKeyValuePairsList);
        printWriter.flush();

        Scanner scanner = new Scanner(socket.getInputStream());
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            System.out.println(line);
            if(line.equals("END")){
                break;
            }
            String key = line.split(" ")[0];
            String value = line.split(" ")[1];

            keyValuePairs.put(key, value);
        }
    }

    private static void handleKeyValuePairsList(Socket conn, Map<String, String> keyValuePairs) throws IOException {
        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());

        for(Map.Entry<String, String> entry: keyValuePairs.entrySet()){
            printWriter.println(entry.getKey() + " " + entry.getValue());
            printWriter.flush();
        }

        printWriter.println("END");
        printWriter.flush();
        printWriter.close();
    }
}
