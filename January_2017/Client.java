package January_2017;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Client {



    public static void main(String[] args) throws IOException {


        for(int i = 0; i < 1; ++i){
            Socket socket = new Socket(Protocol.serverAddress, Protocol.serverPort);
            ClientWorker cw = new ClientWorker(socket);
            cw.start();
        }
    }
}

class ClientWorker extends Thread{

    private static List<String> softwares = new ArrayList<>();

    static{
        softwares.add("Software1 5");
        softwares.add("Software2 10");
        softwares.add("Software3 3");
        softwares.add("Software4 7");
        softwares.add("Software5 15");
    }

    private Socket socket;
    private PrintWriter printWriter;
    private Scanner scanner;

    public ClientWorker(Socket socket) throws IOException {
        this.socket = socket;
        this.printWriter = new PrintWriter(socket.getOutputStream());
        this.scanner = new Scanner(socket.getInputStream());
    }

    @Override
    public void run() {
        Random random = new Random();
        int rand = random.nextInt(softwares.size());
        String softwareToTest = softwares.get(rand);

        printWriter.println(Protocol.testSoftwareRequest + softwareToTest);
        printWriter.flush();

        while(true){
            if(scanner.hasNextLine()){
                String response = scanner.nextLine();
                if(response.equals(Protocol.tryAgainLaterResponse)){
                    System.out.println("Try again later!");
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println(response);
                }
            }
        }


    }
}
