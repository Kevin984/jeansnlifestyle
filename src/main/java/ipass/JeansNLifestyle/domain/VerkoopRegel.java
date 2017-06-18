package ipass.JeansNLifestyle.domain;

public class VerkoopRegel {
private Verkoop verkoop;
private Artikel artikel;
private int aantal;


public VerkoopRegel(Verkoop verkoop, Artikel artikel, int aantal){
	this.verkoop = verkoop;
	this.artikel = artikel;
	this.aantal = aantal;
	
}

public Verkoop getVerkoop(){
	return verkoop;
}

public Artikel getArtikel(){
	return artikel;
}

public void setVerkoop(Verkoop verkoop){
	this.verkoop = verkoop;
}

public void setArtikel(Artikel artikel){
	this.artikel = artikel;
}


public int getAantal(){
	return aantal;
}


public void setAantal(int aantal){
	this.aantal = aantal;
}



}