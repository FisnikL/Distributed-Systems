package January_2017;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Protocol {
    // Organizer
    public static final int serverPort = 8080;
    public static InetAddress serverAddress;

    static {
        try {
            serverAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static final String testSoftwareRequestFromOrganizerToTester = "testSoftwareRequestFromOrganizerToTester ";
    // testSoftwareRequestFromOrganizerToTester {softwareName} {softwareTime}

    // TESTER
    public static final String testerLogin = "testerLogin "; // testerLogin {address} {listeningPort}


    // Client
    public static final String testSoftwareRequest = "testSoftwareRequest "; // testSoftwareRequest {softwareName} {timeToTest}
    public static final String tryAgainLaterResponse = "tryAgainLaterResponse";
}
