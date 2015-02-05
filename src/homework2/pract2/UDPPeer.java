/*
  @author       Obed N Munoz
  @school       ITESM - Master in Computer Sciences
  @class        Distributed Systems 
  @profressor   Marcos de Alba 
  @practice     Homework2 - Practice 2
  @description  Peer-to-Peer communication with UDP Sockets
*/
package pract2;

import java.net.*;
import java.io.*;
import java.util.logging.Logger;
import java.util.Scanner;
    
public class UDPPeer {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
   
    public static void main(String args[]){
	String peerName = "";
	String peerAddress = "localhost";
	int peerPort = 0;
	String colleagueAddress = "";
	int colleaguePort = 0;
	int startFlag = 0;
	Boolean started = false;
	byte[] buffer = new byte[1000];
	
	if(args.length != 5){
	    LOGGER.severe("Invalid Number of parameters");
	    System.out.println("\n Example: \n > java TCPPeer <peer_name> <port_number> <colleagueHost> <colleague_port> <start_flag [1,0]>");
	} else {
	    peerName = args[0];
	    peerPort = Integer.parseInt(args[1]);
	    colleagueAddress = args[2];
	    colleaguePort = Integer.parseInt(args[3]);
	    startFlag = Integer.parseInt(args[4]);
	}
	DatagramSocket aSocket = null;
	try{
	    aSocket = new DatagramSocket(peerPort);
	    while(true) {
		if (startFlag == 1){
		    DatagramSocket firstSocket = null;
		    try{
                        firstSocket = new DatagramSocket();
			String outMsg = peerAddress+":"+peerPort+":"+peerName+":Hi";
			byte[] outBuffer = new byte[1000];
			outBuffer = outMsg.getBytes();
			LOGGER.info(new String(outBuffer));
			InetAddress aHost = InetAddress.getByName(colleagueAddress);
			DatagramPacket firstPack = new DatagramPacket(outBuffer, outBuffer.length,
								      aHost, colleaguePort);
                        firstSocket.send(firstPack);
		    }catch (SocketException e){
			System.out.println("Socket:"+e.getMessage());
		    }catch (IOException e){
			System.out.println("IO: "+e.getMessage());
		    }finally {
			if(firstSocket!=null) 
			    firstSocket.close();
		     startFlag = 0;
		    }
		}
		if (!started)
		    LOGGER.info("Wating for  peer");
		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		aSocket.receive(request);
		Connection c = new Connection(aSocket, request, peerName, peerPort, 
					      colleagueAddress, colleaguePort);
		started = true;
	    }
	} catch(IOException e) {
	    System.out.println("Listen socket:"+e.getMessage());
	}
    }
}

class Connection extends Thread {

    private DatagramSocket clientSocket;
    private DatagramPacket clientRequest;
    private String peerName;
    private String peerAddress;
    private  int peerPort;
    private String colleagueName;
    private String colleagueAddress;
    private int colleaguePort;
    private int count = 0;
    //byte[] buffer = new byte[100];

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Connection (DatagramSocket aSocket, DatagramPacket request, String peerName,
		       int peerPort, String colleagueHost, int colleaguePort) {

	this.peerName = peerName;
	this.peerPort = peerPort;
	this.colleagueName = colleagueName;
	this.colleaguePort = colleaguePort;
	peerAddress = "localhost";

        clientSocket = aSocket;
        clientRequest = request;
        this.start();
    }
    public void run(){
	
	try {                    
	    String buffer = new String(clientRequest.getData());
	    
	    String[] data = buffer.split(":"); 
	    colleagueAddress = data[0];
	    colleaguePort = Integer.parseInt(data[1]);
	    System.out.println("["+data[2]+"] > "+data[3]);
	    
	    String answer = "";
	    
	    Scanner scanner = new Scanner(System.in);
	    while ("".equals(answer) || " ".equals(answer) || answer == null){
		System.out.print("["+peerName+"] > ");
		answer = scanner.nextLine();
	    }
	    if (answer != "" && answer != " "){
		DatagramSocket replySocket = null;
		try{
		    replySocket = new DatagramSocket();
		    InetAddress aHost = InetAddress.getByName(colleagueAddress);
		    String outMsg = peerAddress+":"+peerPort+":"+peerName+":"+answer;
		    byte[] outBuffer = new byte[1000];
		    outBuffer = outMsg.getBytes();
		    DatagramPacket replyRequest =
			new DatagramPacket(outBuffer,  outBuffer.length,
					   aHost, colleaguePort);
		    replySocket.send(replyRequest);
                }catch (SocketException e){
		    System.out.println("Socket:"+e.getMessage());
                }
	    }

	} catch(IOException e) {
	    System.out.println("readline:"+e.getMessage());
	} 
    }
}
