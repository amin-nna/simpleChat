import java.io.IOException;
import java.util.Scanner;

import common.ChatIF;
import ocsf.client.AbstractClient;

import client.ChatClient;

/**
 * @author amina_anna
 *
 */
public class ServerConsole implements ChatIF{
	  //Class variables *************************************************
	  
	  /**
	   * The default port to connect on.
	   */
	  final public static int DEFAULT_PORT = 80;
	  
	  //Instance variables **********************************************
	  
	  /**
	   * The instance of the client that created this ConsoleChat.
	   */

	  EchoServer sv;
	  ChatIF clientUI;
	  String login_id = "Server";
	  String host = "localhost";
	  int port = DEFAULT_PORT;

	  
	  
	  
	  /**
	   * Scanner to read from the console
	   */
	  Scanner fromConsole = new Scanner(System.in);; 
	  
	  public ServerConsole (int port) {
		  sv = new EchoServer(port);
		  // Create scanner object to read from console
		  fromConsole = new Scanner(System.in); 
		  //System.out.println("Prepare to listen");
		  try {
			sv.listen();
			//System.out.println("Server listening");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	 public ServerConsole() {
		// TODO Auto-generated constructor stub
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
	        
	        //System.out.println("On va handle "+ message);
	        sv.handleMessageFromServer(message);
	      }
	    } 
	    catch (Exception ex)
	    {
	    	 ex.printStackTrace();
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
	public static void main(String[] args) 
	  {
	   int port = DEFAULT_PORT; //Port to listen on

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555 (80)
	    }
		
	   
	    ServerConsole chatServer= new ServerConsole(port);
	    //System.out.println("Created the server");
	    
		try {
			chatServer.accept();
			
	      }
	      catch(ArrayIndexOutOfBoundsException e)
	      {
	    	  System.out.println("Nothing have been entered");
	      }
		
	    
	  }

}
