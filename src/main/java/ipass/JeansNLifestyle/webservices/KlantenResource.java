package ipass.JeansNLifestyle.webservices;



//import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import ipass.JeansNLifestyle.domain.Klant;

@Path("/klanten")
public class KlantenResource {
	
	KlantService klantService = KlantServiceProvider.getKlantService();
	
	@GET
//	@RolesAllowed({"user", "admin"})
	@Produces("application/json")
	public String getKlanten() {
	KlantService service = KlantServiceProvider.getKlantService();
	JsonArrayBuilder jab = Json.createArrayBuilder();
	
	for (Klant k : service.getAllKlanten()) {
	JsonObjectBuilder job = Json.createObjectBuilder();
	job.add("ID", k.getKlantID());
	job.add("Naam", k.getNaam());
	job.add("Straat", k.getStraat());
	job.add("Huisnummer", k.getHuisnummer());
	job.add("Postcode", k.getPostcode());
	job.add("Woonplaats", k.getWoonplaats());
	job.add("Email", k.getEmail());
	jab.add(job);
	}
	JsonArray array = jab.build();
	return array.toString();
	}
	
	@GET
//	@RolesAllowed({"user", "admin"})
	@Path("{ID}")
	@Produces("application/json")
	public String getKlantInfo(@PathParam("ID") int ID){
		KlantService service = KlantServiceProvider.getKlantService();
		Klant k = service.getKlantByID(ID);
		
		if(k == null){
			throw new WebApplicationException("Klant bestaat niet!");
		}
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", k.getKlantID());
		job.add("Naam", k.getNaam());
		job.add("Straat", k.getStraat());
		job.add("Huisnummer", k.getHuisnummer());
		job.add("Postcode", k.getPostcode());
		job.add("Woonplaats", k.getWoonplaats());
		job.add("Email", k.getEmail());
		return job.build().toString();
	} 
	
	@GET
//	@RolesAllowed({"user", "admin"})
	@Path("nextval")
	@Produces("application/json")
	public String getNextKlantID(){
		KlantService service = KlantServiceProvider.getKlantService();
		int ID2 = service.getNextKlantID();
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("nextklantID", ID2);
		return job.build().toString();
	}
	
	@POST
//	@RolesAllowed({"user", "admin"})
	@Produces("application/json")
	public String createKlant(
			@FormParam("ID") int ID, 
			@FormParam("Naam") String naam, 
			@FormParam("Straat") String straat,
			@FormParam("Huisnummer") String huisnr, 
			@FormParam("Postcode") String postcode, 
			@FormParam("Woonplaats") String woonplaats, 
			@FormParam("Email") String email){		
		
		Klant newKlant = new Klant(ID, naam, straat, huisnr, postcode, woonplaats, email);
		klantService.saveKlant(newKlant);
		return klantToJson(newKlant).build().toString();
	}
	
	private JsonObjectBuilder klantToJson(Klant k){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", k.getKlantID());
		job.add("Naam", k.getNaam());
		job.add("Straat", k.getStraat());
		job.add("Huisnummer", k.getHuisnummer());
		job.add("Postcode", k.getPostcode());
		job.add("Woonplaats", k.getWoonplaats());
		job.add("Email", k.getEmail());
		
		return job;
	}
	/*
	@GET
//	@RolesAllowed({"user", "admin"})
	@Path("/aankopen")
	@Produces("application/json")
	public String klantAankopen(@QueryParam("ID") int ID){
		JsonObjectBuilder job = Json.createObjectBuilder();
		Klant klant = klantDAO.findKlantByID(ID);
		job.add("ID", ID);
		job.add("Naam", klant.getNaam());
		job.add("Aankopen", (JsonValue) klant.getAankopen());
		// art nr, naam, cat, maat, kleur, merk, aantal gekocht, verkoop prijs per stuk, totaalprijs
		
		return job.build().toString();
	}*/
}
