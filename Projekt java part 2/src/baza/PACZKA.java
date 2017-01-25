package baza;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PACZKA implements Serializable {

	private int idPaczki;
	private String stan;
	private float koszt;
	private double waga;
	private boolean delikatna;
	private String rodzaj;
	private boolean ekspres;
	private ODBIORCA odbiorca;
	private NADAWCA nadawca;
	private Date dataPrzyjecia;
	private int idOdbiorcy;
	private int idNadawcy;
	//
	private int idPunktu;
	private int idKuriera;
	private int idMagazynu;
	private Date dataDostarczenia;

	public PACZKA(int idPaczki, String stan, float koszt, double waga, boolean delikatna, String rodzaj, boolean ekspres,
			String miastoA, String UlicaA, int nrDomA, int nrMieA, String kodA, String imieA, String nazwiskoA,
			String miastoN, String UlicaN, int nrDomN, int nrMieN, String kodN, String imieN, String nazwiskoN,
			Date dataPrzyjecia, int idOdbiorcy, int idNadawcy, Date dataDostarczenia) {
		if (stan == "")
			setStan("Do odebrania");
		else
			this.stan = stan;

		this.idPaczki = idPaczki;
		this.koszt = koszt;
		this.waga = waga;
		this.delikatna = delikatna;
		this.rodzaj = rodzaj;
		this.ekspres = ekspres;
		this.dataPrzyjecia = dataPrzyjecia;
		this.dataDostarczenia = dataDostarczenia;
		this.idNadawcy = idNadawcy;
		this.idOdbiorcy = idOdbiorcy;
		setAdresat(idOdbiorcy, miastoA, UlicaA, nrDomA, nrMieA, kodA, imieA, nazwiskoA);
		setNadawca(idNadawcy, miastoN, UlicaN, nrDomN, nrMieN, kodN, imieN, nazwiskoN);
	}

	@Override
	public String toString() {
		return "Numer paczki = " + idPaczki + "\nStan = " + stan + "\nKoszt = " + koszt + "\nWaga = " + waga
				+ "\nSzklo = " + delikatna + "\nRodzaj = " + rodzaj + "\nEkspres = " + ekspres
				+ "\nData przyjazdu kuriera = " + getDataToString(dataPrzyjecia) +"\nPaczka zostanie dostarczona = "+ getDataToString(dataDostarczenia) +"\n\nDaneAdresata:" + odbiorca
				+ "\n\nDaneNadawcy:" + nadawca + "";
	}

	public Date getDataDostarczenia() {
		return dataDostarczenia;
	}

	public Date getDataPrzyjecia() {
		return dataPrzyjecia;
	}

	public String getDataToString(Date data) {
		String dataToString = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dataToString = dateFormat.format(data);
		return dataToString;
	}

	public int getIdOdbiorcy() {
		return idOdbiorcy;
	}

	public int getIdNadawcy() {
		return idNadawcy;
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

	public int getidPaczki() {
		return idPaczki;
	}

	public double getWaga() {
		return waga;
	}

	public boolean isDelikatna() {
		return delikatna;
	}

	public String getRodzaj() {
		return rodzaj;
	}

	public boolean isEkspres() {
		return ekspres;
	}

	public ODBIORCA getAdresat() {
		return odbiorca;
	}

	public void setAdresat(int idOdbiorcy, String miastoA, String UlicaA, int nrDomA, int nrMieA, String kodA,
			String imieA, String nazwiskoA) {
		// 1 -> idOdbiorcy
		this.odbiorca = new ODBIORCA(idOdbiorcy, imieA, nazwiskoA, miastoA, nrDomA, UlicaA, nrMieA, kodA);
	}

	public NADAWCA getNadawca() {
		return nadawca;
	}

	public void setNadawca(int idNadawcy, String miastoN, String UlicaN, int nrDomN, int nrMieN, String kodN,
			String imieN, String nazwiskoN) {
		this.nadawca = new NADAWCA(idNadawcy, imieN, nazwiskoN, miastoN, nrDomN, UlicaN, nrMieN, kodN);
	}
}

/*
 * public class PACZKA { private int idPaczki; private int idPunktu; private int
 * idKuriera; private int idNadawcy; private int idOdbiorcy; private int
 * idMagazynu; private String dataPrzyjecia; private String dataDostarczenia;
 * private String stan; private float koszt; private float waga; private boolean
 * ekspres; private boolean delikatna;
 * 
 * public PACZKA(int idPaczki, int idPunktu, int idKuriera, int idNadawcy, int
 * idOdbiorcy, int idMagazynu, String dataPrzyjecia, String dataDostarczenia,
 * String stan, float koszt, float waga, boolean ekspres, boolean delikatna) {
 * this.idPaczki = idPaczki; this.idPunktu = idPunktu; this.idKuriera =
 * idKuriera; this.idNadawcy = idNadawcy; this.idOdbiorcy = idOdbiorcy;
 * this.idMagazynu = idMagazynu; this.dataPrzyjecia = dataPrzyjecia;
 * this.dataDostarczenia = dataDostarczenia; this.stan = stan; this.koszt =
 * koszt; this.waga = waga; this.ekspres = ekspres; this.delikatna = delikatna;
 * }
 * 
 * public int getIdPaczki() { return idPaczki; }
 * 
 * public int getIdPunktu() { return idPunktu; }
 * 
 * public int getIdKuriera() { return idKuriera; }
 * 
 * public int getIdNadawcy() { return idNadawcy; }
 * 
 * public int getIdOdbiorcy() { return idOdbiorcy; }
 * 
 * public int getIdMagazynu() { return idMagazynu; }
 * 
 * public String getDataPrzyjecia() { return dataPrzyjecia; }
 * 
 * public String getDataDostarczenia() { return dataDostarczenia; }
 * 
 * public String getStan() { return stan; }
 * 
 * public float getKoszt() { return koszt; }
 * 
 * public float getWaga() { return waga; }
 * 
 * public boolean isEkspres() { return ekspres; }
 * 
 * public boolean isDelikatna() { return delikatna; } }
 */
