package baza;

import java.io.Serializable;

public class NADAWCA implements Serializable{
	private int idNadawcy;
	private String imie;
	private String nazwisko;
	private String miasto;
	private int nrBudynku;
	private String ulica;
	private int nrLokalu;
	private String kodPocztowy;

	public NADAWCA(int idNadawcy, String imie, String nazwisko, String miasto, int nrBudynku, String ulica,
			int nrLokalu, String kodPocztowy) {
		this.idNadawcy = idNadawcy;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.miasto = miasto;
		this.nrBudynku = nrBudynku;
		this.ulica = ulica;
		this.nrLokalu = nrLokalu;
		this.kodPocztowy = kodPocztowy;
	}

	public String getkodPocztowy() {
		return kodPocztowy;
	}

	@Override
	public String toString() {
		if (nrLokalu == 0) {
			return "\n	ID = " + idNadawcy + "\n	Imie = " + imie + "\n	Nazwisko = " + nazwisko
					+ "\n	Miasto = " + miasto + "\n	Ulica = " + ulica + "\n	Numer domu = " + nrBudynku
					+ "\n	Kod pocztowy = " + kodPocztowy;
		} else
			return "\n	ID = " + idNadawcy + "\n	Imie = " + imie + "\n	Nazwisko = " + nazwisko
					+ "\n	Miasto = " + miasto + "\n	Ulica = " + ulica + "\n	Numer domu = " + nrBudynku
					+ "\n	Numer mieszkania = " + nrLokalu + "\n	Kod pocztowy = " + kodPocztowy;
	}

	public int getidNadawcy() {
		return idNadawcy;
	}

	public String getImie() {
		return imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public String getMiasto() {
		return miasto;
	}

	public int getNrBudynku() {
		return nrBudynku;
	}

	public String getUlica() {
		return ulica;
	}

	public int getNrLokalu() {
		return nrLokalu;
	}
}
