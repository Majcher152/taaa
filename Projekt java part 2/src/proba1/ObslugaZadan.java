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
	private boolean flag = true;

	public ObslugaZadan(Socket connection) {
		// Ustawienie gniazda klienta
		this.connection = connection;
		// Klient.ktoryKlient++;
	}

	public void run() {
		// Utworzenie strumieni kominukacji z klientem (odczyt+zapis)
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream(), true);
			out.println("Klient " + KlientSerwer.ktoryKlient);
			out.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (flag) {
			try {
				String aaa = null;
				if (in.ready()) {
					aaa = in.readLine();
					System.out.println(aaa);
					ObslugaKomunikatowOdKlienta(aaa);
				}
			} catch (Exception exc) {
				exc.printStackTrace();
				try {
					// Zamkniecie gniazda klienta
					connection.close();
					flag = false;
				} catch (Exception e) {
				}
			}
		}
	}

	private void ObslugaKomunikatowOdKlienta(String komunikat) {
		out.println("no elo " + komunikat);
		out.flush();
		if (komunikat.contains("Strona")) {
			// System.out.println("dziala strona");
			// nic nie trzeba robic z baza TAK SADZE!!!
		}
		if (komunikat.contains("Logowanie")) {
			boolean flagaLogowanie = true;
			while (flagaLogowanie) {
				try {
					String login = null;
					if (in.ready()) {
						login = in.readLine();
						// WYSY£AMY TO DO BAZY DANYCH I POBIERAMY HASLO
						String haslo = "a";
						out.println(haslo);
						out.flush();
						flagaLogowanie = false;
					}
				} catch (Exception exc) {
					exc.printStackTrace();
					try {
						// Zamkniecie gniazda klienta
						connection.close();
						flagaLogowanie = false;
					} catch (Exception e) {
					}
				}

			}
			if (komunikat.contains("Wyslij Paczke")) {

			}
			if (komunikat.contains("ZnajdzSwojaPaczke")) {

			}
			if (komunikat.contains("ZalogujKurier")) {

			}
			if (komunikat.contains("PrzypomnijHaslo")) {

			}
		}
	}
}
