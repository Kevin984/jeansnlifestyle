package ipass.JeansNLifestyle.webservices;


import java.util.List;
import ipass.JeansNLifestyle.domain.Verkoop;

import ipass.JeansNLifestyle.persistence.VerkoopDAO;

public class VerkoopService {
	//import DAO om alle DAOmethoden aan te roepen met alle onderstaande methoden
private VerkoopDAO verkoopDAO = new VerkoopDAO(); 
	
public List<Verkoop> getVerkopen(){
	return verkoopDAO.findAllVerkopen();
}


	public int getNextVerkooptID(){
		int ID = verkoopDAO.getNextVerkoopID();
		return ID;
	}
	
	public  Verkoop getVerkoopByID(int ID){
		Verkoop result = null;
		
		for (Verkoop v : verkoopDAO.findAllVerkopen()){
			if(v.getVerkoopID() == (ID)){
				result = v;
				break;
			}
		}
		return result;
	}
	
	
	public void saveVerkoop(Verkoop verkoop){
		if (verkoop != null){
			verkoopDAO.saveVerkoop(verkoop);
		}
		else throw new IllegalArgumentException("kan niet opslaan");
	} 
	
	
	
	
}
