package ipass.JeansNLifestyle.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ipass.JeansNLifestyle.domain.Artikel;
import ipass.JeansNLifestyle.domain.Klant;
import ipass.JeansNLifestyle.domain.Verkoop;
import ipass.JeansNLifestyle.domain.VerkoopCompleet;
import ipass.JeansNLifestyle.domain.VerkoopRegel;

public class VerkoopCompleetDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;
	private VerkoopDAO verkoopDAO = new VerkoopDAO();
	private VerkoopRegelDAO verkoopregelDAO = new VerkoopRegelDAO();
	private KlantDAO klantDAO = new KlantDAO();
	private ArtikelDAO artikelDAO = new ArtikelDAO();
	
	private List<VerkoopCompleet> selectCompleteVerkopen(String query){ //als dit t niet doet, weer public maken <<
	List<VerkoopCompleet> verkopencompleet = new ArrayList<VerkoopCompleet>();

		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			
			while(dbResultSet.next()){
				int verkoopID = dbResultSet.getInt("ID");
				
				int klantID = dbResultSet.getInt("Klant_ID");
				int artikelID = dbResultSet.getInt("Artikel_ID");
				String artikelMaat = dbResultSet.getString("Artikel_Maat");
				String artikelKleur = dbResultSet.getString("Artikel_Kleur");
		
				
				Verkoop newVerkoop = verkoopDAO.findByVerkoopID(verkoopID);
				VerkoopRegel newVerkoopRegel = verkoopregelDAO.findVerkoopRegelByPK(verkoopID, artikelID, artikelMaat, artikelKleur);
			Artikel newArtikel = artikelDAO.findByPK(artikelID, artikelMaat, artikelKleur);
			Klant newKlant = klantDAO.findKlantByID(klantID);
				newVerkoop.setKlant(newKlant);
			VerkoopCompleet newVerkoopCompleet = new VerkoopCompleet(newVerkoop, newVerkoopRegel);
				newVerkoopCompleet.setArtikel(newArtikel);
				verkopencompleet.add(newVerkoopCompleet);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
		return verkopencompleet;
}
	
	public List<VerkoopCompleet> findAllCompleteVerkopen(){
		return selectCompleteVerkopen("SELECT verk.\"ID\", verk.\"Klant_ID\", vr.\"Artikel_ID\", vr.\"Artikel_Maat\", vr.\"Artikel_Kleur\" from public.\"Verkoop\" as verk  join public.\"VerkoopRegel\" as vr on (verk.\"ID\" = vr.\"ID\") join public.\"Klant\" as k on (verk.\"Klant_ID\" = k.\"ID\")  group by (verk.\"ID\", vr.\"Artikel_ID\", vr.\"Artikel_Maat\", vr.\"Artikel_Kleur\")");
	}	

}
