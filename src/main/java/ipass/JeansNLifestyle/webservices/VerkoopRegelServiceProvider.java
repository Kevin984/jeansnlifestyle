package ipass.JeansNLifestyle.webservices;

public class VerkoopRegelServiceProvider {
	private static VerkoopRegelService verkoopregelService = new VerkoopRegelService();
	public static VerkoopRegelService getVerkoopRegelService(){
		return verkoopregelService;
	}
}
