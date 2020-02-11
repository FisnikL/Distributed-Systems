package kol1_2018_secondTry;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class CentralServer {
    public static Map<String, KVSServerObj> kvsServers = new HashMap<>();
    public static Map<String, String> keyValuePairs = new HashMap<>();

    static{
        keyValuePairs.put("key1", "value1");
        keyValuePairs.put("key2", "value2");
        keyValuePairs.put("key3", "value3");
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Protocol.centralServerPort);
        System.out.println("Central Server is listening on port " + serverSocket.getLocalPort() + "...");

        while(true){
            System.out.println("KVSServers: " + kvsServers.size());
            Socket conn = serverSocket.accept();
            System.out.println("new request after accept");
            Scanner scanner = new Scanner(conn.getInputStream());
            // PrintWriter printWriter = new PrintWriter(conn.getOutputStream());

            if(scanner.hasNextLine()){
                String request = scanner.nextLine();
                if(request.startsWith(Protocol.loginKVSServer)){
                    System.out.println("new kvs login request");
                    handleKVSLogin(conn, request);
                }
                else if(request.startsWith(Protocol.logoutKVSServer)){
                    System.out.println("new kvs logout request");
                    handleKVSLogout(conn, request);
                }
                else if(request.startsWith(Protocol.requestForRandomKVSServer)){
                    System.out.println("new randomKVSServer request");
                    handleRandomKVSServer(conn, request);
                }
                else if(request.equals(Protocol.requestForKeyValuePairsList)){
                    System.out.println("new keyValuePairsList request");
                    handleKeyValuePairsList(conn);
                }
                else if(request.equals(Protocol.randomKVSServerForClient)){
                    System.out.println("new randomKVSServerForClient request");
                    handleRandomKVSServerForClient(conn);
                }
            }
        }
    }

    private static void handleKVSLogin(Socket conn, String request) throws IOException {
        String address = conn.getInetAddress().getHostAddress();
        int port = conn.getPort();

        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());

        String key = "KVSServer" + (kvsServers.size() + 1);
        int listeningPort = Integer.parseInt(request.replace(Protocol.loginKVSServer, ""));

        KVSServerObj newKVSServer = new KVSServerObj(address, port, listeningPort);

        kvsServers.put(key, newKVSServer);

        printWriter.println(key);
        printWriter.flush();
        printWriter.close();
        conn.close();
    }

    private static void handleKVSLogout(Socket conn, String request) throws IOException {
        String key = request.replace(Protocol.logoutKVSServer, "");
        if(kvsServers.containsKey(key)){
            System.out.println("Removing " + key);
            kvsServers.remove(key);
        }else{
            // Poraka za greshka
        }
        conn.close();
    }

    private static void handleRandomKVSServer(Socket conn, String request) throws IOException {
        String address = conn.getInetAddress().getHostAddress();
        int port = conn.getPort();
        String key = request.replace(Protocol.requestForRandomKVSServer, "");

        // If the only KVSServer is the one that is requesting random KVSServer
        if(kvsServers.containsKey(key)){
            if(kvsServers.size() == 1){
                PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
                printWriter.println(Protocol.centralServerAddress + " " + Protocol.centralServerPort);
                printWriter.flush();
                printWriter.close();
                return;
            }

            // If there are more
            KVSServerObj[] kvsServerObjs = new KVSServerObj[kvsServers.size()];
            kvsServers.values().toArray(kvsServerObjs);
            Random random = new Random();
            while(true){
                int index = random.nextInt(kvsServerObjs.length);
                if(kvsServerObjs[index].getAddress() != kvsServers.get(key).getAddress() || kvsServerObjs[index].getPort() != kvsServers.get(key).getPort()){
                    PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
                    printWriter.println(kvsServerObjs[index].getAddress() + " " + kvsServerObjs[index].getListeningPort());
                    printWriter.flush();
                    printWriter.close();
                    break;
                }
            }
        }else{
            // poraka za greshka
        }
    }

    private static void handleKeyValuePairsList(Socket conn) throws IOException {
        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
        for(Map.Entry<String, String> entry: keyValuePairs.entrySet()){
            printWriter.println(entry.getKey() + " " + entry.getValue());
            printWriter.flush();
        }
        printWriter.println("END");
        printWriter.flush();
        printWriter.close();
    }

    private static void handleRandomKVSServerForClient(Socket conn) throws IOException {
        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());

        if(kvsServers.size() == 0){
            printWriter.println(Protocol.centralServerAddress + " " + Protocol.centralServerPort);
            printWriter.flush();
            printWriter.close();
            return;
        }

        KVSServerObj[] kvsServerObjs = new KVSServerObj[kvsServers.size()];
        kvsServers.values().toArray(kvsServerObjs);
        Random random = new Random();
        int index = random.nextInt(kvsServerObjs.length);
        printWriter.println(kvsServerObjs[index].getAddress() + " " + kvsServerObjs[index].getListeningPort());
        printWriter.flush();
        printWriter.close();
    }
}
