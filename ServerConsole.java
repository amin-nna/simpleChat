import java.util.Scanner;

import client.ChatClient;
import common.ChatIF;


/**
 * @author amina_anna
 *
 */
public class ServerConsole implements ChatIF {
	  //Class variables *************************************************
	  
	  /**
	   * The default port to connect on.
	   */
	  final public static int DEFAULT_PORT = 80;
	  
	  //Instance variables **********************************************
	  
	  /**
	   * The instance of the client that created this ConsoleChat.
	   */

	  ChatClient clientServer;
	  ChatIF clientUI;
	  
	  
	  
	  /**
	   * Scanner to read from the console
	   */
	  Scanner fromConsole; 
	  
	  public ServerConsole () {
		  
	  }
	  
	 /**
	   * This method waits for input from the console.  Once it is 
	   * received, it sends it to the server's message handler.
	   */
	  public void accept() 
	  {
	    try
	    {

	      String message;

	      while (true) 
	      {
	        message = fromConsole.nextLine();
	        clientServer.handleMessageFromServer(message);
	      }
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println
	        ("Unexpected error while reading from console!");
	    }
	  }

	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		System.out.println("SERVER MSG> " + message);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerConsole chatServer= new ServerConsole();
		
		try {
			chatServer.accept();
			
	      }
	      catch(ArrayIndexOutOfBoundsException e)
	      {
	    	  System.out.println("Nothing have been entered");
	      }
		
	}

}
