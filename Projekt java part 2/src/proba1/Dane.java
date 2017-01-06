package proba1;

import java.io.Serializable;

public class Dane implements Serializable {

	private String miasto;
	private String ulica;
	private int nrDomu;
	private int nrMieszkania;
	private int kodPocztowy1;
	private int kodPocztowy2;

	public Dane(String miasto, String ulica, int nrDomu, int nrMieszkania, int kodPocztowy1, int kodPocztowy2) {
		this.miasto = miasto;
		this.ulica = ulica;
		this.nrDomu = nrDomu;
		this.nrMieszkania = nrMieszkania;
		this.kodPocztowy1 = kodPocztowy1;
		this.kodPocztowy2 = kodPocztowy2;
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

	public int getKodPocztowy1() {
		return kodPocztowy1;
	}

	public int getKodPocztowy2() {
		return kodPocztowy2;
	}

	@Override
	public String toString() {
		if (nrMieszkania == 0) {
			return "\n	Miasto = " + miasto + "\n	Ulica = " + ulica + "\n	Numer domu = " + nrDomu
					+ "\n	Kod pocztowy =" + kodPocztowy1 + "-" + kodPocztowy2;
		} else
			return "\n	Miasto = " + miasto + "\n	Ulica = " + ulica + "\n	Numer domu = " + nrDomu
					+ "\n	Numer mieszkania = " + nrMieszkania + "\n	Kod pocztowy = " + kodPocztowy1 + "-"
					+ kodPocztowy2;
	}

}
