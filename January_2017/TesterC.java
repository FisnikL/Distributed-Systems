package January_2017;

public class TesterC {
    private String address;
    private int listeningPort;

    public TesterC(String address, int listeningPort) {
        this.address = address;
        this.listeningPort = listeningPort;
    }

    @Override
    public String toString() {
        return address + " " + listeningPort;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getListeningPort() {
        return listeningPort;
    }

    public void setListeningPort(int listeningPort) {
        this.listeningPort = listeningPort;
    }
}
