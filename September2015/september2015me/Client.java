package september2015me;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client {
    public static List<String> movies = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        byte[] buf = Protocol.messageForMoviesList.getBytes();
        DatagramPacket dp = new DatagramPacket(buf, buf.length);
        dp.setAddress(InetAddress.getByName("localhost"));
        dp.setPort(Protocol.serverPort);
        ds.send(dp);

        buf = new byte[256];
        dp = new DatagramPacket(buf, buf.length);
        ds.receive(dp);
        if(new String(dp.getData()).trim().equals(Protocol.startMoviesList)){
            while(true){
                buf = new byte[256];
                dp = new DatagramPacket(buf, buf.length);
                ds.receive(dp);
                String m = new String(dp.getData()).trim();
                if(m.equals(Protocol.endMoviesList)){
                    break;
                }
                System.out.println(m);
                movies.add(m);
            }
        }else{
            // nekoja poraka za greshka
        }
        movies.add("NonExistentMovie");
        // System.out.println("out");
        buf = new byte[256];
        Random random = new Random();
        String mov = movies.get(random.nextInt(movies.size()));
        System.out.println(mov);
        buf = (Protocol.startMovie + mov).getBytes();
        // ds = new DatagramSocket();
        dp = new DatagramPacket(buf, buf.length);
        dp.setAddress(InetAddress.getByName("localhost"));
        dp.setPort(Protocol.serverPort);
        ds.send(dp);

        buf = new byte[256];
        dp = new DatagramPacket(buf, buf.length);
        ds.receive(dp);
        if(new String(dp.getData()).trim().equals(Protocol.startingMovie)) {
            while(true){
                buf = new byte[256];
                dp = new DatagramPacket(buf, buf.length);
                ds.receive(dp);
                String m = new String(dp.getData()).trim();
                if(m.equals(Protocol.endingMovie)){
                    System.out.println("Kraj");
                    break;
                }
                if(m.equals(Protocol.stoppedMovie)){
                    System.out.println("Stopped");
                    break;
                }
                System.out.println(new String(dp.getData()).trim());
                if(Integer.parseInt(m) == 10){
                    buf = new byte[256];
                    dp = new DatagramPacket(buf, buf.length);
                    dp.setAddress(InetAddress.getByName("localhost"));
                    dp.setPort(Protocol.serverPort);
                    dp.setData((Protocol.moviePosition + Integer.toString(random.nextInt(40))).getBytes());
                    ds.send(dp);
                }
                if(Integer.parseInt(m) == 20 || Integer.parseInt(m) == 40){
                    buf = new byte[256];
                    dp = new DatagramPacket(buf, buf.length);
                    dp.setAddress(InetAddress.getByName("localhost"));
                    dp.setPort(Protocol.serverPort);
                    dp.setData(Protocol.stopMovie.getBytes());
                    ds.send(dp);
                }
            }
        }
        else{
            // nekoja poraka za greshka
        }


    }
}
