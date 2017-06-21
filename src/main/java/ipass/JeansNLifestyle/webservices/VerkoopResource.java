package ipass.JeansNLifestyle.webservices;

import javax.annotation.security.RolesAllowed;
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
import javax.ws.rs.WebApplicationException;
import ipass.JeansNLifestyle.domain.Klant;
import ipass.JeansNLifestyle.domain.Verkoop;

@Path("/verkopen")
public class VerkoopResource {
	private KlantService klantService = new KlantService();
	VerkoopService verkoopService = VerkoopServiceProvider.getVerkoopService();
	
	@GET
	//@RolesAllowed({"user", "admin"}) << staat tussen comments omdat er geen rechten worden gegeven, zelfs wanneer je inlogt als user/admin. Bij delete en GET werkt dit wel, POST en PUT niet
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
		//maak van verkoop klasse, json
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
	//maak een verkoop met gegevens uit de HTML form
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
	public String getNextVerkoopID(){
		VerkoopService service = VerkoopServiceProvider.getVerkoopService();
		int ID2 = service.getNextVerkooptID();
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("nextverkoopID", ID2);
		//gebruik service en dao om eerstvolgend beschikbare verkoopID te vinden en json ervan te maken
		return job.build().toString(); 
	}

}
