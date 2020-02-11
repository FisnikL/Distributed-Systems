package kol1_2018_secondTry;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(Protocol.centralServerAddress, Protocol.centralServerPort);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        Scanner scanner = new Scanner(socket.getInputStream());

        printWriter.println(Protocol.randomKVSServerForClient);
        printWriter.flush();

        String kvsServer = null;
        if(scanner.hasNextLine()){
            kvsServer = scanner.nextLine();
            System.out.println(kvsServer);
        }

        String address = kvsServer.split(" ")[0];
        int port = Integer.parseInt(kvsServer.split(" ")[1]);

        socket = new Socket(address, port);

        printWriter = new PrintWriter(socket.getOutputStream());

        printWriter.println(Protocol.keyValuePairFromKVSServerRequest);
    }
}
