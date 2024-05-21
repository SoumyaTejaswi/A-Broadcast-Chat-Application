//Name:Soumya Tejaswi Vadlamani(FSU ID:SV22O)


package chatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	public static void main(String[] args) {
		ServerSocket serverSocket=null;
		Socket clientSocket=null;
		PrintWriter output = null;
		BufferedReader userInput=null;
		
		try {
		//Server Socket creation
			serverSocket= new ServerSocket(65140);//give whatever value of the port you want same as in chatclient
			System.out.println("Server started.Waiting for Clients...");
			
			//Accept Client Connections
			clientSocket=serverSocket.accept();//method that waits for the client to connect and return a socket object which represenst the connection. 
			System.out.println("Client Connected: "+clientSocket);
			
			//create input output string for the client
			BufferedReader input=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//reading data from the socket
			output=new PrintWriter(clientSocket.getOutputStream(),true);
			
			//Create a separate thread to read messages from the client
			Thread readThread=new Thread(()-> {
				String message;
				try {
					while((message=input.readLine())!=null) {
						System.out.println("User :"+message);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}finally {//closed buffered reader
					try {
						input.close();
					}catch(IOException e) {
					throw new RuntimeException(e);
				}
				}
			});
			readThread.start();
			
			userInput=new BufferedReader(new InputStreamReader(System.in));
			String message;
			while((message=userInput.readLine()) !=null) {
				output.println(message);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			//Close Connection
			output.close();
			try {
				userInput.close();
				clientSocket.close();
				serverSocket.close(); 
			}catch(IOException e) {
				throw new RuntimeException(e);
			}
			
			
		}
	}
}
 
 