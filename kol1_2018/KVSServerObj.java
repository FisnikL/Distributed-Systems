package kol1_2018_secondTry;

public class KVSServerObj {
    private String address;
    private int port;
    private int listeningPort;

    public KVSServerObj(String address, int port, int listeningPort) {
        this.address = address;
        this.port = port;
        this.listeningPort = listeningPort;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getListeningPort() {
        return listeningPort;
    }

    public void setListeningPort(int listeningPort) {
        this.listeningPort = listeningPort;
    }
}
