import java.io.*;
import java.net.*;
import java.util.*;

//Server o process ping requests over UDP
public class PingServer {
    private static final double LOSS_RATE = 0.3;
    private static final int AVERAGE_DELAY = 100; //milliseconds

    public static void main(String[] args) throws IOException {
        //get command line argument
        if (args.length != 1) {
            System.out.println("Requiredarguments: port");
            return;

        }
        int port = Integer.parseInt(args[0]); // Or int port= 555;

                //Create random number generator for use in simulating
                //packet loss and network delay

        Random ran = new Random();

                //Create a datagram socket for receiving and sending UDP packets
                //through the port specified on the command line

        DatagramSocket socket = new DatagramSocket(port);

                //Processing loop
                //Create a datagram packet to hold incoming UDP packet
       try {

        while (true) {

            DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                    //Block until the host receives a UDP packet
            socket.receive(request);
                    //Print the recieve data
            printData(request);
                    //Decide wether to reply, or simulate packet loss
            if (ran.nextDouble() < LOSS_RATE) {
                System.out.println(" Replay not sent.");
                continue;
            }
                    //Simulate network delay.
            Thread.sleep((int)(ran.nextDouble()*2*AVERAGE_DELAY));
                    //Send replay
            InetAddress clientHost = request.getAddress();
            int clientPort = request.getPort();
            byte[] buf = request.getData();
            DatagramPacket reply = new DatagramPacket(buf,buf.length,clientHost,clientPort);
            socket.send(reply);
            System.out.println(" Data sent.");
        }// END While

       }catch (Exception e){
           System.err.println(e);
       }

    }//END main

                //Print ping data to the standard output stream of bytes.
                //Obtain references to the packet's array
    private static void printData(DatagramPacket request) throws Exception {
        byte[] buf = request.getData();
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        InputStreamReader iSR = new InputStreamReader(bais);
        BufferedReader bR = new BufferedReader(iSR);
        String line = bR.readLine();

        System.out.println(
                "Received from "+
                        request.getAddress().getHostAddress()+
                        ": "+
                        new String (line)); //END sout
    }//END printData
}
