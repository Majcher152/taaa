package proba1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import baza.PACZKA;

import java.sql.*;

public class ObslugaZadan extends Thread {

	// Strumienie gniazda komunikacji z klientem(odczyt + zapis)
	private BufferedReader in = null;
	private PrintWriter out = null;
	// Gniazdo klienta
	private Socket connection = null;
	private boolean flag = true;

	PolaczenieZBazaDanych polaczenieBD;

	public ObslugaZadan(Socket connection, PolaczenieZBazaDanych polaczenieBD) {
		// Ustawienie gniazda klienta
		this.connection = connection;
		this.polaczenieBD = polaczenieBD;
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
				String komunikat = "";
				if (in.ready()) {
					komunikat = in.readLine();
					System.out.println(komunikat);

					ObslugaKomunikatowOdKlienta(komunikat);
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
			int paczka_ID = -1;
			int odbiorca_ID = -1;
			int nadawca_ID = -1;
			out.println(komunikat + " ... OK");
			out.flush();
			paczka_ID = polaczenieBD.idKolejnejPaczki();
			out.println(paczka_ID);
			out.flush();
			odbiorca_ID = polaczenieBD.idKolejnegoOdbiorcy();
			out.println(odbiorca_ID);
			out.flush();
			nadawca_ID = polaczenieBD.idKolejnegoNadawcy();
			out.println(nadawca_ID);
			out.flush();
			try {
				ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
				PACZKA p = null;
				p = (PACZKA) ois.readObject();
				if (!(p.equals(null))) {
					// dodanie paczki do bazy danych
					polaczenieBD.dodajPaczke(p);
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
			if (!(numerPaczki.isEmpty())) {

				int idPaczki = (int) Double.parseDouble(numerPaczki);
				System.out.println(numerPaczki);
				// SPRAWDZANIE W BAZIE DANYCH paczki
				PACZKA p = polaczenieBD.pobierzPaczke(idPaczki);
				// System.out.println(p.toString());
				try {
					ObjectOutputStream oos = new ObjectOutputStream(connection.getOutputStream());
					oos.writeObject(p);
					oos.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (komunikat.contains("ZalogujKurier")) {
			String login = odczytWiadomosciOdKlienta();
			String haslo = odczytWiadomosciOdKlienta();
			String czyHasloPoprawne = sprawdzanieHasla(login, haslo);
			System.out.println(login + " " + haslo);
			out.println(czyHasloPoprawne + " ... OK");
			out.flush();
			if (czyHasloPoprawne.contains("Poprawne")) {
				String wiadomosc = odczytWiadomosciOdKlienta();
				System.out.println(wiadomosc);
				ArrayList<PACZKA> doOdebrania = polaczenieBD.paczkiDoOdebrania();
				ArrayList<PACZKA> doDostarczenia = polaczenieBD.paczkiDoDostarczenia();
				try {
					ObjectOutputStream oos = new ObjectOutputStream(connection.getOutputStream());
					oos.writeObject(doOdebrania);
					oos.flush();
					oos.writeObject(doDostarczenia);
					oos.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else if (komunikat.contains("PrzypomnijHaslo")) {
			out.println(komunikat + " ... OK");
			out.flush();
			String podpowiedz = "";
			String login = odczytWiadomosciOdKlienta();
			if (!(login.equals(""))) {
				int idLoginu = (int) Double.parseDouble(login);
				System.out.println(login);
				podpowiedz = polaczenieBD.wyszukajPodpowiedz(idLoginu);
			}
			out.println(podpowiedz);
			out.flush();

		} else if (komunikat.contains("Przekazana do nadania") || komunikat.contains("W drodze do miasta adresata")
				|| komunikat.contains("W punkcie odbioru") || komunikat.contains("Przekazana do odebrania")
				|| komunikat.contains("Do dostarczenia")) {
			ustawienieStanu(komunikat);
		} else if (komunikat.contains("Odebrana")) {
			usuwaniePrzesylki();
		} else if (komunikat.contains("Odswiez")) {

			ArrayList<PACZKA> doOdebrania = polaczenieBD.paczkiDoOdebrania();
			ArrayList<PACZKA> doDostarczenia = polaczenieBD.paczkiDoDostarczenia();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(connection.getOutputStream());
				oos.writeObject(doOdebrania);
				oos.flush();
				oos.writeObject(doDostarczenia);
				oos.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (komunikat.contains("Powrot") || komunikat.contains("Wyloguj"))

		{
			out.println(komunikat + " ... OK");
			out.flush();
		} else if (komunikat.contains("Wyjscie")) {
			out.println(komunikat + " ... Exit");
			out.flush();

		}
	}

	private void usuwaniePrzesylki() {
		String id = odczytWiadomosciOdKlienta();
		int idLiczba = (int) Double.parseDouble(id);
		System.out.println(idLiczba);
		polaczenieBD.usunPaczke(idLiczba);
	}

	private void ustawienieStanu(String komunikat) {
		String id = odczytWiadomosciOdKlienta();
		int idLiczba = (int) Double.parseDouble(id);
		polaczenieBD.zmienStatusPAczki(idLiczba, komunikat);
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
		int idLogin = -1;
		try {
			idLogin = (int) Double.parseDouble(login);
		} catch (NumberFormatException e2) {

		}
		if (polaczenieBD.czyHasloPoprawne(idLogin, haslo)) {
			return "Poprawne";
		} else
			return "Bledne";
	}
}
