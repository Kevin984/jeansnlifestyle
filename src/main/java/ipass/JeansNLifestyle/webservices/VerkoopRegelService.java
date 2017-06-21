package ipass.JeansNLifestyle.webservices;

import java.util.List;


import ipass.JeansNLifestyle.domain.VerkoopRegel;
import ipass.JeansNLifestyle.persistence.VerkoopRegelDAO;

public class VerkoopRegelService {
private VerkoopRegelDAO verkoopregelDAO = new VerkoopRegelDAO(); //import DAO om alle DAOmethoden aan te roepen met alle onderstaande methoden

public List<VerkoopRegel> getVerkoopRegels(){
	return verkoopregelDAO.findAllVerkoopRegels();
}

public List<VerkoopRegel> getCompleteVerkopen(){
	return verkoopregelDAO.findAllCompleteVerkopen();
}

public VerkoopRegel getVerkoopRegelByPK(int ID, int artikelID, String artikelMaat, String artikelKleur){
	VerkoopRegel result = null;	
	for (VerkoopRegel v : verkoopregelDAO.findAllVerkoopRegels()){
		if( v.getVerkoop().getVerkoopID()== ID && v.getArtikel().getArtikelID() == artikelID && v.getArtikel().getMaat().equals(artikelMaat)  &&  v.getArtikel().getKleur().equals(artikelKleur)){
			result = v;
			break;
		}
	}
	return result;
}

public void saveVerkoopRegel(VerkoopRegel verkoopregel){
	if (verkoopregel != null){
     //		int artikelAantalInVoorraad = verkoopregel.getArtikel().getAantal();
	//		verkoopregel.getArtikel().setAantal(artikelAantalInVoorraad - verkoopregel.getAantal());
				
		verkoopregelDAO.saveVerkoopRegel(verkoopregel);
	}
	else throw new IllegalArgumentException("kan niet opslaan");
} 
}
