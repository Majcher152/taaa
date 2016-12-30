package proba1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Pattern;

public class ObslugaZadan extends Thread {

	// Strumienie gniazda komunikacji z klientem(odczyt + zapis)
	private BufferedReader in = null;
	private PrintWriter out = null;
	// Gniazdo klienta
	private Socket connection = null;

	// #######################################
	// Nie dzia³a, jak narazie
	// #######################################
	private void wypiszOdpowiedz(String odpowiedz) throws IOException {
		if (odpowiedz != null)
			// Wypisanie odpowiedzi klientowi
			out.println("GOOD JOB " + odpowiedz);
	}

	public ObslugaZadan(Socket connection) {
		// Ustawienie gniazda klienta
		this.connection = connection;
		// Klient.ktoryKlient++;
		try {
			// Utworzenie strumieni kominukacji z klientem (odczyt+zapis)
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream(), true);
			out.println("Klient " + KlientSerwer.ktoryKlient);
		} catch (Exception exc) {
			exc.printStackTrace();
			try {
				// Zamkniecie gniazda klienta
				connection.close();
			} catch (Exception e) {
			}
		}
	}

	// #######################################
	// Nie dzia³a, jak narazie, tzn dzia³a ale bez b³êdów ale nic nie wypisze
	// #######################################
	public void run() {
		try {
			String line = in.readLine();
			// Odczytywanie zlecen klienta

			switch (line) {
			case "1":
				// logowanie
				break;
			case "2":
				// Zapomniane Has³o
				break;
			case "3":
				// exit
				wypiszOdpowiedz("bye bye My love <3 ");
				break;
			default:
				// Odpowiedz serwera
				String odpowiedz;
				// System.out.println(line + "\n");
				wypiszOdpowiedz(line);

			}

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			try {
				// Zamkniecie strumieni i gniazda
				in.close();
				out.close();
				connection.close();
			} catch (Exception exc) {
			}
		}
	}
}
