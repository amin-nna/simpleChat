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
  
  ChatIF clientUI = new ServerConsole();
  
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
			
			clientUI.display("SERVER MSG> " +msgString);
			sendToAllClients("SERVER MSG> " +msg);
			
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
			  if ( this.isListening()) {
				 
				  clientUI.display("Error, the serveur is already running");
			  }
			  else{
					try {
						this.listen();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			      }
			  }
		  else if ( cmd.startsWith("#setport")) {
			  if ( this.isListening()) {
				  
				  clientUI.display("Error, the serveur already is running");
			  }
			  else {
				  int cmdPort = Integer.parseInt(cmd.substring(cmd.indexOf('<') + 1, cmd.indexOf('>')));
				  this.setPort(cmdPort);
				  clientUI.display("Server port has been set to " + cmdPort);
			  }
		  }
		  else if ( cmd.equals("#getport")) {
			  clientUI.display("Le numéro de port actuel est " + this.getPort());
		  }
		  else {
			  clientUI.display("Commande entrée non reconue, veuillez réessayer.");
		  }
		  
		}
	  
	  
		
	  
	  
  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   * @throws IOException 
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {
    ServerConsole displayUI = new ServerConsole();
	//displayUI.display("Message received: " + msg + " from " + client.getInfo(loginKey));
    
    String msgString = (String)msg;
    displayUI.display("Message received: " + msg + " from " + client.getInfo(loginKey));
    
    if ( msgString.startsWith("#login")) {
    	if ( client.getInfo(loginKey) != null) {
			try {
				//Le server ferme la connexion avec le client.
				//displayUI.display(client.getInfo(loginKey) + " is trying to login while logged in.\n The sever closes the connexion with this client.");
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
    	}
    	else {
	    	String loginID = msgString.substring(msgString.indexOf('<') + 1, msgString.indexOf('>'));
	    	//displayUI.display("<" + loginID + ">" + "has logged on.");
	    	displayUI.display("<" + loginID + ">" + " has logged on.");
	    	client.setInfo(loginKey, loginID);
    	}
    }
    else {
    	//displayUI .display(msgString);
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
  @Override
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections on port "+getPort());
  }
  
  @Override
  protected void clientConnected(ConnectionToClient client) {
		System.out.println("A new client has connected to the server.");
	}
  
  @Override
  protected void clientDisconnected(ConnectionToClient client) {
		System.out.println(client.getInfo(loginKey)+" has disconnected.");
	}

  
}
//End of EchoServer class
