package proba1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ObslugaZadan extends Thread {

	private int paczka_ID = 0;
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
				System.out.println(flag + " 2");

				exc.printStackTrace();
				try {
					System.out.println(flag + " 3");
					// Zamkniecie gniazda klienta
					connection.close();
					flag = false;
				} catch (Exception e) {
				}
			}
		}

	}

	private void ObslugaKomunikatowOdKlienta(String komunikat) {

		if (komunikat.contains("Wyslij Paczke")) {
			paczka_ID++;
			out.println(komunikat + " ... OK");
			out.flush();
			out.println(paczka_ID);
			out.flush();
			try {
				ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
				Paczka p = null;
				p = (Paczka) ois.readObject();
				if (!(p.equals(null))) {
					// WYSY£AMY TO DO BAZY DANYCH I POBIERAMY HASLO
					System.out.println(p.toString());
				}
			} catch (Exception exc) {
				exc.printStackTrace();
				try {
					// Zamkniecie gniazda klienta
					connection.close();
				} catch (Exception e) {
				}

			}
		} else if (komunikat.contains("ZnajdzSwojaPaczke")) {
			out.println(komunikat + " ... OK");
			out.flush();
			String numerPaczki = odczytWiadomosciOdKlienta();
			System.out.println(numerPaczki);
			// SPRAWDZANIE W BAZIE DANYCH paczki
			Paczka p = new Paczka(1, (float) 12.4, 2, true, "List", true, "a", "b", 1, 2, 31, 333, "lalala", "cacasrea",
					3, 4, 11, 111);
			try {
				ObjectOutputStream oos = new ObjectOutputStream(connection.getOutputStream());
				oos.writeObject(p);
				oos.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (komunikat.contains("ZalogujKurier")) {
			String login = odczytWiadomosciOdKlienta();
			String haslo = odczytWiadomosciOdKlienta();
			// SPRAWDZANIE W BAZIE DANYCH HASLA
			String czyHasloPoprawne = sprawdzanieHasla(login, haslo);
			System.out.println(login + " " + haslo);
			out.println(czyHasloPoprawne + " ... OK");
			out.flush();
			String wiadomosc = odczytWiadomosciOdKlienta();
			System.out.println(wiadomosc);
			Paczka p[] = new Paczka[2];
			p[0] = new Paczka(1,(float) 12.4, 2, true, "List", true, "a", "b", 1, 2, 31, 333, "lalala", "cacasrea", 3, 4,
					11, 111);
			p[1] = new Paczka(2,(float) 12.4, 2, true, "List", true, "a", "b", 1, 2, 31, 333, "lalala", "cacasrea", 3, 4,
					11, 111);
			try {
				ObjectOutputStream oos = new ObjectOutputStream(connection.getOutputStream());
				oos.writeObject(p);
				oos.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (komunikat.contains("PrzypomnijHaslo")) {
			out.println(komunikat + " ... OK");
			out.flush();
			String login = odczytWiadomosciOdKlienta();
			System.out.println(login);
			// SPRAWDZANIE W BAZIE DANYCH podpowiedzi do hasla
			out.println("hehehehhe");
			out.flush();

		} else if (komunikat.contains("Powrot")) {
			out.println(komunikat + " ... OK");
			out.flush();

		} else if (komunikat.contains("Wyloguj")) {
			out.println(komunikat + " ... OK");
			out.flush();

		} else if (komunikat.contains("Wyjscie")) {
			out.println(komunikat + " ... Exit");
			out.flush();

		}
	}

	private String odczytWiadomosciOdKlienta() {
		boolean flagWiadomoscOdKlienta = true;
		String wiadomoscOdKlienta = null;
		while (flagWiadomoscOdKlienta) {
			try {
				if (in.ready()) {
					wiadomoscOdKlienta = in.readLine();
					flagWiadomoscOdKlienta = false;
				}
			} catch (Exception exc) {
				exc.printStackTrace();
				try {
					// Zamkniecie gniazda klienta
					connection.close();
					flagWiadomoscOdKlienta = false;
				} catch (Exception e) {
				}
			}
		}
		return wiadomoscOdKlienta;
	}

	private String sprawdzanieHasla(String login, String haslo) {
		String loginHaslo = "a";
		boolean flagHaslo = true;
		while (flagHaslo) {
			if (loginHaslo.contains(haslo))
				return "Poprawne";
			else
				return "Bledne";
		}
		return "Blad";
	}
}
