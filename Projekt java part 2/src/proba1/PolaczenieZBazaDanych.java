package proba1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import baza.NADAWCA;
import baza.ODBIORCA;
import baza.KURIER;
import baza.MAGAZYN;
import baza.PUNKTODBIORU;
import baza.PACZKA;

public class PolaczenieZBazaDanych {

	private Connection conection = null;

	public static void main(String args[]) {
		new PolaczenieZBazaDanych();
	}

	public PolaczenieZBazaDanych() {
		try {
			// wczytanie sterownikow polaczenia z baza danych
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// utworzenie obiektu polaczenia
			conection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "ukasz", "ukasz");

		} catch (Exception e) {
		}
	}

	// wyszukaj paczke - > dziala jak Dorota chciala
	public PACZKA pobierzPaczke(int idPaczki) {
		PACZKA paczka = null;
		Statement statement_Paczka = null;
		Statement statement_Odbiorca = null;
		Statement statement_Nadawca = null;
		ResultSet resultSet_Paczka = null;
		ResultSet resultSet_Odbiorca = null;
		ResultSet resultSet_Nadawca = null;
		try {
			statement_Paczka = conection.createStatement();
			statement_Odbiorca = conection.createStatement();
			statement_Nadawca = conection.createStatement();

			resultSet_Paczka = statement_Paczka.executeQuery("select * from PACZKA where idPaczki=" + idPaczki);
			resultSet_Paczka.next();
			int idO = resultSet_Paczka.getInt("idOdbiorcy");
			int idN = resultSet_Paczka.getInt("idNadawcy");
			resultSet_Odbiorca = statement_Odbiorca.executeQuery("select * from ODBIORCA where idOdbiorcy=" + idO);
			resultSet_Odbiorca.next();
			resultSet_Nadawca = statement_Nadawca.executeQuery("select * from NADAWCA where idNadawcy=" + idN);
			resultSet_Nadawca.next();

			paczka = new PACZKA(resultSet_Paczka.getInt("idPaczki"), resultSet_Paczka.getString("stan"),
					resultSet_Paczka.getFloat("koszt"), resultSet_Paczka.getDouble("waga"),
					resultSet_Paczka.getBoolean("delikatna"), resultSet_Paczka.getString("rodzaj"),
					resultSet_Paczka.getBoolean("ekspres"), resultSet_Odbiorca.getString("miasto"),
					resultSet_Odbiorca.getString("ulica"), resultSet_Odbiorca.getInt("nrBudynku"),
					resultSet_Odbiorca.getInt("nrLokalu"), resultSet_Odbiorca.getString("kodPocztowy"),
					resultSet_Odbiorca.getString("imie"), resultSet_Odbiorca.getString("nazwisko"),
					resultSet_Nadawca.getString("miasto"), resultSet_Nadawca.getString("ulica"),
					resultSet_Nadawca.getInt("nrBudynku"), resultSet_Nadawca.getInt("nrLokalu"),
					resultSet_Nadawca.getString("kodPocztowy"), resultSet_Nadawca.getString("imie"),
					resultSet_Nadawca.getString("nazwisko"), resultSet_Paczka.getDate("DATAPRZYJECIA"), idO, idN,
					resultSet_Paczka.getDate("DATADOSTARCZENIA"));

		} catch (SQLException e) {
			System.out.println("error");
			e.printStackTrace();
		} finally {
			close(resultSet_Paczka);
			close(resultSet_Odbiorca);
			close(resultSet_Nadawca);
			close(statement_Paczka);
			close(statement_Odbiorca);
			close(statement_Nadawca);
		}
		return paczka;
	}

	public int idKolejnejPaczki() {
		int id = -1;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = conection.createStatement();
			resultSet = statement.executeQuery("select MAX(idPaczki) from PACZKA");
			resultSet.next();
			id = resultSet.getInt("MAX(idPaczki)");
			id += 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(resultSet);
			close(statement);
		}

		return id;
	}

	public int idKolejnegoOdbiorcy() {
		int id = -1;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = conection.createStatement();
			resultSet = statement.executeQuery("select MAX(idodbiorcy) from ODBIORCA");
			resultSet.next();
			id = resultSet.getInt("MAX(idodbiorcy)");
			id += 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(resultSet);
			close(statement);
		}

		return id;
	}

	public int idKolejnegoNadawcy() {
		int id = -1;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = conection.createStatement();
			resultSet = statement.executeQuery("select MAX(idnadawcy) from NADAWCA");
			resultSet.next();
			id = resultSet.getInt("MAX(idnadawcy)");
			id += 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(resultSet);
			close(statement);
		}

		return id;
	}

	// zmien status paczki -> dzia³a jak Dorota chcia³a
	public int zmienStatusPAczki(int idPaczki, String stan) {
		CallableStatement callableStatement = null;
		try {
			callableStatement = conection.prepareCall("BEGIN zmienStatusPaczki(?,?); END;");
			callableStatement.setInt(1, idPaczki);
			callableStatement.setString(2, stan);
			callableStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			close(callableStatement);
		}
		return 1;
	}

	// usun paczke -> dziala jak Dorota chciala
	public int usunPaczke(int idPaczki) {
		Statement statement_Paczka = null;
		Statement statement_Odbiorca = null;
		Statement statement_Nadawca = null;
		ResultSet resultSet = null;
		int idO;
		int idN;
		try {
			statement_Paczka = conection.createStatement();
			statement_Odbiorca = conection.createStatement();
			statement_Nadawca = conection.createStatement();

			resultSet = statement_Paczka.executeQuery("select * from PACZKA where IDpaczki = " + idPaczki);
			resultSet.next();
			idO = resultSet.getInt("idOdbiorcy");
			idN = resultSet.getInt("idNadawcy");
			System.out.println(idO + " " + idN);
			statement_Paczka.executeUpdate("delete from PACZKA where IDpaczki = " + idPaczki);
			statement_Odbiorca.executeUpdate("delete from ODBIORCA where IDODBIORCY = " + idO);
			statement_Nadawca.executeUpdate("delete from NADAWCA where IDNADAWCY = " + idN);

		} catch (SQLException e) {
			return 0;
		} finally {
			close(statement_Paczka);
			close(statement_Odbiorca);
			close(statement_Nadawca);
			close(resultSet);
		}
		return 1;
	}

	// wyszukaj podpowiedz ->dziala jak Dorota chciala
	public String wyszukajPodpowiedz(int idKuriera) {
		String podpowiedz = "";
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = conection.createStatement();
			resultSet = statement.executeQuery("select * from kurier where IDKURIERA=" + idKuriera);
			resultSet.next();
			if (!(resultSet.isAfterLast()))
				podpowiedz = resultSet.getString("podpowiedz");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(resultSet);
			close(statement);
		}

		return podpowiedz;
	}

	// dodaj paczke
	// dodaj zamowienie - > dziala jak Dorota chciala
	public int dodajPaczke(PACZKA paczka) {
		// wywo³anie procedury
		CallableStatement callableStatement = null;
		try {
			// 1 -> idMagazynu
			// 3 -> idPunktu
			// 2 -> idKuriera
			// data dostarczenia
			// Date date1 = new Date();

			java.util.Date utilDate2 = paczka.getDataPrzyjecia();
			java.sql.Date dataPrzyjecia = new java.sql.Date(utilDate2.getTime());
			java.util.Date utilDate = paczka.getDataDostarczenia();
			java.sql.Date dataDostarczenia = new java.sql.Date(utilDate.getTime());
			callableStatement = conection
					.prepareCall("BEGIN dodajPaczke(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); END;");
			callableStatement.setInt(1, 1);
			callableStatement.setInt(2, 3);
			callableStatement.setInt(3, 2);
			callableStatement.setString(4, paczka.getNadawca().getImie());
			callableStatement.setString(5, paczka.getNadawca().getNazwisko());
			callableStatement.setString(6, paczka.getNadawca().getMiasto());
			callableStatement.setInt(7, paczka.getNadawca().getNrBudynku());
			callableStatement.setString(8, paczka.getNadawca().getkodPocztowy());
			callableStatement.setString(9, paczka.getAdresat().getImie());
			callableStatement.setString(10, paczka.getAdresat().getNazwisko());
			callableStatement.setString(11, paczka.getAdresat().getMiasto());
			callableStatement.setInt(12, paczka.getAdresat().getNrBudynku());
			callableStatement.setString(13, paczka.getAdresat().getKodPocztowy());
			callableStatement.setString(14, paczka.getStan());
			callableStatement.setFloat(15, paczka.getKoszt());
			callableStatement.setFloat(16, (float) paczka.getWaga());
			callableStatement.setBoolean(17, paczka.isEkspres());
			callableStatement.setBoolean(18, paczka.isDelikatna());
			callableStatement.setDate(19, dataPrzyjecia);
			callableStatement.setDate(20, dataDostarczenia);
			callableStatement.setString(21, paczka.getNadawca().getUlica());
			callableStatement.setInt(22, paczka.getNadawca().getNrLokalu());
			callableStatement.setString(23, paczka.getAdresat().getUlica());
			callableStatement.setInt(24, paczka.getAdresat().getNrLokalu());
			callableStatement.setString(25, paczka.getRodzaj());
			callableStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			close(callableStatement);
		}
		return 1;
	}

	// zaloguj - > dziala jak Dorota chciala
	public boolean czyHasloPoprawne(int idKuriera, String haslo) {
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			System.out.println(idKuriera);
			statement = conection.createStatement();
			resultSet = statement.executeQuery("select * from KURIER where idKuriera=" + idKuriera);
			resultSet.next();
			String hasloBaza = "";
			if (!(resultSet.isAfterLast())) {
				hasloBaza = resultSet.getString("haslo");
			}
			if (hasloBaza.equals(haslo))
				return true;
			else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(resultSet);
			close(statement);
		}
	}

	// paczki do odebrania -> dziala jak Dorota chciala
	public ArrayList<PACZKA> paczkiDoOdebrania() {
		ArrayList<PACZKA> doOdebrania = new ArrayList<PACZKA>();
		Statement statement_Paczka = null;
		Statement statement_Odbiorca = null;
		Statement statement_Nadawca = null;
		ResultSet resultSet_Paczka = null;
		ResultSet resultSet_Odbiorca = null;
		ResultSet resultSet_Nadawca = null;
		int idO;
		int idN;
		try {
			statement_Paczka = conection.createStatement();
			statement_Odbiorca = conection.createStatement();
			statement_Nadawca = conection.createStatement();
			resultSet_Paczka = statement_Paczka.executeQuery("select * from PACZKA where stan= \'Do odebrania\'");

			while (resultSet_Paczka.next()) {
				idO = resultSet_Paczka.getInt("idOdbiorcy");
				idN = resultSet_Paczka.getInt("idNadawcy");
				resultSet_Odbiorca = statement_Odbiorca.executeQuery("select * from ODBIORCA where idOdbiorcy=" + idO);
				resultSet_Odbiorca.next();
				resultSet_Nadawca = statement_Nadawca.executeQuery("select * from NADAWCA where idNadawcy=" + idN);
				resultSet_Nadawca.next();

				doOdebrania.add(new PACZKA(resultSet_Paczka.getInt("idPaczki"), resultSet_Paczka.getString("stan"),
						resultSet_Paczka.getFloat("koszt"), resultSet_Paczka.getDouble("waga"),
						resultSet_Paczka.getBoolean("delikatna"), resultSet_Paczka.getString("rodzaj"),
						// resultSet_Paczka.getBoolean("delikatna"), "List",
						resultSet_Paczka.getBoolean("ekspres"), resultSet_Odbiorca.getString("miasto"),
						resultSet_Odbiorca.getString("ulica"), resultSet_Odbiorca.getInt("nrBudynku"),
						resultSet_Odbiorca.getInt("nrLokalu"), resultSet_Odbiorca.getString("kodPocztowy"),
						resultSet_Odbiorca.getString("imie"), resultSet_Odbiorca.getString("nazwisko"),
						resultSet_Nadawca.getString("miasto"), resultSet_Nadawca.getString("ulica"),
						resultSet_Nadawca.getInt("nrBudynku"), resultSet_Nadawca.getInt("nrLokalu"),
						resultSet_Nadawca.getString("kodPocztowy"), resultSet_Nadawca.getString("imie"),
						resultSet_Nadawca.getString("nazwisko"), resultSet_Paczka.getDate("DATAPRZYJECIA"), idO, idN,
						resultSet_Paczka.getDate("DATADOSTARCZENIA")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(resultSet_Paczka);
			close(resultSet_Odbiorca);
			close(resultSet_Nadawca);
			close(statement_Paczka);
			close(statement_Odbiorca);
			close(statement_Nadawca);
		}
		return doOdebrania;

	}

	// paczki do dostarczenia -> dziala jak Dorota chciala
	public ArrayList<PACZKA> paczkiDoDostarczenia() {
		ArrayList<PACZKA> doDostarczenia = new ArrayList<PACZKA>();
		Statement statement_Paczka = null;
		Statement statement_Odbiorca = null;
		Statement statement_Nadawca = null;
		ResultSet resultSet_Paczka = null;
		ResultSet resultSet_Odbiorca = null;
		ResultSet resultSet_Nadawca = null;
		int idO;
		int idN;
		try {
			statement_Paczka = conection.createStatement();
			statement_Odbiorca = conection.createStatement();
			statement_Nadawca = conection.createStatement();

			resultSet_Paczka = statement_Paczka.executeQuery("select * from PACZKA where stan != \'Do odebrania\'");
			while (resultSet_Paczka.next()) {
				idO = resultSet_Paczka.getInt("idOdbiorcy");
				idN = resultSet_Paczka.getInt("idNadawcy");
				System.out.println(idO + " " + idN);
				if ((idO != 0) && (idN != 0)) {
					resultSet_Odbiorca = statement_Odbiorca
							.executeQuery("select * from ODBIORCA where idOdbiorcy=" + idO);
					resultSet_Odbiorca.next();
					resultSet_Nadawca = statement_Nadawca.executeQuery("select * from NADAWCA where idNadawcy=" + idN);
					resultSet_Nadawca.next();

					doDostarczenia
							.add(new PACZKA(resultSet_Paczka.getInt("idPaczki"), resultSet_Paczka.getString("stan"),
									resultSet_Paczka.getFloat("koszt"), resultSet_Paczka.getDouble("waga"),
									resultSet_Paczka.getBoolean("delikatna"), resultSet_Paczka.getString("rodzaj"),
									resultSet_Paczka.getBoolean("ekspres"), resultSet_Odbiorca.getString("miasto"),
									resultSet_Odbiorca.getString("ulica"), resultSet_Odbiorca.getInt("nrBudynku"),
									resultSet_Odbiorca.getInt("nrLokalu"), resultSet_Odbiorca.getString("kodPocztowy"),
									resultSet_Odbiorca.getString("imie"), resultSet_Odbiorca.getString("nazwisko"),
									resultSet_Nadawca.getString("miasto"), resultSet_Nadawca.getString("ulica"),
									resultSet_Nadawca.getInt("nrBudynku"), resultSet_Nadawca.getInt("nrLokalu"),
									resultSet_Nadawca.getString("kodPocztowy"), resultSet_Nadawca.getString("imie"),
									resultSet_Nadawca.getString("nazwisko"), resultSet_Paczka.getDate("DATAPRZYJECIA"),
									idO, idN, resultSet_Paczka.getDate("DATADOSTARCZENIA")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(resultSet_Paczka);
			close(resultSet_Odbiorca);
			close(resultSet_Nadawca);
			close(statement_Paczka);
			close(statement_Odbiorca);
			close(statement_Nadawca);
		}
		return doDostarczenia;

	}

	public static void close(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(CallableStatement callableStatement) {
		try {
			if (callableStatement != null) {
				callableStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
