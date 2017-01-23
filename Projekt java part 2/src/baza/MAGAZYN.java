package baza;

public class MAGAZYN {
	private int idMagazynu;
	private String miasto;
	private int nrBudynku;
	private String ulica;
	private int nrLokalu;

	public MAGAZYN(int idMagazynu, String miasto, int nrBudynku, String ulica, int nrLokalu) {
		this.idMagazynu = idMagazynu;
		this.miasto = miasto;
		this.nrBudynku = nrBudynku;
		this.ulica = ulica;
		this.nrLokalu = nrLokalu;
	}

	public int getIdMagazynu() {
		return idMagazynu;
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
