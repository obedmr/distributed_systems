/*
  @author       Obed N Munoz
  @school       ITESM - Master in Computer Sciences
  @class        Distributed Systems 
  @profressor   Marcos de Alba 
  @practice     Homework2 - Practice 1
  @description  Peer-to-Peer communication with TCP Sockets
*/

package pract1;

import java.net.*;
import java.io.*;
import java.util.logging.Logger;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Scanner;
    
public class TCPPeer {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
   
    public static void main(String args[]){
	String peerName = "";
	String peerAddress = "localhost";
	int peerPort = 0;
	String colleagueAddress = "";
	int colleaguePort = 0;
	int startFlag = 0;
	Boolean started = false;

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
	
	try{
	    ServerSocket listenSocket = new ServerSocket(peerPort);
	    while(true) {
		if (startFlag == 1){
		    Socket s = null;
		    try{
                        s = new Socket(colleagueAddress, colleaguePort);
                        DataOutputStream out =new DataOutputStream( s.getOutputStream());
			String outMsg = peerAddress+":"+peerPort+":"+peerName+":Hi";
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
					      colleagueAddress, colleaguePort);
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
    private String colleagueName;
    private String colleagueAddress;
    private int colleaguePort;
    private int count = 0;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Connection (Socket aClientSocket, String peerName, int peerPort, 
		       String colleagueHost, int colleaguePort) {

	this.peerName = peerName;
	this.peerPort = peerPort;
	this.colleagueName = colleagueName;
	this.colleaguePort = colleaguePort;
	peerAddress = "localhost";

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
	    colleagueAddress = data[0];
	    colleaguePort = Integer.parseInt(data[1]);
	    System.out.println("["+data[2]+"] > "+data[3]);
	    
	    /*
	      // WIP for being able to write 2 or more messages in a row
	      // instead of waiting for peer message
	      if (!new String("RECIBIDO").equals(data[3])) {
	      System.out.println("["+data[2]+"] > "+data[3]);
	      Socket s2 = new Socket(colleagueAddress, colleaguePort);
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
		    s = new Socket(colleagueAddress, colleaguePort);
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
