package september2015me;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerWorker extends Thread {

    private DatagramPacket dp;
    private Movie movie;
    private DatagramSocket ds;
    private long position;

    public ServerWorker(DatagramPacket dp, Movie movie) throws SocketException {
        this.dp = dp;
        this.movie = movie;
        this.position = 0;
        this.ds = new DatagramSocket();
    }

    @Override
    public void run() {
        System.out.println("Server Worker run method");
        InetAddress address = dp.getAddress();
        int port = dp.getPort();
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        packet.setAddress(address);
        packet.setPort(port);
        packet.setData(Protocol.startingMovie.getBytes());
        try {
            ds.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(this.position <= movie.getFrames()){
            buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            packet.setAddress(address);
            packet.setPort(port);
            packet.setData(Long.toString(position).getBytes());
            try {
                ds.send(packet);
                this.position++;
                sleep(1000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if(this.position == movie.getFrames() + 1){
            buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            packet.setAddress(address);
            packet.setPort(port);
            packet.setData(Protocol.endingMovie.getBytes());
            try {
                ds.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            packet.setAddress(address);
            packet.setPort(port);
            packet.setData(Protocol.stoppedMovie.getBytes());
            try {
                ds.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void setPosition(long position) {
//        synchronized(this.position){
//            this.position = position;
//        }
        this.position = position;
    }

    public void stopThread(){
        this.position = movie.getFrames() + 2;
    }
}
