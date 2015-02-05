/*
  @author       Obed N Munoz
  @school       ITESM - Master in Computer Sciences
  @class        Distributed Systems 
  @profressor   Marcos de Alba 
  @practice     Homework2 - Practice 3
  @description  Peer-Server-Peer communication with TCP Sockets
*/

import java.net.*;
import java.io.*;
import java.util.logging.Logger;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Scanner;
    
public class TCPPeerClient {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
   
    public static void main(String args[]){
	String peerName = "";
	String peerAddress = "localhost";
	int peerPort = 0;
	String serverAddress = "";
	int serverPort = 0;
	int startFlag = 1;
	Boolean started = false;

	if(args.length != 4){
	    LOGGER.severe("Invalid Number of parameters");
	    System.out.println("\n Example: \n > java TCPPeerClient <name> <port> <server_host> <server_port>");
	} else {
	    peerName = args[0];
	    peerPort = Integer.parseInt(args[1]);
	    serverAddress = args[2];
	    serverPort = Integer.parseInt(args[3]);
	}
	
	try{
	    ServerSocket listenSocket = new ServerSocket(peerPort);
	    while(true) {
		if (startFlag == 1){
		    Socket s = null;
		    try{
                        s = new Socket(serverAddress, serverPort);
                        DataOutputStream out =new DataOutputStream( s.getOutputStream());
			String outMsg = peerAddress+":"+peerPort+":"+peerName+":join";
                        out.writeUTF(outMsg);          
		    }catch (UnknownHostException e){
			System.out.println("Socket:"+e.getMessage());
		    }catch (EOFException e){
			System.out.println("EOF:"+e.getMessage());
		    }catch (IOException e){
			System.out.println("readline:"+e.getMessage());
		    }finally {if(s!=null) 
			    try {
				s.close();
			    }catch (IOException e){
				System.out.println("close:"+e.getMessage());
			    }
		     startFlag = 0;
		    }
		}
		if (!started)
		    LOGGER.info("Wating for  peer");

		Socket clientSocket = listenSocket.accept();
		Connection c = new Connection(clientSocket, peerName, peerPort, 
					      serverAddress, serverPort);
		started = true;
	    }
	} catch(IOException e) {
	    System.out.println("Listen socket:"+e.getMessage());
	}
    }
}

class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    private String peerName;
    private String peerAddress;
    private  int peerPort;
    private String serverName;
    private String serverAddress;
    private int serverPort;
    private int count = 0;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Connection (Socket aClientSocket, String peerName, int peerPort, 
		       String serverHost, int serverPort) {

	this.peerName = peerName;
	this.peerPort = peerPort;
	this.serverName = serverName;
	this.serverPort = serverPort;
	peerAddress = "localhost";
	LOGGER.info("Inside");
	try {
	    clientSocket = aClientSocket;
	    in = new DataInputStream( clientSocket.getInputStream());
	    out =new DataOutputStream( clientSocket.getOutputStream());
	    this.start();
	} catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
    }
    
    public void run(){
	
	try {                    
	    String buffer = in.readUTF();
	    String[] data = buffer.split(":"); 
	    serverAddress = data[0];
	    serverPort = Integer.parseInt(data[1]);
	    System.out.println("["+data[2]+"] > "+data[3]);
	    
	    /*
	      // WIP for being able to write 2 or more messages in a row
	      // instead of waiting for peer message
	      if (!new String("RECIBIDO").equals(data[3])) {
	      System.out.println("["+data[2]+"] > "+data[3]);
	      Socket s2 = new Socket(serverAddress, serverPort);
	      DataInputStream in2 = new DataInputStream( s2.getInputStream());
	      DataOutputStream out2 =new DataOutputStream( s2.getOutputStream());
	      String outMsg2 = peerAddress+":"+peerPort+":"+peerName+":"+"RECIBIDO";
	      out2.writeUTF(outMsg2);
	      s2.close();
	      }
	    */

	    String answer = "";
	    
	    Scanner scanner = new Scanner(System.in);
	    while ("".equals(answer) || " ".equals(answer) || answer == null){
		System.out.print("["+peerName+"] > ");
		answer = scanner.nextLine();
	    }
	    if (answer != "" && answer != " "){
		Socket s = null;
		try{
		    s = new Socket(serverAddress, serverPort);
		    DataInputStream in = new DataInputStream( s.getInputStream());
		    DataOutputStream out =new DataOutputStream( s.getOutputStream());
		    String outMsg = peerAddress+":"+peerPort+":"+peerName+":"+answer;
		    out.writeUTF(outMsg);
                }catch (UnknownHostException e){
		    System.out.println("Socket:"+e.getMessage());
                }catch (EOFException e){
		    System.out.println("EOF:"+e.getMessage());
                }catch (IOException e){
		    System.out.println("readline:"+e.getMessage());
                }finally {
		    if(s!=null) 
			try {
			      s.close();
			}catch (IOException e){
			    System.out.println("close:"+e.getMessage());
			}
		}
	    }

	}catch (EOFException e){
	    System.out.println("EOF:"+e.getMessage());
	} catch(IOException e) {
	    System.out.println("readline:"+e.getMessage());
	} finally{ 
	    try {
		clientSocket.close();
	    } catch (IOException e){
		/*close failed*/
	    }
	}


    }
}
