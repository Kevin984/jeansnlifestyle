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
import ipass.JeansNLifestyle.webservices.KlantService;

public class VerkoopDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;
	private KlantDAO klantDAO = new KlantDAO();
	private ArtikelDAO artikelDAO = new ArtikelDAO();
//	private VerkoopRegelDAO verkoopregelDAO = new VerkoopRegelDAO();
	
	
	private List<Verkoop> selectVerkopen(String query){
		List<Verkoop> verkopen = new ArrayList<Verkoop>();
		
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			
			while(dbResultSet.next()){
				int verkoopID = dbResultSet.getInt("ID");
				String datum = dbResultSet.getString("Datum");
				int klantID = dbResultSet.getInt("Klant_ID");	
				
				Verkoop newVerkoop = new Verkoop(verkoopID, datum);
				Klant k = klantDAO.findKlantByID(klantID);
				newVerkoop.setKlant(k);
				verkopen.add(newVerkoop);
			}
		}
			catch(SQLException sqle){ 
				sqle.printStackTrace();
			}
				return verkopen;
			}
	
	public int getNextVerkoopID(){	
		String query = "SELECT MAX(\"ID\") +1 as nextid FROM public.\"Verkoop\"";
		int ID = 0;
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			
			while (dbResultSet.next()){
				int ID2 = dbResultSet.getInt("nextid");
				ID = ID2;
			}
		}
		catch (SQLException sqle){ sqle.printStackTrace();
		}
		return ID;	
	}
	
	
	public List<Verkoop> findAllVerkopen(){
		return selectVerkopen("SELECT \"ID\", \"Datum\", \"Klant_ID\" from public.\"Verkoop\"");
	}
	
	public Verkoop findByVerkoopID(int id){
		return selectVerkopen("SELECT \"ID\", \"Datum\", \"Klant_ID\" from public.\"Verkoop\" WHERE \"ID\" = " + id).get(0);		
	}
	
	
	
	public void saveVerkoop(Verkoop verkoop){
		String query = "INSERT INTO public.\"Verkoop\" (\"ID\", \"Datum\", \"Klant_ID\")  VALUES(?,to_date('"+ verkoop.getVerkoopDatum() + "', 'dd-mm-yyyy'),?)"; 
		
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, verkoop.getVerkoopID());
			preparedStatement.setInt(2, verkoop.getKlant().getKlantID());
			//java.sql.Date sqlDate = java.sql.Date.valueOf(verkoop.getVerkoopDatum());
			//preparedStatement.setString(2, verkoop.getVerkoopDatum());
			//preparedStatement.setInt(3, verkoop.getKlant().getID());
			preparedStatement.executeUpdate();	
			preparedStatement.close();
		//	Verkoop newVerkoop = new Verkoop(verkoop.getVerkoopID(), verkoop.getVerkoopDatum());
			
			System.out.println("Verkoop: " + verkoop.getVerkoopID() + " saved.");
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
}

}
