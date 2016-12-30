package proba1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class KlientSerwer extends Thread {

	private Socket sock = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	public static int ktoryKlient;
	String name = null;

	public KlientSerwer(String host, int port) {

		try {
			sock = new Socket(host, port);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			name = in.readLine();
			System.out.println(name);
			start();
		} catch (UnknownHostException e) {
			System.err.println("Nieznany host: " + host + " dla " + name);
			System.exit(2);
		} catch (IOException e) {
			System.err.println("I/O error dla	" + name);
			System.exit(3);
		} catch (Exception exc) {
			exc.printStackTrace();
			System.exit(4);
		}
	}



	public void run() {
		new StronaGlowna(); 
	}



	private void makeRequest(String req) throws IOException {
		// System.out.println("Request: " + req);
		System.out.println(req);
		out.println(req);
		String resp = in.readLine();
		System.out.println(resp);
	}

	public static void main(String[] args) {
		String host = "127.0.0.1"; // nazwa hosta
		int port = 1500; // numer portu

		// for(; i <5;i++)
		new KlientSerwer(host, port);
	}

}
