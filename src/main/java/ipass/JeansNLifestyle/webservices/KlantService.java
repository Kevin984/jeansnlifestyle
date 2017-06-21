package ipass.JeansNLifestyle.webservices;

import java.util.List;

import ipass.JeansNLifestyle.domain.Artikel;
import ipass.JeansNLifestyle.domain.Klant;
import ipass.JeansNLifestyle.persistence.KlantDAO;

public class KlantService {
private KlantDAO klantDAO = new KlantDAO(); //import DAO om alle DAOmethoden aan te roepen met alle onderstaande methoden
	
	public List<Klant> getAllKlanten(){
		return klantDAO.findAllKlanten();
	}
	
	public  Klant getKlantByID(int ID){
		Klant result = null;
		
		for (Klant k : klantDAO.findAllKlanten()){
			if(k.getKlantID() == (ID)){
				result = k;
				break;
			}
		}
		return result;
	}

	public void saveKlant(Klant klant){
		if (klant != null){
			klantDAO.saveKlant(klant);
		}
		else throw new IllegalArgumentException("kan niet opslaan");
	} 
	
	public int getNextKlantID(){
		int ID = klantDAO.getNextKlantID();
		return ID;
	}
	

public void deleteKlant(int ID){
	Klant k = klantDAO.findKlantByID(ID);
	
	if(k != null){
	klantDAO.deleteKlant(k);
	} else throw new IllegalArgumentException("Klant bestaat niet.");
}


}
