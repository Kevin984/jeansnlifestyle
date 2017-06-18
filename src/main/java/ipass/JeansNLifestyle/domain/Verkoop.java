package ipass.JeansNLifestyle.domain;

public class Verkoop {
	private int ID;
	private String datum;
	private Klant klant;
	
	public Verkoop(int ID, String datum){
		this.ID = ID;
		this.datum = datum;
	}
	
	public int getVerkoopID(){
		return ID;
	}
	
	public String getVerkoopDatum(){
		return datum;
	}
	
	public Klant getKlant(){
		return klant;
	}
	
public void setVerkoopID(int ID){
	this.ID = ID;
}
	public void setVerkoopDatum(String datum){
		this.datum = datum;
	}
	
	public void setKlant(Klant klant){
		this.klant = klant;
	}
}
