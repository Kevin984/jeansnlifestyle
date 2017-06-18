package ipass.JeansNLifestyle.webservices;

public class KlantServiceProvider {
	private static KlantService klantService = new KlantService();
	public static KlantService getKlantService() {
	return klantService;
	}

}
