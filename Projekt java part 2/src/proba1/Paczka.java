/*package proba1;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Paczka implements Serializable {

	private int paczkaID;
	private String stan;
	private float koszt;
	private double waga;
	private boolean szklo;
	private String rodzaj;
	private boolean ekspres;
	private Adresat adresat;
	private Nadawca nadawca;
	private Date data;

	public Paczka(int paczkaID, float koszt, double waga, boolean szklo, String rodzaj, boolean ekspres, String miastoA,
			String UlicaA, int nrDomA, int nrMieA, String kodA, String imieA, String nazwiskoA, String miastoN,
			String UlicaN, int nrDomN, int nrMieN, String kodN, String imieN, String nazwiskoN, Date data) {
		setStan("Do Odebrania");
		this.paczkaID = paczkaID;
		this.koszt = koszt;
		this.waga = waga;
		this.szklo = szklo;
		this.rodzaj = rodzaj;
		this.ekspres = ekspres;
		this.data = data;
		setAdresat(miastoA, UlicaA, nrDomA, nrMieA, kodA, imieA, nazwiskoA);
		setNadawca(miastoN, UlicaN, nrDomN, nrMieN, kodN, imieN, nazwiskoN);
	}

	@Override
	public String toString() {
		return "Numer paczki = " + paczkaID + "\nStan = " + stan + "\nKoszt = " + koszt + "\nWaga = " + waga
				+ "\nSzklo = " + szklo + "\nRodzaj = " + rodzaj + "\nEkspres = " + ekspres
				+ "\nData przyjazdu kuriera = " + getDataToString(data) + "\n\nDaneAdresata:" + adresat
				+ "\n\nDaneNadawcy:" + nadawca + "";
	}

	public Date getData() {
		return data;
	}

	public String getDataToString(Date data) {
		String dataToString = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dataToString = dateFormat.format(data);
		return dataToString;
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

	public Adresat getAdresat() {
		return adresat;
	}

	public void setAdresat(String miastoA, String UlicaA, int nrDomA, int nrMieA, String kodA, String imieA,
			String nazwiskoA) {
		this.adresat = new Adresat(miastoA, UlicaA, nrDomA, nrMieA, kodA, imieA, nazwiskoA);
	}

	public Nadawca getNadawca() {
		return nadawca;
	}

	public void setNadawca(String miastoN, String UlicaN, int nrDomN, int nrMieN, String kodN, String imieN,
			String nazwiskoN) {
		this.nadawca = new Nadawca(miastoN, UlicaN, nrDomN, nrMieN, kodN, imieN, nazwiskoN);
	}
}*/
