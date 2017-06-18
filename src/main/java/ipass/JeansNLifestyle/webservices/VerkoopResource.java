package ipass.JeansNLifestyle.webservices;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import ipass.JeansNLifestyle.domain.Klant;
import ipass.JeansNLifestyle.domain.Verkoop;
import ipass.JeansNLifestyle.domain.VerkoopCompleet;
import ipass.JeansNLifestyle.persistence.KlantDAO;

@Path("/verkopen")
public class VerkoopResource {
//	private KlantDAO klantDAO = new KlantDAO();
	private KlantService klantService = new KlantService();
	VerkoopService verkoopService = VerkoopServiceProvider.getVerkoopService();
	
	@GET
	//@RolesAllowed({"user", "admin"})
	@Produces("application/json")
	public String getVerkopen(){
		VerkoopService service = VerkoopServiceProvider.getVerkoopService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for(Verkoop v : service.getVerkopen()){
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("VerkoopID", v.getVerkoopID());
			job.add("Datum", v.getVerkoopDatum());
			job.add("KlantID", v.getKlant().getKlantID());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	
	@GET
//	@RolesAllowed({"user", "admin"})
	@Path("{ID}")
	@Produces("application/json")
	public String getVerkoopInfo(@PathParam("ID") int verkoopID){
		VerkoopService service = VerkoopServiceProvider.getVerkoopService();
		Verkoop v = service.getVerkoopByID(verkoopID);
		
		if(v == null){
			throw new WebApplicationException("Verkoop bestaat niet!");
		}
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("VerkoopID", v.getVerkoopID());
		job.add("Datum", v.getVerkoopDatum());
		job.add("KlantID", v.getKlant().getKlantID());
		return job.build().toString();
	} 
	
	
	@POST
//	@RolesAllowed({"user", "admin"})
	@Produces("application/json")
//	@Consumes()
	public String createVerkoop(@FormParam("verkoopID")int verkoopID, @FormParam("datumVandaag") String datum, @FormParam("klantID") int klantID){
		Verkoop newVerkoop = new Verkoop(verkoopID, datum);
		Klant k =  klantService.getKlantByID(klantID);
    	newVerkoop.setKlant(k);	
		verkoopService.saveVerkoop(newVerkoop);
		return verkoopToJson(newVerkoop).build().toString();
	}	
	
	private JsonObjectBuilder verkoopToJson(Verkoop v){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("VerkoopID", v.getVerkoopID());
		job.add("Datum", v.getVerkoopDatum());
		job.add("KlantID", v.getKlant().getKlantID());
		return job;
	}
	
	@GET
//	@RolesAllowed({"user", "admin"})
	@Path("nextval")
	@Produces("application/json")
	public String getNextKlantID(){
		VerkoopService service = VerkoopServiceProvider.getVerkoopService();
		int ID2 = service.getNextVerkooptID();
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("nextverkoopID", ID2);
		return job.build().toString();
	}

}
