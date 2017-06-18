package ipass.JeansNLifestyle.webservices;

import java.util.List;

import ipass.JeansNLifestyle.domain.Artikel;
import ipass.JeansNLifestyle.persistence.ArtikelDAO;

public class ArtikelService {
private ArtikelDAO artikelDAO = new ArtikelDAO();

public List<Artikel> getVoorraad(){
	return artikelDAO.findAll();
}

public Artikel getArtikelByPK(int ID, String maat, String kleur){
	Artikel result = null;
	
	for (Artikel a : artikelDAO.findAll()){
		if(a.getArtikelID() == (ID)  && a.getMaat().equals(maat) && a.getKleur().equals(kleur) ){
			result = a;
			break;
		}
	}
	return result;
}

public void saveArtikel(Artikel artikel){
	if (artikel != null){
		artikelDAO.saveArtikel(artikel);
	}
	else throw new IllegalArgumentException("kan niet opslaan");
} 


public void deleteArtikel(int ID, String maat, String kleur){
	Artikel a  = artikelDAO.findByPK(ID, maat, kleur);
	
	if(a != null){
	artikelDAO.deleteArtikel(a);
	} else throw new IllegalArgumentException("ID, maat en/of kleur bestaat niet.");
}

public void updateArtikel(Artikel artikel){
	if (artikel != null){
		artikelDAO.updateArtikel(artikel);
	}
	
	else throw new IllegalArgumentException("kan niet updaten");
	
}


}
