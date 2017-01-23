package baza;

public class KURIER {
	private int idKuriera;
	private int  idMagazynu;     
	private String  imie;           
	private String  nazwisko;       
	private String  email;          
	private String  haslo;
	
	
	public KURIER(int idKuriera, int idMagazynu, String imie, String nazwisko, String email, String haslo) {
		this.idKuriera = idKuriera;
		this.idMagazynu = idMagazynu;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.email = email;
		this.haslo = haslo;
	}
	
	public int getIdKuriera() {
		return idKuriera;
	}
	public int getIdMagazynu() {
		return idMagazynu;
	}
	public String getImie() {
		return imie;
	}
	public String getNazwisko() {
		return nazwisko;
	}
	public String getEmail() {
		return email;
	}
	public String getHaslo() {
		return haslo;
	}           
}
