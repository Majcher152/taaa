/*package proba1;

import java.io.Serializable;

public class Dane implements Serializable {

	private String miasto;
	private String ulica;
	private int nrDomu;
	private int nrMieszkania;
	private String kodPocztowy;
	private String imie;
	private String nazwisko;

	public Dane(String miasto, String ulica, int nrDomu, int nrMieszkania, String kodPocztowy, String imie,
			String nazwisko) {
		this.miasto = miasto;
		this.ulica = ulica;
		this.nrDomu = nrDomu;
		this.nrMieszkania = nrMieszkania;
		this.kodPocztowy = kodPocztowy;
		this.imie = imie;
		this.nazwisko = nazwisko;
	}

	public String getMiasto() {
		return miasto;
	}

	public String getUlica() {
		return ulica;
	}

	public int getNrDomu() {
		return nrDomu;
	}

	public int getNrMieszkania() {
		return nrMieszkania;
	}

	public String getKodPocztowy() {
		return kodPocztowy;
	}

	public String getImie() {
		return imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	@Override
	public String toString() {
		if (nrMieszkania == 0) {
			return "\n	Imie = " + imie + "\n	Nazwisko = " + nazwisko + "\n	Miasto = " + miasto + "\n	Ulica = "
					+ ulica + "\n	Numer domu = " + nrDomu + "\n	Kod pocztowy = " + kodPocztowy;
		} else
			return "\n	Imie = " + imie + "\n	Nazwisko = " + nazwisko + "\n	Miasto = " + miasto + "\n	Ulica = "
					+ ulica + "\n	Numer domu = " + nrDomu + "\n	Numer mieszkania = " + nrMieszkania
					+ "\n	Kod pocztowy = " + kodPocztowy;
	}

}
*/