package proba1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

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

	// wyszukaj paczke
	public PACZKA pobierzPaczke(int idPaczki) {
		PACZKA paczka = null;
		Statement statement = null;
		ResultSet resultSet_Paczka = null;
		ResultSet resultSet_Odbiorca = null;
		ResultSet resultSet_Nadawca = null;
		try {
			statement = conection.createStatement();

			//resultSet_Paczka = statement.executeQuery("select * from PACZKA where idPaczki=" + idPaczki + ";");
			resultSet_Paczka = statement.executeQuery("select * from PACZKA;");

			resultSet_Paczka.next();
			
			System.out.println(idPaczki);
			int idO = resultSet_Paczka.getInt("idOdbiorcy");
			int idN = resultSet_Paczka.getInt("idNadawcy");
			System.out.println(idO);
			System.out.println(idN);
			resultSet_Odbiorca = statement.executeQuery("select * from ODBIORCA where idOdbiorcy=" + idO + ";");
			resultSet_Odbiorca.next();
			resultSet_Nadawca = statement.executeQuery("select * from ODBIORCA where idNadawcy=" + idN + ";");
			resultSet_Nadawca.next();

			paczka = new PACZKA(resultSet_Paczka.getInt("idPaczki"), resultSet_Paczka.getString("stan"),
					resultSet_Paczka.getFloat("koszt"), resultSet_Paczka.getDouble("waga"),
					resultSet_Paczka.getBoolean("szklo"), resultSet_Paczka.getString("rodzaj"),
					resultSet_Paczka.getBoolean("ekspres"), resultSet_Odbiorca.getString("miasto"),
					resultSet_Odbiorca.getString("ulica"), resultSet_Odbiorca.getInt("nrBudynku"),
					resultSet_Odbiorca.getInt("nrLokalu"), resultSet_Odbiorca.getString("kodPocztowy"),
					resultSet_Odbiorca.getString("imie"), resultSet_Odbiorca.getString("nazwisko"),
					resultSet_Nadawca.getString("miasto"), resultSet_Nadawca.getString("ulica"),
					resultSet_Nadawca.getInt("nrBudynku"), resultSet_Nadawca.getInt("nrLokalu"),
					resultSet_Nadawca.getString("kodPocztowy"), resultSet_Nadawca.getString("imie"),
					resultSet_Nadawca.getString("nazwisko"), resultSet_Paczka.getDate("data"), idO, idN);

		} catch (SQLException e) {
System.out.println("chuj");
			 e.printStackTrace();
		} finally {
			close(resultSet_Paczka);
			close(resultSet_Odbiorca);
			close(resultSet_Nadawca);
			close(statement);
		}
		return paczka;
	}

	// zmien status paczki
	public int zmienStatusPAczki(int idPaczki, String stan) {
		/*
		 * Statement statement = null; try { statement =
		 * conection.createStatement();
		 * statement.executeUpdate("update PACZKA set stan=" + stan +
		 * " where idPaczki='" + idPaczki + "'"); } catch (SQLException e) {
		 * e.printStackTrace(); return 0; } finally { close(statement); } return
		 * 1;
		 */
		CallableStatement callableStatement = null;
		try {
			// 1 -> idMagazynu
			// 2 -> idPunktu
			// 3 -> idKuriera
			// data dostarczenia
			callableStatement = conection.prepareCall("{call zmienStatusPaczki(" + idPaczki + ", " + stan + ")}");

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			close(callableStatement);
		}
		return 1;
	}

	// usun paczke
	public int usunPaczke(String idPaczki) {
		Statement statement = null;
		try {
			statement = conection.createStatement();
			statement.executeUpdate("delete from PACZKA where paczkaID =" + idPaczki);

		} catch (SQLException e) {
			return 0;
		} finally {
			close(statement);
		}
		return 1;
	}

	// dodaj paczke
	// dodaj zamowienie
	public int dodajPaczke(PACZKA paczka) {
		/*
		 * Statement statement_Paczka = null; Statement statement_Nadawca =
		 * null; Statement statement_Odbiorca = null; try { statement_Paczka =
		 * conection.createStatement(); // 1 -> idPunktu // 2 -> idKuriera //
		 * 3-> idMagazynu // 4 -> dataDostarczenia
		 * statement_Paczka.executeUpdate("insert into PACZKA values('','" +
		 * paczka.getidPaczki() + "','" + 1 + "'," + 2 + "," +
		 * paczka.getIdNadawcy() + "','" + paczka.getIdOdbiorcy() + "'," +
		 * paczka.getData() + "','" + 4 + "'," + paczka.getStan() + "','" +
		 * paczka.getKoszt() + "'," + paczka.getWaga() + "','" +
		 * paczka.isEkspres() + "'," + paczka.isSzklo() + ")");
		 * 
		 * statement_Nadawca = conection.createStatement();
		 * 
		 * statement_Nadawca.executeUpdate("insert into NADAWCA values('','" +
		 * paczka.getidPaczki() + "','" + 1 + "'," + 2 + "," +
		 * paczka.getIdNadawcy() + "','" + paczka.getIdOdbiorcy() + "'," +
		 * paczka.getData() + "','" + 4 + "'," + paczka.getStan() + "','" +
		 * paczka.getKoszt() + "'," + paczka.getWaga() + "','" +
		 * paczka.isEkspres() + "'," + paczka.isSzklo() + ")");
		 * 
		 * statement_Odbiorca = conection.createStatement(); } catch (
		 * 
		 * SQLException e) { e.printStackTrace(); return 0; } finally {
		 * close(statement_Paczka); close(statement_Nadawca);
		 * close(statement_Odbiorca); } return 1;
		 */

		// wywo³anie procedury
		CallableStatement callableStatement = null;
		try {
			// 1 -> idMagazynu
			// 1 -> idPunktu
			// 1 -> idKuriera
			// data dostarczenia
			Date date1 = new Date();
			/*callableStatement = conection.prepareCall("BEGIN dodajPaczke(" + 1 + ", " + 1 + ", " + 1 + ", '"
					+ paczka.getNadawca().getImie() + "' , '" + paczka.getNadawca().getNazwisko() + "', '"
					+ paczka.getNadawca().getMiasto() + "', '" + paczka.getNadawca().getNrBudynku() + "', '"
					+ paczka.getNadawca().getkodPocztowy() + "', '" + paczka.getAdresat().getImie() + "', '"
					+ paczka.getAdresat().getNazwisko() + "', '" + paczka.getAdresat().getMiasto() + "', "
					+ paczka.getAdresat().getNrBudynku() + ", '" + paczka.getAdresat().getKodPocztowy() + "', '"
					+ paczka.getStan() + "', " + paczka.getKoszt() + ", " + paczka.getWaga() + ", " + paczka.isEkspres()
					+ ", " + paczka.isDelikatna() + ", '" + paczka.getDataPrzyjecia() + "', '" + date1 + "', '"
					+ paczka.getNadawca().getUlica() + "', " + paczka.getNadawca().getNrLokalu() + ", '"
					+ paczka.getAdresat().getUlica() + "', " + paczka.getAdresat().getNrLokalu() + "); END;");*/
			callableStatement = conection.prepareCall("BEGIN dodajPaczke(1,1, 1, 'imie','nazwisko', 'miasto', 2, '31-435', 'imie', 'nazwisko', 'miasto',2 , ' 31-435 ', 'do dostarczenia ', 21.1,  31 , 0, 1, '2008-11-11', '2008-11-11','ulica ',  2 , 'ulica ',  2 ); END;");
		
			/*
			 * 	BEGIN 
			dodajPaczke(1,1, 1, 'imie','nazwisko', 'miasto', 2, '31-435', 'imie', 'nazwisko', 'miasto',
			2 , ' 31-435 ', 'do dostarczenia ', 21.1,  31 , 0, 1, '2008-11-11', '2008-11-11',
			'ulica ',  2 , 'ulica ',  2 ); 
			END;
			 */
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			close(callableStatement);
		}
		return 1;
	}

	// zaloguj
	public boolean czyHasloPoprawne(int idKuriera, String haslo) {
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = conection.createStatement();
			resultSet = statement.executeQuery("select * from KURIER where idKuriera='" + idKuriera + "'");
			resultSet.next();

			if (resultSet.getString(haslo) == haslo)
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

	// paczki do odebrania
	public ArrayList<PACZKA> paczkiDoOdebrania() {
		ArrayList<PACZKA> doOdebrania = new ArrayList<PACZKA>();
		Statement statement = null;
		ResultSet resultSet_Paczka = null;
		ResultSet resultSet_Odbiorca = null;
		ResultSet resultSet_Nadawca = null;
		int idO;
		int idN;
		try {
			statement = conection.createStatement();

			resultSet_Paczka = statement.executeQuery("select * from PACZKI where stan= \"Do Odebrania\"");

			while (resultSet_Paczka.next()) {
				idO = resultSet_Paczka.getInt("idOdbiorcy");
				idN = resultSet_Paczka.getInt("idNadawcy");
				resultSet_Odbiorca = statement.executeQuery("select * from ODBIORCA where idOdbiorcy='" + idO + "'");
				resultSet_Odbiorca.next();
				resultSet_Nadawca = statement.executeQuery("select * from ODBIORCA where idNadawcy='" + idN + "'");
				resultSet_Nadawca.next();

				doOdebrania.add(new PACZKA(resultSet_Paczka.getInt("idPaczki"), resultSet_Paczka.getString("stan"),
						resultSet_Paczka.getFloat("koszt"), resultSet_Paczka.getDouble("waga"),
						resultSet_Paczka.getBoolean("szklo"), resultSet_Paczka.getString("rodzaj"),
						resultSet_Paczka.getBoolean("ekspres"), resultSet_Odbiorca.getString("miasto"),
						resultSet_Odbiorca.getString("ulica"), resultSet_Odbiorca.getInt("nrBudynku"),
						resultSet_Odbiorca.getInt("nrLokalu"), resultSet_Odbiorca.getString("kodPocztowy"),
						resultSet_Odbiorca.getString("imie"), resultSet_Odbiorca.getString("nazwisko"),
						resultSet_Nadawca.getString("miasto"), resultSet_Nadawca.getString("ulica"),
						resultSet_Nadawca.getInt("nrBudynku"), resultSet_Nadawca.getInt("nrLokalu"),
						resultSet_Nadawca.getString("kodPocztowy"), resultSet_Nadawca.getString("imie"),
						resultSet_Nadawca.getString("nazwisko"), resultSet_Paczka.getDate("data"), idO, idN));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(resultSet_Paczka);
			close(resultSet_Odbiorca);
			close(resultSet_Nadawca);
			close(statement);
		}
		return null;

	}

	// paczki do dostarczenia
	public ArrayList<PACZKA> paczkiDoDostarczenia() {
		ArrayList<PACZKA> doDostarczenia = new ArrayList<PACZKA>();
		Statement statement = null;
		ResultSet resultSet_Paczka = null;
		ResultSet resultSet_Odbiorca = null;
		ResultSet resultSet_Nadawca = null;
		int idO;
		int idN;
		try {
			statement = conection.createStatement();

			resultSet_Paczka = statement.executeQuery("select * from PACZKI where stan != \"Do Odebrania\"");

			while (resultSet_Paczka.next()) {
				idO = resultSet_Paczka.getInt("idOdbiorcy");
				idN = resultSet_Paczka.getInt("idNadawcy");
				resultSet_Odbiorca = statement.executeQuery("select * from ODBIORCA where idOdbiorcy='" + idO + "'");
				resultSet_Odbiorca.next();
				resultSet_Nadawca = statement.executeQuery("select * from ODBIORCA where idNadawcy='" + idN + "'");
				resultSet_Nadawca.next();

				doDostarczenia.add(new PACZKA(resultSet_Paczka.getInt("idPaczki"), resultSet_Paczka.getString("stan"),
						resultSet_Paczka.getFloat("koszt"), resultSet_Paczka.getDouble("waga"),
						resultSet_Paczka.getBoolean("szklo"), resultSet_Paczka.getString("rodzaj"),
						resultSet_Paczka.getBoolean("ekspres"), resultSet_Odbiorca.getString("miasto"),
						resultSet_Odbiorca.getString("ulica"), resultSet_Odbiorca.getInt("nrBudynku"),
						resultSet_Odbiorca.getInt("nrLokalu"), resultSet_Odbiorca.getString("kodPocztowy"),
						resultSet_Odbiorca.getString("imie"), resultSet_Odbiorca.getString("nazwisko"),
						resultSet_Nadawca.getString("miasto"), resultSet_Nadawca.getString("ulica"),
						resultSet_Nadawca.getInt("nrBudynku"), resultSet_Nadawca.getInt("nrLokalu"),
						resultSet_Nadawca.getString("kodPocztowy"), resultSet_Nadawca.getString("imie"),
						resultSet_Nadawca.getString("nazwisko"), resultSet_Paczka.getDate("data"), idO, idN));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(resultSet_Paczka);
			close(resultSet_Odbiorca);
			close(resultSet_Nadawca);
			close(statement);
		}
		return null;

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
