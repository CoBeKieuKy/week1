package com.hust.soict.thanh_hc.client_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 9898);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		System.out.println(in.readLine());
		Scanner scanner = new Scanner(System.in);
		String message = scanner.nextLine();
		
		//Question 6: while loop
		while(!message.isEmpty()) {
			out.println(message);
			System.out.println(in.readLine());
			String choice = scanner.nextLine();
			out.println(choice);
			
			System.out.println("the array after sorting:" + in.readLine());
			System.out.println("input another array ? (or just press Enter to exit)");
			message = scanner.nextLine();
			if(message.isEmpty()) {
				System.out.println("Logged out!");
				break;
			}
		}

		socket.close();
		scanner.close();
	}
}
