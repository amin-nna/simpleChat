// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;
import java.io.IOException;
import ocsf.server.AbstractServer;
import common.ChatIF;
import ocsf.client.AbstractClient;



/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI;
  String login_id;
  

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI, String login_id) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.login_id = login_id;
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) {
	String msgString = (String) msg;
	if ( msgString .startsWith("#")) {
		
	}
	else {
		clientUI.display(msgString);
	}
		
	   	  
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  
  {
    try
    {
    	if ( message.startsWith("#")) {
    		handleCommandClient(message);
    	}
    	else {
    		//clientUI.display(message);
    		sendToServer(message);
    	}
     
    }
    catch(IOException e)
    {
      clientUI.display("Could not send message to server.  Terminating client.");
      quit();
    }
    
  }
 
  
  private void handleCommandClient (String cmd) {
	  
	if ( cmd.equals("#quit")) {
		  //On a déjà une méthode quit qui va fermer une connexion et sortir
		  clientUI.display("The client quit.");
		  quit();
	  }
	  else if ( cmd.equals("#logoff")) {
		  if (this.isConnected()) {
			  //String messageToServer = "#logoff <"+this.login_id+">";
		  		try {
					//sendToServer(messageToServer);
					this.closeConnection();
				} catch (IOException e) {
					// Try catch block
					e.printStackTrace();
				}
			  
			  
		  }
		  else {
			  clientUI.display("The client is already disconnected.");
		  }
		  
	  }
	  else if ( cmd.startsWith("#sethost")) {
		  //Si le client est déconnecté
		  if ( this.isConnected()) {
			  clientUI.display("Error, the client is connected");
		  }
		  else {
			  String cmdHost = cmd.substring(cmd.indexOf('<') + 1, cmd.indexOf('>'));
			  this.setHost(cmdHost);
		  }
		  
		  
	  }
	  else if ( cmd.startsWith("#setport")) {
		  if ( this.isConnected()) {
			  clientUI.display("Error, the client is connected");
		  }
		  else {
			  int cmdPort = Integer.parseInt(cmd.substring(cmd.indexOf('<') + 1, cmd.indexOf('>')));
			  this.setPort(cmdPort);
		  }
		  
		  
	  }
	  else if ( cmd.equals("#login")) {
		  //Si le client n'est pas déjà connecté
		  if ( this.isConnected()) {
			  clientUI.display("Error, the client is already connected");
			  try {
				sendToServer(cmd);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  else {
			    try {
					openConnection();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  }
		  
		  
	  }
	  else if ( cmd.equals("#gethost")) {
		  clientUI.display("Le nom d'hote actuel est " + this.getHost());
	  }
	  else if ( cmd.equals("#getport")) {
		  clientUI.display("Le numéro de port actuel est " + this.getPort());
	  }
	  else {
		  clientUI.display("Commande entrée non reconue, veuillez réessayer.");
	  }
	}
  
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  

	/**
	 * Hook method called after the connection has been closed. The default
	 * implementation does nothing. The method may be overriden by subclasses to
	 * perform special processing such as cleaning up and terminating, or
	 * attempting to reconnect.
	 */
  	@Override
	protected void connectionClosed() {
		clientUI.display("The connexion has been closed");
	}

	/**
	 * Hook method called each time an exception is thrown by the client's
	 * thread that is waiting for messages from the server. The method may be
	 * overridden by subclasses.
	 * 
	 * @param exception
	 * the exception raised.
	 */
  	@Override
	protected void connectionException(Exception exception) {
  		clientUI.display("The server has shut down");
  		System.exit(0);
	}
  	
  	@Override
	protected void connectionEstablished() {
  		//Envoyer "#login <loginid>" au serveur
  		String messageToServer = "#login <"+this.login_id+">";
  		
  		try {
			sendToServer(messageToServer);
			clientUI.display(this.login_id+" has logged on");
		} catch (IOException e) {
			// Try catch block
			e.printStackTrace();
		}
	}
}
//End of ChatClient class
