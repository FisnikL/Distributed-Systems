package January_2017;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Organizer {
    public static List<TesterC> testers = new ArrayList<>();
    public static int testerNumber = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Protocol.serverPort);
        System.out.println("Server is listening on port " + serverSocket.getLocalPort());
        while(true){
            Socket conn = serverSocket.accept();

            Scanner scanner = new Scanner(conn.getInputStream());

            if(scanner.hasNextLine()){
                String request = scanner.nextLine();

                if(request.startsWith(Protocol.testerLogin)){
                    handleTesterLogin(request);
                }
                else if(request.startsWith(Protocol.testSoftwareRequest)){
                    handleTestSoftwareRequest(conn, request);
                }
            }
        }
    }

    private static void handleTesterLogin(String request){
        String tester = request.replace(Protocol.testerLogin, "");
        String[] parts = tester.split(" ");

        String address = parts[0];
        int port = Integer.parseInt(parts[1]);

        TesterC newTester = new TesterC(address, port);
        testers.add(newTester);
        System.out.println(newTester + " tester added");
    }

    private static void handleTestSoftwareRequest(Socket conn, String request) throws IOException {
        TestSoftware ts = new TestSoftware(conn, request);
        ts.start();
    }
}

class TestSoftware extends Thread{
    private Socket socket;
    private PrintWriter printWriter;
    private Scanner scanner;
    private String request;

    public TestSoftware(Socket socket, String request) throws IOException {
        this.socket = socket;
        this.printWriter = new PrintWriter(socket.getOutputStream());
        this.scanner = new Scanner(socket.getInputStream());
        this.request = request;
    }

    @Override
    public void run() {
        TesterC testerC = Organizer.testers.get(Organizer.testerNumber);
        Organizer.testerNumber = (Organizer.testerNumber + 1) % Organizer.testers.size();

        try {
            InetAddress address = InetAddress.getLocalHost();
            int port = testerC.getListeningPort();

            Socket socket = new Socket(address, port);


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
