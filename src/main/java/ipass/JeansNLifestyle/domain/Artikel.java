package ipass.JeansNLifestyle.domain;

public class Artikel {
	private int artikelID;
	private String naam;
	private String categorie;
	private String maat;
	private String kleur;
	private String merk;
	private double inkoopprijs;
	private double verkoopprijs;
	private int aantal;
	
	public Artikel(int artNr, String artNm, String cat, String mt, String klr, String mrk, double kostpr, double verkooppr, int aant){
		artikelID = artNr;
		naam = artNm;
		categorie = cat;
		maat = mt;
		kleur = klr;
		merk = mrk;
		inkoopprijs = kostpr;
		verkoopprijs = verkooppr;
		aantal = aant;
	}
	
	
	public int getArtikelID(){
		return artikelID;
	}
	
	public String getNaam(){
		return naam;
	}
	
	public String getCategorie(){
		return categorie;
	}
	
	public String getMaat(){
		return maat;
	}
	
	public String getKleur(){
		return kleur;
	}
	
	public String getMerk(){
		return merk;
	}
	
	public double getInkoopprijs(){
		return inkoopprijs;
	}
	
	public double getVerkoopprijs(){
		return verkoopprijs;
	}
	
	public void setArtikelID(int artID){
		artikelID = artID;
	}
	
	public void setArtikelNaam(String artNaam){
		naam = artNaam;	
	}
	
	public void setArtikelCategorie(String cat){
		categorie = cat;
	}
	
	public void setArtikelMaat(String artMaat){
		maat = artMaat;
	}
	
	public void setArtikelKleur(String artKleur){
		kleur = artKleur;
	}
	
	public void setArtikelMerk(String artMerk){
		merk = artMerk;
	}
	
	public void setInkoopprijs(double artKostpr){
		inkoopprijs = artKostpr;
	}
	
	public void setVerkoopprijs(double artVerkooppr){
		verkoopprijs = artVerkooppr;
	}
	
  public int getAantal(){
	  return aantal;
  }
  
  public void setAantal(int aantal){
	  this.aantal = aantal;
  }
}
