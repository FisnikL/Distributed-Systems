package January_2017;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Tester {

    private static ServerSocket listeningSocket;

    public static void main(String[] args) throws IOException {
        listeningSocket = new ServerSocket(0);

        loginRequest();

        while(true){
            Socket socket = listeningSocket.accept();

            Scanner scanner = new Scanner(socket.getInputStream());

            if(scanner.hasNextLine()){
                String request = scanner.nextLine();
                if(request.startsWith(Protocol.testSoftwareRequestFromOrganizerToTester)){
                    // if()
                }
            }
        }
    }

    private static void loginRequest() throws IOException {
        Socket socket = new Socket(Protocol.serverAddress, Protocol.serverPort);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

        String address = InetAddress.getLocalHost().getHostAddress();
        int port = listeningSocket.getLocalPort();

        printWriter.println(Protocol.testerLogin + address + " " + port);
        printWriter.flush();
        System.out.println("tester login request sent");
    }
}

class TesterWorker extends Thread{
    public boolean isTesterWorkerFree = true;

    @Override
    public void run() {
        isTesterWorkerFree = false;



        isTesterWorkerFree = true;
    }
}
