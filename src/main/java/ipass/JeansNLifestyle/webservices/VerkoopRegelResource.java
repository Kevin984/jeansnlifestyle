package ipass.JeansNLifestyle.webservices;

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

import ipass.JeansNLifestyle.domain.Artikel;
import ipass.JeansNLifestyle.domain.Verkoop;
import ipass.JeansNLifestyle.domain.VerkoopRegel;

@Path("verkoopregels")
public class VerkoopRegelResource {
	VerkoopRegelService verkoopregelService = VerkoopRegelServiceProvider.getVerkoopRegelService();
	
	
	@GET
	@Produces("application/json")
	public String getVerkoopRegels(){
		VerkoopRegelService service = VerkoopRegelServiceProvider.getVerkoopRegelService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for(VerkoopRegel v  : service.getVerkoopRegels()){
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("VerkoopID", v.getVerkoop().getVerkoopID());
			job.add("ArtikelID", v.getArtikel().getArtikelID());
			job.add("ArtikelMaat", v.getArtikel().getMaat());
			job.add("ArtikelKleur", v.getArtikel().getKleur());
			job.add("Aantal", v.getAantal());
			//maak van alle gegevens van elke verkoopregel JSON
			jab.add(job); 
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	
	@GET
//	@RolesAllowed({"user", "admin"}) << staat tussen comments omdat er geen rechten worden gegeven, zelfs wanneer je inlogt als user/admin. Bij delete en GET werkt dit wel, POST en PUT niet
	@Path("{ID}/{ArtikelID}/{ArtikelMaat}/{ArtikelKleur}")
	@Produces("application/json")
	public String getVerkoopRegelInfo(@PathParam("ID") int ID, @PathParam("ArtikelID") int artikelID, @PathParam("ArtikelMaat")String artikelMaat, @PathParam("ArtikelKleur") String artikelKleur){
		
		VerkoopRegelService service = VerkoopRegelServiceProvider.getVerkoopRegelService();
		VerkoopRegel v = service.getVerkoopRegelByPK(ID, artikelID, artikelMaat, artikelKleur);
		
		if(v == null){
			throw new WebApplicationException("Verkoopregel bestaat niet!");
		}
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("VerkoopID", v.getVerkoop().getVerkoopID());
		job.add("ArtikelID", v.getArtikel().getArtikelID());
		job.add("ArtikelMaat", v.getArtikel().getMaat());
		job.add("ArtikelKleur", v.getArtikel().getKleur());
		job.add("Aantal", v.getAantal());
		return job.build().toString();
	} 
	
	
	@POST
//	@RolesAllowed({"user", "admin"})
	@Produces("application/json")
	public String createVerkoopRegel(@FormParam("VerkoopID")int ID, @FormParam("ArtikelID") int artikelID, @FormParam("ArtikelMaat") String artikelMaat, @FormParam("ArtikelKleur") String artikelKleur, @FormParam("Aantal") int aant){
		VerkoopService verkoopService = new VerkoopService();
		ArtikelService artikelService = new ArtikelService();
		
		Verkoop newVerkoop = verkoopService.getVerkoopByID(ID);
		Artikel newArtikel = artikelService.getArtikelByPK(artikelID, artikelMaat, artikelKleur);
		int artikelAantal = newArtikel.getAantal();
		int newArtikelAantal = artikelAantal - aant;
		//verlaag het aantal van een artikel met het aantal dat geselecteerd wordt bij de verkoop
		newArtikel.setAantal(newArtikelAantal); 
		artikelService.updateArtikel(newArtikel); 
		
		
		VerkoopRegel newVerkoopRegel = new VerkoopRegel(newVerkoop, newArtikel, aant);
		verkoopregelService.saveVerkoopRegel(newVerkoopRegel);
		return verkoopregelToJson(newVerkoopRegel).build().toString();
	}
	
	private JsonObjectBuilder verkoopregelToJson(VerkoopRegel v ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("VerkoopID", v.getVerkoop().getVerkoopID());
		job.add("ArtikelID", v.getArtikel().getArtikelID());
		job.add("ArtikelMaat", v.getArtikel().getMaat());
		job.add("ArtikelKleur", v.getArtikel().getKleur());
		job.add("Aantal", v.getAantal());
		return job;
	}
	
	
	
	@Path("/completeverkopen")
	@GET
	//@RolesAllowed({"user", "admin"})
	@Produces("application/json")
	public String getCompleteVerkopen(){
		VerkoopRegelService service = VerkoopRegelServiceProvider.getVerkoopRegelService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		//maak json van complete verkoop (dus incl verkoopregels en klant naam etc) ipv alleen verkoopID, datum en klantID)
		for(VerkoopRegel v : service.getCompleteVerkopen()){
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("VerkoopID", v.getVerkoop().getVerkoopID());
			job.add("Datum", v.getVerkoop().getVerkoopDatum());
			job.add("KlantID", v.getVerkoop().getKlant().getKlantID());
			job.add("KlantNaam", v.getVerkoop().getKlant().getNaam());
			job.add("ArtikelID", v.getArtikel().getArtikelID());
			job.add("ArtikelNaam", v.getArtikel().getNaam());
			job.add("ArtikelMaat", v.getArtikel().getMaat());
			job.add("ArtikelKleur", v.getArtikel().getKleur());
			job.add("ArtikelMerk", v.getArtikel().getMerk());
			job.add("ArtikelVerkoopprijs", v.getArtikel().getVerkoopprijs());
			job.add("Aantal", v.getAantal());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	
	
	

}
