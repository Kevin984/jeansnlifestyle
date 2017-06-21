package ipass.JeansNLifestyle.persistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ipass.JeansNLifestyle.domain.Artikel;
import ipass.JeansNLifestyle.domain.Klant;

public class KlantDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;
	
	
	private List<Klant> selectKlanten(String query) {
	List<Klant>	klanten = new ArrayList<Klant>();
		try (Connection con = super.getConnection()){
		Statement stmt = con.createStatement();
		ResultSet dbResultSet = stmt.executeQuery(query);
		while (dbResultSet.next()) {
		int ID = dbResultSet.getInt("ID");
		String naam = dbResultSet.getString("Naam");
		String straat = dbResultSet.getString("Straat");
		String huisnummer = dbResultSet.getString("Huisnummer");
		String postcode = dbResultSet.getString("Postcode");
		String woonplaats = dbResultSet.getString("Woonplaats");
		String email = dbResultSet.getString("Email");
		
		Klant newKlant = new Klant(ID, naam, straat, huisnummer, postcode, woonplaats, email);
		klanten.add(newKlant);
		
		}
		} catch (SQLException sqle) { sqle.printStackTrace(); }
		return klanten;
		}
	
		public List<Klant> findAllKlanten() {
		return selectKlanten("SELECT * FROM public.\"Klant\" ORDER BY \"ID\" ASC "); 
		}

		public Klant findKlantByID(int ID){
			return selectKlanten("SELECT * FROM public.\"Klant\""
				+ "WHERE \"ID\" = " + ID).get(0);
		}
		
		public int getNextKlantID(){	
			String query = "SELECT MAX(\"ID\") +1 as nextid FROM public.\"Klant\"";
			int ID = 0;
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				ResultSet dbResultSet = stmt.executeQuery(query);
				
				while (dbResultSet.next()){
					int ID2 = dbResultSet.getInt("nextid"); 
					//ID is nu het eerstvolgend beschikbare klant ID
					ID = ID2; 
				}
			}
			
			catch (SQLException sqle){ sqle.printStackTrace();
			}
		
			return ID;
		}
		
		public void saveKlant(Klant klant){
			String query = "INSERT INTO public.\"Klant\" (\"ID\", \"Naam\", \"Straat\", \"Huisnummer\", \"Postcode\", \"Woonplaats\", \"Email\" ) VALUES(nextval('public.\"klant_ID_seq\"'),?,?,?,?,?,?)";
			try (Connection con = super.getConnection()) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, klant.getNaam());
				preparedStatement.setString(2,  klant.getStraat());
				preparedStatement.setString(3, klant.getHuisnummer());
				preparedStatement.setString(4, klant.getPostcode());
				preparedStatement.setString(5, klant.getWoonplaats());
				preparedStatement.setString(6, klant.getEmail());
				preparedStatement.executeUpdate();
				preparedStatement.close();
				System.out.println("Klant: " + klant.getNaam()  + " saved.");
				
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}	
	}
	
		public boolean deleteKlant(Klant klant){
			boolean result = false;
			boolean klantExists = findKlantByID(klant.getKlantID()) != null;
			
			if(klantExists){
				String query = "DELETE FROM public.\"Klant\" WHERE \"ID\" = ?";
				String query2 ="DELETE FROM public.\"Verkoop\" WHERE \"Klant_ID\" IN (?)";
				String query3 = "DELETE FROM public.\"VerkoopRegel\" WHERE \"ID\" IN ((SELECT \"ID\" FROM public.\"Verkoop\" WHERE \"Klant_ID\" = ?))";
				try(Connection con = super.getConnection()){
				//verwijder eerst alle verkoopregels die verbonden zijn aan een klant omdat klant niet verwijderd kan worden wanneer er nog verkopen in de database staan die gelinkt zijn aan klant
					preparedStatement = con.prepareStatement(query3);
					
					preparedStatement.setInt(1, klant.getKlantID());
					preparedStatement.executeUpdate();
					preparedStatement.close();
					
					//verwijder alle verkopen gelinkt aan klant
					preparedStatement = con.prepareStatement(query2);
					
					preparedStatement.setInt(1, klant.getKlantID());
					preparedStatement.executeUpdate();
					preparedStatement.close();
					
					//verwijder de klant
					preparedStatement = con.prepareStatement(query);
					
					preparedStatement.setInt(1, klant.getKlantID());
					if(preparedStatement.executeUpdate() == 1){
						result = true;
					}
					preparedStatement.close();
					
				} 
				catch (SQLException sqle){
					sqle.printStackTrace(); } 
			}
				return result;
		}
}
