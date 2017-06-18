package ipass.JeansNLifestyle.webservices;

public class VerkoopServiceProvider {
	private static VerkoopService verkoopService = new VerkoopService();

	public static VerkoopService getVerkoopService(){
		return verkoopService;
	}

}
