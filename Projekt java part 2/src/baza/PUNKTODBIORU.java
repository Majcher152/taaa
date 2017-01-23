package baza;

public class PUNKTODBIORU {
	private int idPunktu;
	private int idKuriera;
	private String godzinyOtwarcia;
	private String miasto;
	private int nrBudynku;
	private String ulica;
	private int nrLokalu;

	public PUNKTODBIORU(int idPunktu, int idKuriera, String godzinyOtwarcia, String miasto, int nrBudynku, String ulica,
			int nrLokalu) {
		this.idPunktu = idPunktu;
		this.idKuriera = idKuriera;
		this.godzinyOtwarcia = godzinyOtwarcia;
		this.miasto = miasto;
		this.nrBudynku = nrBudynku;
		this.ulica = ulica;
		this.nrLokalu = nrLokalu;
	}

	public int getIdPunktu() {
		return idPunktu;
	}

	public int getIdKuriera() {
		return idKuriera;
	}

	public String getGodzinyOtwarcia() {
		return godzinyOtwarcia;
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
