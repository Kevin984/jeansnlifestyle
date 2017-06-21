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
import ipass.JeansNLifestyle.domain.VerkoopRegel;
import ipass.JeansNLifestyle.webservices.KlantService;

public class VerkoopDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;
	private KlantDAO klantDAO = new KlantDAO();

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
				Klant k = klantDAO.findKlantByID(klantID); //zoek klant op gebruikmakend van klant ID
				newVerkoop.setKlant(k); //geef klant aan verkoop mee
				verkopen.add(newVerkoop);
			}
		}
			catch(SQLException sqle){ 
				sqle.printStackTrace();
			}
				return verkopen;
			}
	
	public int getNextVerkoopID(){	
		String query = "SELECT MAX(\"ID\") +1 as nextid FROM public.\"Verkoop\""; //selecteert eerstvolgend beschikbare verkoopID
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
			//java.sql.Date sqlDate = java.sql.Date.valueOf(verkoop.getVerkoopDatum());    dit werkt niet, vandaar dat datum in java een String is.
			preparedStatement.executeUpdate();	
			preparedStatement.close();
			System.out.println("Verkoop: " + verkoop.getVerkoopID() + " saved.");
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
}
	public boolean deleteVerkoop(Verkoop verkoop){
		boolean result = false;
		boolean verkoopExists = findByVerkoopID(verkoop.getVerkoopID()) != null;
		
		if(verkoopExists){
			String query = "DELETE FROM public.\"Verkoop\" WHERE \"ID\" = " + verkoop.getVerkoopID();
			
			try(Connection con = super.getConnection()){ //maak connectie met de database
				Statement stmt = con.createStatement();
				if(stmt.executeUpdate(query) == 1){ //als er niet meer en niet minder dan 1 regel verwijderd is, result = true
					result = true;			
				}
			} 
			catch (SQLException sqle){
				sqle.printStackTrace(); }
		}
			return result;
	}
}
