package proba1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class SerwerObslugaZadan {

	// Maksymalna liczba klientów oczekuj¹cych na po³¹czenie
	private static int oczekujacy = 20;
	// Gniazdo serwera
	private ServerSocket serwerSocket = null;
	// Zmienna odpowiadaj¹ca za dzia³anie serwera
	private boolean serwerDziala = true;
	
	 PolaczenieZBazaDanych polaczenieBD;

	public static void main(String[] args) {
		
		// Inicjalizacja gniazda serwera
		ServerSocket sS = null;
		// Nazwa hosta
		String host = "127.0.0.1";
		// Numer portu
		int port = 1500;
		// Adres gniazda (adres IP + port)
		InetSocketAddress iSA = new InetSocketAddress(host, port);
		try {
			// Utworzenie gniazda serwera
			sS = new ServerSocket();
			// Zwi¹zanie gniazda serwera z adresem gniazda(adres IP + port)
			sS.bind(iSA,oczekujacy);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B³¹d przy utworzeniu gniazda");
			System.exit(1);
		}
		// Start serwera
		new SerwerObslugaZadan(sS);
	}

	public SerwerObslugaZadan(ServerSocket sS) {
		polaczenieBD = new PolaczenieZBazaDanych();
		// Ustawienie gniazda serwera
		this.serwerSocket = sS;
		System.out.println("Serwer: - Rozpoczêcie dzia³ania");
		// Nasluchiwanie polaczen od klientow
		obslugaPolaczenOdKlineta(); 
	}

	public void obslugaPolaczenOdKlineta() {
		// Inicjalizacja gniazda komunikacji z polaczonym klientem
		Socket connection = null;
		// Nieskoczona petla - serwer kontynuuje swoje dzialane
		while (serwerDziala) {
			try {
				// Nasluch i akceptacja po³aczen
				connection = serwerSocket.accept();
				System.out.println("Polaczenie ustawione ");
				System.out.println("Klient " + KlientSerwer.ktoryKlient++);
				// Start watku obs³ugi zlecen
				new ObslugaZadan(connection,polaczenieBD).start();
				
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} 
		try {
			// Zamkniêcie gniazda serwera
			serwerSocket.close();
		} catch (Exception exc) {
		}
	}
}
