// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import java.io.IOException;

import common.ChatIF;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
	final public String loginKey="loginkey";
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 80;
  
  ChatIF clientUI;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  public void handleMessageFromServer(Object msg) {
		String msgString = (String) msg;
		if ( msgString.startsWith("#")) {
			//System.out.println("On va appelle handle commandserver");
			handleCommandServer(msgString);
		}
		else {
			
			clientUI.display(msgString);
			
		}    	  
	  }

	  
	    
	  private void handleCommandServer (String cmd) {
		 
		  if ( cmd.equals("#quit")) {
			  //instructions pour quit()
			  System.exit(0);
			
		  }
		  else if ( cmd.equals("#stop")) {
			this.stopListening();
		  }
		  else if ( cmd.equals("#close")) {
			  try {
				this.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  
		  else if ( cmd.equals("#start")) {
				try {
					this.listen();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		  else if ( cmd.startsWith("#setport")) {
			  if ( this.isListening()) {
				  
				  clientUI.display("Error, the serveur is running");
			  }
			  else {
				  int cmdPort = Integer.parseInt(cmd.substring(cmd.indexOf('<') + 1, cmd.indexOf('>')));
				  this.setPort(cmdPort);
			  }
		  }
		  else if ( cmd.equals("#getport")) {
			  clientUI.display("Le numéro de port actuel est " + this.getPort());
		  }
		}
	  
	  
		
	  
	  
  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {
    ServerConsole displayUI = new ServerConsole();
	displayUI.display("Message received: " + msg + " from " + client);
    
    String msgString = (String)msg;
    
    if ( msgString.startsWith("#login")) {
    	String loginID = msgString.substring(msgString.indexOf('<') + 1, msgString.indexOf('>'));
    	client.setInfo(loginKey, loginID);
    }
    else {
    	this.sendToAllClients(client.getInfo(loginKey) + " : " + msgString);
    }
    
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
 
	protected void clientConnected() {
		System.out.println("A client is connected");
	}
  
  
	protected void clientDisconnected() {
		System.out.println("A client is disconnected");
	}

  
}
//End of EchoServer class
