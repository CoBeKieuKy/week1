package com.hust.soict.thanh_hc.client_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import com.hust.soict.thanh_hc.helper.*;
import java.util.Arrays;

public class Server {
	public static void main(String[] args) {
		System.out.println("The Sorter Server is running!");
		int clientNumber = 0;
		try (ServerSocket listener = new ServerSocket(9898)) {
			while (true) {
				new Sorter(listener.accept(), clientNumber++).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Sorter extends Thread {
	private Socket socket;
	private int clientNumber;

	public Sorter(Socket socket, int clientNumber) {
		this.socket = socket;
		this.clientNumber = clientNumber;
		System.out.println("New client #" + clientNumber + " connected at " + socket);
	}

	public void run() {
		 try{
			 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			 out.println("Hello, you are client # " + clientNumber+ "! Please input your array that you want to get sorted:");
			 while (true){
				 String input = in.readLine();
				 if (input == null || input.isEmpty()) {
					 break;
				 }
				 
				String[] nums = input.split(" ");
				int[] intarr = new int[ nums.length];
				int i = 0;
			
				for ( String textValue : nums ) {
					if(!textValue.isEmpty()) {
					intarr[i] = Integer.parseInt( textValue );
					i++;
					}
				}
				//Sort the numbers in this int array
				out.println("input your choice of algorithms: (1: Selection Sort, 2: Shell Sort, 3: Bubble Sort, 4: Insertion Sort");
				String choice = in.readLine();
				switch(Integer.parseInt(choice)) {
				case 1:
					new SelectionSort().sort(intarr);break;
				case 2: 
					new ShellSort().sort(intarr);break;
				case 3: 
					new BubbleSort().sort(intarr);break;
				case 4:
					new InsertionSort().sort(intarr);break;
				};
				 //Convert the int array to String
				String strArray[] = Arrays.stream(intarr).mapToObj(String::valueOf).toArray(String[]::new);
				 //Send the result to Client
				out.println(Arrays.toString(strArray));
			 }
		}catch (IOException e){
			System.out.println("Error handling client #" + clientNumber);
		}finally{
			 try { socket.close();
			 } catch (IOException e){}
			 System.out.println("Connection with client # " + clientNumber + " closed");
		}
	}
}

