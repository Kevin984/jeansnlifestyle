package ipass.JeansNLifestyle.domain;

public class Klant {
	private int klantID;
	private String naam;
	private String straat;
	private String huisnummer;
	private String postcode;
	private String woonplaats;
	private String email;
	
	
	public Klant(int ID, String naam, String straat, String huisnummer, String postcode, String woonplaats, String email){
		this.klantID = ID;
		this.naam = naam;
		this.straat = straat;
		this.huisnummer = huisnummer;
		this.postcode = postcode;
		this.woonplaats = woonplaats;
		this.email = email;
		}
	
	public int getKlantID(){
		return klantID;
	}
	
	public void setKlantID(int ID){
		this.klantID = ID;
	}
	
	public void setNaam(String naam){
		this.naam = naam;
	}
	
	public void setStraat(String straat){
		this.straat = straat;
	}
	
	public void setHuisnummer(String huisnummer){
		this.huisnummer = huisnummer;
	}
	
	public void setPostcode(String postcode){
		this.postcode = postcode;
	}
	
	public void setWoonplaats(String woonplaats){
		this.woonplaats = woonplaats;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getNaam(){
		return naam;
	}
	
	public String getStraat(){
		return straat;
	}
	
	public String getHuisnummer(){
		return huisnummer;
	}
	
	public String getPostcode(){
		return postcode;
	}
	
	public String getWoonplaats(){
		return woonplaats;
	}
	
	public String getEmail(){
		return email;
	}
	
	
}
