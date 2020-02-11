package september2015me;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    public static Map<Klient, ServerWorker> clients = new HashMap<>();
    public static List<Movie> movies = new ArrayList<>();

    static{
        movies.add(new Movie("Movie1", 50));
        movies.add(new Movie("Movie2", 55));
        movies.add(new Movie("Movie3", 45));
        movies.add(new Movie("Movie4", 52));
        movies.add(new Movie("Movie5", 43));
    }

    public static void main(String[] args) throws Exception {
        DatagramSocket ds = new DatagramSocket(Protocol.serverPort);
        System.out.println("Server's listening...");
        while(true){
            System.out.println("Num of clients:" + clients.size());
            byte[] buf = new byte[256];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            System.out.println("Receiving...");
            ds.receive(dp);
            System.out.println("New Request");
            String message = new String(dp.getData()).trim();
            System.out.println(message);
            if(message.equals(Protocol.messageForMoviesList)){
                System.out.println("New request for movie list");
                InetAddress address = dp.getAddress();
                int port = dp.getPort();
                buf = new byte[256];
                dp = new DatagramPacket(buf, buf.length);
                dp.setAddress(address);
                dp.setPort(port);
                dp.setData(Protocol.startMoviesList.getBytes());
                ds.send(dp);
                for(Movie movie: movies){
                    buf = new byte[256];
                    dp = new DatagramPacket(buf, buf.length);
                    dp.setAddress(address);
                    dp.setPort(port);
                    dp.setData(movie.getName().getBytes());
                    ds.send(dp);
                }
                buf = new byte[256];
                dp = new DatagramPacket(buf, buf.length);
                dp.setAddress(address);
                dp.setPort(port);
                dp.setData(Protocol.endMoviesList.getBytes());
                ds.send(dp);
            }
            else if(message.startsWith(Protocol.startMovie)){
                String movieName = message.replace(Protocol.startMovie, "");
                Movie temp = new Movie(movieName);
                int indexOfMovie = movies.indexOf(temp);
                if(indexOfMovie == -1){
                    // vratime greshka
                }else{
                    Movie movie = movies.get(indexOfMovie);
                    // System.out.println(movie);
                    ServerWorker sw = new ServerWorker(dp, movie);
                    Klient klient = new Klient(dp.getAddress().getHostAddress(), dp.getPort());
                    clients.put(klient, sw);
                    sw.start();
                }
            }
            else if(message.startsWith(Protocol.moviePosition)){ // Change movie position
                String address = dp.getAddress().getHostAddress();
                int port = dp.getPort();
                Klient temp = new Klient(address, port);
                ServerWorker serverWorker = clients.get(temp);
                if(serverWorker != null){
                    long position = Long.parseLong(message.replace(Protocol.moviePosition, ""));
                    serverWorker.setPosition(position);
                }else{
                    // Poraka za greshka
                }
            }else if(message.equals(Protocol.stopMovie)){
                System.out.println("Request for stopping movie");
                String address = dp.getAddress().getHostAddress();
                int port = dp.getPort();
                Klient temp = new Klient(address, port);
                ServerWorker serverWorker = clients.get(temp);
                if(serverWorker != null){
                    // String movie = message.replace(Protocol.stopMovie, "");
                    // serverWorker.interrupt();
                    serverWorker.stopThread();
                    clients.remove(temp);
                    System.out.println("Movie stopped");
                }else{
                    // Poraka za greshka
                }


            }
        }
    }

//    private static Klient checkTheNewKlient(DatagramPacket dp){
//
//
//        Klient newKlient = new Klient(address, port);
//        int result = clients.indexOf(newKlient);
//
//        Klient klient;
//        if(result == -1){
//            clients.add(newKlient);
//            klient = clients.get(clients.size() - 1);
//        }else{
//            klient = clients.get(result);
//        }
//    }
}
