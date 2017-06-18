package ipass.JeansNLifestyle.domain;

public class VerkoopCompleet {
	private Verkoop verkoop;
	private VerkoopRegel verkoopregel;
	private Artikel artikel;
	
	
	public VerkoopCompleet(Verkoop verkoop, VerkoopRegel verkoopregel){
		this.verkoop = verkoop;
		this.verkoopregel = verkoopregel;
	}
	
	public Artikel getArtikel(){
		return artikel;
	}
	
	public void setArtikel(Artikel artikel){
		this.artikel = artikel;
	}
	
	public Verkoop getVerkoop(){
		return verkoop;
	}
	
	public VerkoopRegel getVerkoopRegel(){
		return verkoopregel;
	}
	
	public void setVerkoop(Verkoop verkoop){
		this.verkoop = verkoop;
	}
	
	public void setVerkoopRegel(VerkoopRegel verkoopregel){
		this.verkoopregel = verkoopregel;
	}
}
