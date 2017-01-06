package proba1;

import java.io.Serializable;

public class Paczka implements Serializable {

	private int paczkaID;
	private String stan;
	private float koszt;
	private double waga;
	private boolean szklo;
	private String rodzaj;
	private boolean ekspres;
	private Dane daneAdresata;
	private Dane daneNadawcy;

	public Paczka(int paczkaID, float koszt,double waga, boolean szklo, String rodzaj, boolean ekspres, String miastoA, String UlicaA, int nrDomA,
			int nrMieA, int kod1A, int kod2A, String miastoN, String UlicaN, int nrDomN, int nrMieN, int kod1N,
			int kod2N) {
		setStan("Do Odebrania");
		this.paczkaID = paczkaID;
		this.koszt = koszt;
		this.waga = waga;
		this.szklo = szklo;
		this.rodzaj = rodzaj;
		this.ekspres = ekspres;
		setDaneAdresata(miastoA, UlicaA, nrDomA, nrMieA, kod1A, kod2A);
		setDaneNadawcy(miastoN, UlicaN, nrDomN, nrMieN, kod1N, kod2N);
	}
	

	@Override
	public String toString() {
		return "Numer paczki = " + paczkaID + "\nStan = " + stan + "\nKoszt = " + koszt + "\nWaga = " + waga + "\nSzklo = "
				+ szklo + "\nRodzaj = " + rodzaj + "\nEkspres = " + ekspres + "\n\nDaneAdresata:" + daneAdresata
				+ "\n\nDaneNadawcy:" + daneNadawcy + "";
	}


	public float getKoszt() {
		return koszt;
	}
	
	public String getStan() {
		return stan;
	}

	public void setStan(String stan) {
		this.stan = stan;
	}

	public int getPaczkaID() {
		return paczkaID;
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
