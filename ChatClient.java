//Name:Soumya Tejaswi Vadlamani(FSU ID:SV22O)
package chatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {


public static void main(String[] args) {
	Socket clientSocket=null;
	PrintWriter output=null;
	BufferedReader userInput=null;
	
	
	try {//create a client,clientSocket
		clientSocket=new Socket("localhost",65140);//give whatever value of the port you want
		System.out.println("Connected to Server:" +clientSocket);
		
		//create input output streams
		BufferedReader input=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		output=new PrintWriter(clientSocket.getOutputStream(),true);
		
		//Create a separate thread to read messages from the server
		Thread readThread=new Thread(() ->{
			String message;
			try {
				while((message =input.readLine())!= null) {
					System.out.println("CustomerCare :" +message);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				try {
					input.close();
				}catch(IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		readThread.start();
		
		//start communication
		userInput=new BufferedReader(new InputStreamReader(System.in));
		String message;
		while((message=userInput.readLine())!=null) {
			output.println(message);
			}
				
	}catch(IOException e) {
		e.printStackTrace();
	}finally {
		output.close();
		try {
		userInput.close();
		clientSocket.close();
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
}

