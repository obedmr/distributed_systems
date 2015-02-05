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

public class TCPServer {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static void main(String[] args) {
	int serverPort;
	DataInputStream in;
	DataOutputStream out;
	try{
	    serverPort = Integer.parseInt(args[0]);
	    ServerSocket listenSocket = new ServerSocket(serverPort);
	    
	    while(true) {
		try{
		    LOGGER.info("Wating Messages");
		    Socket clientSocket = listenSocket.accept();
		    ServerConnection con = new ServerConnection(clientSocket, serverPort);
		    
	        } catch(IOException e) {
		    System.out.println("readline:"+e.getMessage());
		}
	    }
	} catch(IOException e) {
	    System.out.println("Listen socket:"+e.getMessage());
	}
    }

}

class ServerConnection extends Thread {

    private Socket clientSocket;
    private DataInputStream input;
    private String peer1Address = "";
    private int peer1Port = 0;
    private String peer1Name = "";
    private String peer2Address = "";
    private int peer2Port = 0;
    private String peer2Name = "";
    private int serverPort = 0;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public ServerConnection(Socket clientSocket, int serverPort){
	try{
	    this.clientSocket = clientSocket;
	    this.serverPort = serverPort;
	    input = new DataInputStream(clientSocket.getInputStream());
	    this.start();
	} catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
    }
	
    public void run(){
	try{
	    String msg = "";  
	    String buffer = input.readUTF();
	    String[] data = buffer.split(":");

	    msg = data[3];
	    LOGGER.info("Peer1: "+peer1Address);
	    if (peer1Address == ""  && msg.equals("join")){
		peer1Address = data[0];
		peer1Port = Integer.parseInt(data[1]);
		peer1Name = data[2];
		Socket welcomeSocket = null;
		try{
		    welcomeSocket = new Socket(peer1Address, peer1Port);
		    DataOutputStream out =new DataOutputStream( welcomeSocket.getOutputStream());
		    String outMsg = "";
		    outMsg = "localhost:"+serverPort+":Server:"+msg;

		    out.writeUTF(outMsg);

		}catch (UnknownHostException e){
		    System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){
		    System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){
		    System.out.println("readline:"+e.getMessage());
		}finally {
		    if(welcomeSocket!=null)
			try {
			    welcomeSocket.close();
			}catch (IOException e){
			    System.out.println("close:"+e.getMessage());
			}
		}
		LOGGER.info("Peer1 ("+peer1Name+") has joined ...");
	    } else if (peer2Address == "" && msg.equals("join")){
		peer2Address = data[0];
		peer2Port = Integer.parseInt(data[1]);
		peer2Name = data[2];
		LOGGER.info("Peer2 ("+peer2Name+") has joined ...");
	    } else if (peer1Address != "" && peer2Address != ""){
		Socket peer1Socket = null;
		Socket peer2Socket = null;
		try{
		    peer1Socket = new Socket(peer1Address, peer1Port);
		    peer2Socket = new Socket(peer2Address, peer2Port);
		    DataOutputStream out1 =new DataOutputStream( peer1Socket.getOutputStream());
		    DataOutputStream out2 =new DataOutputStream( peer2Socket.getOutputStream());
		    String outMsg = "";
		    if (peer1Name.equals(data[2]))
			outMsg = peer1Address+":"+peer1Port+":"+peer1Name+":"+msg;
		    else
			outMsg = peer2Address+":"+peer2Port+":"+peer2Name+":"+msg;

		    out1.writeUTF(outMsg);
		    out2.writeUTF(outMsg);

		}catch (UnknownHostException e){
		    System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){
		    System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){
		    System.out.println("readline:"+e.getMessage());
		}finally {
		    if(peer1Socket!=null)
			try {
			    peer1Socket.close();
			    peer2Socket.close();
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
	    }catch (IOException e){
		/*close failed*/
	    }
	}
    }
}

