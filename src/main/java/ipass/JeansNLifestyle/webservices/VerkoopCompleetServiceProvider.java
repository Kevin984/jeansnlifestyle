package ipass.JeansNLifestyle.webservices;

public class VerkoopCompleetServiceProvider {
	private static VerkoopCompleetService verkoopcompleetService = new VerkoopCompleetService();

	public static VerkoopCompleetService getVerkoopCompleetService(){
		return verkoopcompleetService;
	}


}
