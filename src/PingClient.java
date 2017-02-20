import java.io.*;
import java.net.*;
import java.util.*;

//HUSK socket.setToTimeOut
//Server o process ping requests over UDP
public class PingClient {
    private static final double LOSS_RATE = 0.3;
    private static final int AVERAGE_DELAY = 100; //milliseconds

    public static void main(String[] args) throws IOException {
        //get command line argument
        if (args.length != 1) {
            System.out.println("Required arguments: port");
            return;

        }
        int port = Integer.parseInt(args[0]); // Or int port= 555;

        //Create random number generator for use in simulating
        //packet loss and network delay

        Random ran = new Random();

        //Create a datagram socket for receiving and sending UDP packets
        //through the port specified on the command line

        DatagramSocket socket = new DatagramSocket();

        //Processing loop
        //Create a datagram packet to hold incoming UDP packet
        DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
        InetAddress clientHost = request.getAddress();
        int clientPort = request.getPort();
        try {

            while (true) {

                //Block until the host receives a UDP packet --- Edit to mailPack
                //socket.receive(request);
                //Print the recieve data --- Edit to mailPack
                //printData(request);
                //Decide weather to reply, or simulate packet loss
                //Simulate network delay.
                Thread.sleep((int) (ran.nextDouble() * 2 * AVERAGE_DELAY));
                //Send replay

              /*  byte[] buf = request.getData();
                DatagramPacket reply = new DatagramPacket(buf, buf.length, clientHost, clientPort);
                socket.send(reply);
                */


                String response = new String();
                response = "SOMETHING";

                try {
                    DatagramPacket sendPacket = new DatagramPacket(response.getBytes(),response.length(),InetAddress.getLocalHost(),1234);
                    socket.send(sendPacket);
                    System.out.println(" Reply sent.");
                }catch (Exception e) {
                    System.err.println(e);
                }
            }// END While

        } catch (Exception e) {
            System.err.println(e);
        }
        // outgoing udp?


    }//END main

    //Print ping data to the standard output stream of bytes.
    //Obtain references to the packet's array
  /*  private static void printData(DatagramPacket send) throws Exception {
        byte[] buf = send.getData();
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        InputStreamReader iSR = new InputStreamReader(bais);
        BufferedReader bR = new BufferedReader(iSR);
        String line = bR.readLine();
        System.out.println(
                "Received from " +
                        send.getAddress().getHostAddress() +
                        ": " +
                        new String(line)); //END sout

    }//END printData*/
}
