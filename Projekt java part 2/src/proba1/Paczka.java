package proba1;

public class Paczka {

	private double waga;
	private boolean szklo;
	private String rodzaj;
	private boolean ekspres;
	private Dane daneAdresata;
	private Dane daneNadawcy;

	public Paczka(double waga, boolean szklo, String rodzaj, boolean ekspres, String miastoA, String UlicaA, int nrDomA,
			int nrMieA, int kod1A, int kod2A, String miastoN, String UlicaN, int nrDomN, int nrMieN, int kod1N,
			int kod2N) {
		this.waga = waga;
		this.szklo = szklo;
		this.rodzaj = rodzaj;
		this.ekspres = ekspres;
		setDaneAdresata(miastoA, UlicaA, nrDomA, nrMieA, kod1A, kod2A);
		setDaneNadawcy(miastoN, UlicaN, nrDomN, nrMieN, kod1N, kod2N);
	}

	@Override
	public String toString() {
		return "Paczka [waga=" + waga + ", szklo=" + szklo + ", rodzaj=" + rodzaj + ", ekspres=" + ekspres
				+ ", daneAdresata=" + daneAdresata.toString() + ", daneNadawcy=" + daneNadawcy.toString() + "]";
	}

	public double getWaga() {
		return waga;
	}

	public boolean isSzklo() {
		return szklo;
	}

	public String getRodzaj() {
		return rodzaj;
	}

	public boolean isEkspres() {
		return ekspres;
	}

	public Dane getDaneAdresata() {
		return daneAdresata;
	}

	public void setDaneAdresata(String miastoA, String UlicaA, int nrDomA, int nrMieA, int kod1A, int kod2A) {
		this.daneAdresata = new Dane(miastoA, UlicaA, nrDomA, nrMieA, kod1A, kod2A);
	}

	public Dane getDaneNadawcy() {
		return daneNadawcy;
	}

	public void setDaneNadawcy(String miastoN, String UlicaN, int nrDomN, int nrMieN, int kod1N, int kod2N) {
		this.daneNadawcy = new Dane(miastoN, UlicaN, nrDomN, nrMieN, kod1N, kod2N);
	}
}
