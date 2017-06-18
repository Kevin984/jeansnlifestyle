package ipass.JeansNLifestyle.webservices;

public class ArtikelServiceProvider {
private static ArtikelService voorraadService = new ArtikelService();

public static ArtikelService getVoorraadService(){
	return voorraadService;
}
}
