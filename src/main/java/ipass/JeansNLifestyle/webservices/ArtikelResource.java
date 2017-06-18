package ipass.JeansNLifestyle.webservices;
import java.io.IOException;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import ipass.JeansNLifestyle.webservices.ArtikelService;
import ipass.JeansNLifestyle.webservices.ArtikelServiceProvider;
import ipass.JeansNLifestyle.domain.Artikel;

@Path("/voorraad")
public class ArtikelResource {
	ArtikelService artikelService = ArtikelServiceProvider.getVoorraadService();
	
	@GET
	//@RolesAllowed({"user", "admin"})
	@Produces("application/json")
	public String getVoorraad(){
		ArtikelService service = ArtikelServiceProvider.getVoorraadService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for(Artikel a : service.getVoorraad()){
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", a.getArtikelID());
			job.add("Naam", a.getNaam());
			job.add("Categorie", a.getCategorie());
			job.add("Maat", a.getMaat());
			job.add("Kleur", a.getKleur());
			job.add("Merk", a.getMerk());
			job.add("Inkoopprijs", a.getInkoopprijs());
			job.add("Verkoopprijs", a.getVerkoopprijs());
			job.add("Aantal", a.getAantal());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	
	@GET
//	@RolesAllowed({"user", "admin"})
	@Path("{ID}/{Maat}/{Kleur}")
	@Produces("application/json")
	public String getArtikelInfo(@PathParam("ID") int ID, @PathParam("Maat")String maat, @PathParam("Kleur") String kleur){
		ArtikelService service = ArtikelServiceProvider.getVoorraadService();
		Artikel a = service.getArtikelByPK(ID, maat, kleur);
		
		if(a == null){
			throw new WebApplicationException("Artikel bestaat niet!");
		}
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", a.getArtikelID());
		job.add("Naam", a.getNaam());
		job.add("Categorie", a.getCategorie());
		job.add("Maat", a.getMaat());
		job.add("Kleur", a.getKleur());
		job.add("Merk", a.getMerk());
		job.add("Inkoopprijs", a.getInkoopprijs());
		job.add("Verkoopprijs", a.getVerkoopprijs());
		job.add("Aantal", a.getAantal());
		return job.build().toString();
	} 
	
	@POST
//	@RolesAllowed({"user", "admin"})
	@Produces("application/json")
	//@Consumes("application/x-www-form-urlencoded")
	public String createArtikel(@FormParam("ID")int ID, @FormParam("Naam") String nm, @FormParam("Categorie") String cat, @FormParam("Maat") String maat, @FormParam("Kleur") String kleur, @FormParam("Merk")String merk ,@FormParam("Inkoopprijs") double ink, @FormParam("Verkoopprijs") double verk, @FormParam("Aantal") int aant){
		Artikel newArtikel = new Artikel(ID, nm, cat, maat, kleur, merk, ink, verk, aant);
		
		artikelService.saveArtikel(newArtikel);
		return artikelToJson(newArtikel).build().toString();
	}
	
	
	private JsonObjectBuilder artikelToJson(Artikel a ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", a.getArtikelID());
		job.add("Naam", a.getNaam());
		job.add("Categorie", a.getCategorie());
		job.add("Maat", a.getMaat());
		job.add("Kleur", a.getKleur());
		job.add("Merk", a.getMerk());
		job.add("Inkoopprijs", a.getInkoopprijs());
		job.add("Verkoopprijs", a.getVerkoopprijs());
		job.add("Aantal", a.getAantal());
		return job;
	}
	
	
	@DELETE
	@RolesAllowed({"user", "admin"})
	@Path("{ID}/{Maat}/{Kleur}")
	public Response deleteArtikel(@PathParam("ID") int ID, @PathParam("Maat")String maat, @PathParam("Kleur") String kleur ){
		Artikel found = null;
		
		for (Artikel a : artikelService.getVoorraad()){
			if (a.getArtikelID() == ID && a.getMaat().equals(maat) && a.getKleur().equals(kleur)){
				found = a;
				break;
			}
		}
		
		if(found == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		else {
			artikelService.deleteArtikel(ID, maat, kleur);
		return Response.ok().build();
		}
	}
	
	/*
	@PUT
	@Path("update/{ID}/{Maat}/{Kleur}")
	public Response updateArtikel(@PathParam("ID") int ID, @PathParam("Maat")String maat, @PathParam("Kleur")String kleur,
			@FormParam("Naam") String naam, @FormParam("Categorie") String categorie, @FormParam("Merk") String merk,
			@FormParam("Inkoopprijs") double inkoopprijs, @FormParam("Verkoopprijs") double verkoopprijs, @FormParam("Aantal") int aantal){
		Voorraad found = null;
		
		for(Voorraad v : voorraadService.getVoorraad()){
			if (v.getArtikel().getID() == ID && v.getArtikel().getMaat().equals(maat) && v.getArtikel().getKleur().equals(kleur)){
				found = v;
				break;
				}
		}
		
		if(found == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		else {	
			found.getArtikel().setArtikelNaam(naam);
			found.getArtikel().setArtikelCategorie(categorie);
			found.getArtikel().setArtikelMerk(merk);
			found.getArtikel().setInkoopprijs(inkoopprijs);
			found.getArtikel().setVerkoopprijs(verkoopprijs);
			found.setAantal(aantal);
			
			voorraadService.updateArtikel(found);
		return Response.ok().build();
	}
	}
	*/
	
		
	@PUT
	@RolesAllowed({"user", "admin"})
	@Path("{ID}/{Maat}/{Kleur}")
	@Produces("application/json")
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Consumes("application/json")
	public String updateArtikel(@PathParam("ID") int ID, @PathParam("Maat")String maat, @PathParam("Kleur")String kleur,
	@FormParam("Naam") String naam, @FormParam("Categorie") String categorie, @FormParam("Merk") String merk,
	@FormParam("Inkoopprijs") double inkoopprijs, @FormParam("Verkoopprijs") double verkoopprijs, @FormParam("Aantal") int aantal){
		
		Artikel found = null;
		for (Artikel a : artikelService.getVoorraad()){
			if (a.getArtikelID() == ID && a.getMaat().equals(maat) && a.getKleur().equals(kleur)){
				
			    a.setArtikelNaam(naam);
				a.setArtikelCategorie(categorie);
				a.setArtikelMerk(merk);
				a.setInkoopprijs(inkoopprijs);
				a.setVerkoopprijs(verkoopprijs);
				a.setAantal(aantal);
				
			//	artikelService.updateArtikel(a);
				return artikelToJson(found).build().toString();	
			}
		}
		throw new WebApplicationException("Artikel niet gevonden");
	}

}
