package ipass.JeansNLifestyle.webservices;

import java.util.List;

import ipass.JeansNLifestyle.domain.VerkoopCompleet;
import ipass.JeansNLifestyle.persistence.VerkoopCompleetDAO;

public class VerkoopCompleetService {
	private VerkoopCompleetDAO verkoopcompleetDAO = new VerkoopCompleetDAO();

	
	public List<VerkoopCompleet> getCompleteVerkopen(){
		return verkoopcompleetDAO.findAllCompleteVerkopen();
	}
}
