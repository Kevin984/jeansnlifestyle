package ipass.JeansNLifestyle.webservices;



import javax.annotation.security.RolesAllowed;
//import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import ipass.JeansNLifestyle.domain.Artikel;
import ipass.JeansNLifestyle.domain.Klant;

@Path("/klanten")
public class KlantenResource {
	
	KlantService klantService = KlantServiceProvider.getKlantService();
	
	@GET
//	@RolesAllowed({"user", "admin"}) << staat tussen comments omdat er geen rechten worden gegeven, zelfs wanneer je inlogt als user/admin. Bij delete en GET werkt dit wel, POST en PUT niet
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
		//maak json van een klant object waar ID gelijk is aan klantID
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
	
	@DELETE
	@RolesAllowed({"user", "admin"})
	@Path("{ID}")
	public Response deleteArtikel(@PathParam("ID") int ID ){
		
		Klant found = null;
		
		for (Klant k : klantService.getAllKlanten()){
			if (k.getKlantID() == ID){
				found = k;
				break;
			}
		}
		
		if(found == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		else {
			klantService.deleteKlant(ID);
			
		return Response.ok().build();
		}
	}
}
