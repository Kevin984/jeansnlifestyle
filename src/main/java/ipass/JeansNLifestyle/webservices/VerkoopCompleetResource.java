package ipass.JeansNLifestyle.webservices;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import ipass.JeansNLifestyle.domain.VerkoopCompleet;



@Path("/completeverkopen")
public class VerkoopCompleetResource {

	@GET
	//@RolesAllowed({"user", "admin"})
	@Produces("application/json")
	public String getCompleteVerkopen(){
		VerkoopCompleetService service = VerkoopCompleetServiceProvider.getVerkoopCompleetService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		//verkid datum klantid kaltnnaam artikelid artikelnaam artikelmaat artikelkleur artikelmerk verkoopprijs aantal
		for(VerkoopCompleet v : service.getCompleteVerkopen()){
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("VerkoopID", v.getVerkoop().getVerkoopID());
			job.add("Datum", v.getVerkoop().getVerkoopDatum());
			job.add("KlantID", v.getVerkoop().getKlant().getKlantID());
			job.add("KlantNaam", v.getVerkoop().getKlant().getNaam());
			job.add("ArtikelID", v.getVerkoopRegel().getArtikel().getArtikelID());
			job.add("ArtikelNaam", v.getArtikel().getNaam());
			job.add("ArtikelMaat", v.getVerkoopRegel().getArtikel().getMaat());
			job.add("ArtikelKleur", v.getVerkoopRegel().getArtikel().getKleur());
			job.add("ArtikelMerk", v.getArtikel().getMerk());
			job.add("ArtikelVerkoopprijs", v.getArtikel().getVerkoopprijs());
			job.add("Aantal", v.getVerkoopRegel().getAantal());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}

}
