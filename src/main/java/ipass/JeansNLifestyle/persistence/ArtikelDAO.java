package ipass.JeansNLifestyle.persistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ipass.JeansNLifestyle.domain.Artikel;

public class ArtikelDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;

	private List<Artikel> selectArtikelen(String query){
		List<Artikel> artikelen = new ArrayList<Artikel>();
				
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			
			while(dbResultSet.next()){ 
				//voor elke regel pak de ID, naam etc en maar er een artikel van gebruikmakend van Artikel POJO
				 int ID = dbResultSet.getInt("ID");
				 String naam = dbResultSet.getString("Naam");
				 String categorie = dbResultSet.getString("Categorie");
				 String maat = dbResultSet.getString("Maat");
				 String kleur = dbResultSet.getString("Kleur");
				 String merk = dbResultSet.getString("Merk");
				 double inkoopprijs = dbResultSet.getDouble("Inkoopprijs");
				 double verkoopprijs = dbResultSet.getDouble("Verkoopprijs");
				 int aantal = dbResultSet.getInt("Aantal");
				 Artikel newArtikel = new Artikel(ID, naam, categorie ,maat, kleur, merk, inkoopprijs, verkoopprijs, aantal);
				//voeg artikel toe aan artikelen lijst
				 artikelen.add(newArtikel);		
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return artikelen; 
	}
	
	public List<Artikel> findAll(){
		return selectArtikelen("SELECT \"ID\", \"Naam\", \"Categorie\", \"Maat\", \"Kleur\", \"Merk\", \"Inkoopprijs\", \"Verkoopprijs\", \"Aantal\" FROM public.\"Artikel\" ORDER BY \"ID\" ASC, \"Maat\" ASC, \"Kleur\" ASC ");
	}
	
	//gebruik de primary keys (id, maat, kleur) van artikel om een specifiek artikel te vinden
	public Artikel findByPK(int ID, String maat, String kleur){ 	
		return selectArtikelen("SELECT \"ID\", \"Naam\", \"Categorie\", \"Maat\", \"Kleur\", \"Merk\", \"Inkoopprijs\", \"Verkoopprijs\", \"Aantal\" "
			+ "FROM public.\"Artikel\" " 
				+ " WHERE \"ID\" = " + ID + "AND \"Maat\" = '" + maat + "' AND \"Kleur\" = '" + kleur + "'").get(0);
	}
	 
	public void saveArtikel(Artikel artikel){
		String query = "INSERT INTO public.\"Artikel\" (\"ID\", \"Naam\", \"Categorie\", \"Maat\", \"Kleur\", \"Merk\", \"Inkoopprijs\", \"Verkoopprijs\", \"Aantal\")  VALUES(?,?,?,?,?,?,?,?,?)"; 
		
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query);
			// eerste vraagteken = 1
			preparedStatement.setInt(1, artikel.getArtikelID()); 
			//tweede = 2, etc.
			preparedStatement.setString(2, artikel.getNaam()); 
			preparedStatement.setString(3,  artikel.getCategorie());
			preparedStatement.setString(4, artikel.getMaat());
			preparedStatement.setString(5, artikel.getKleur());
			preparedStatement.setString(6, artikel.getMerk());
			preparedStatement.setDouble(7, artikel.getInkoopprijs());
			preparedStatement.setDouble(8, artikel.getVerkoopprijs());
			preparedStatement.setInt(9, artikel.getAantal());
			preparedStatement.executeUpdate();	
			preparedStatement.close();

			System.out.println("Artikel: " + artikel.getNaam()  + " saved.");
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
}
	
	
	public boolean deleteArtikel(Artikel artikel){
		boolean result = false;
		boolean artikelExists = findByPK(artikel.getArtikelID(), artikel.getMaat(), artikel.getKleur()) != null;
		
		if(artikelExists){
			
			String query = "DELETE FROM public.\"Artikel\" WHERE \"ID\" IN ("+artikel.getArtikelID()+") AND \"Maat\" IN ('"+artikel.getMaat()+"') AND \"Kleur\" IN ('"+artikel.getKleur()+"')";
			String query2 = "DELETE FROM public.\"VerkoopRegel\" WHERE \"Artikel_ID\" IN (?) AND \"Artikel_Maat\" IN (?) AND \"Artikel_Kleur\" IN (?)";
			
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				//verwijder eerst alle verkoopregels met hetzelfde artikel omdat ze gelinkt zijn aan artikel
				preparedStatement = con.prepareStatement(query2); 
				
				preparedStatement.setInt(1, artikel.getArtikelID());
				preparedStatement.setString(2, artikel.getMaat());
				preparedStatement.setString(3, artikel.getKleur());
				preparedStatement.executeUpdate();
				preparedStatement.close();
				
				preparedStatement = con.prepareStatement(query);
				//verwijder nu het artikel
				if(preparedStatement.executeUpdate() == 1){     
					result = true;
				}
				preparedStatement.close();
				
				
				//als er niet meer en niet minder dan 1 regel is verwijderd, result = true
				if(stmt.executeUpdate(query) == 1){ 
					result = true;
					
				
				}
			} 
			catch (SQLException sqle){
				sqle.printStackTrace(); }
		}
			return result;
	}
	
	/*
	 public boolean deleteArtikel(Artikel artikel){
		boolean result = false;
		boolean artikelExists = findByPK(artikel.getArtikelID(), artikel.getMaat(), artikel.getKleur()) != null;
		
		if(artikelExists){
			String query = "DELETE FROM public.\"Artikel\" WHERE \"ID\" = " + artikel.getArtikelID() + "AND \"Maat\" = '" + artikel.getMaat() + "' AND \"Kleur\" = '" + artikel.getKleur() +"'";
			String query2 ="DELETE FROM public.\"Verkoop\" WHERE \"Klant_ID\" IN (?)";
			String query3 = "DELETE FROM public.\"VerkoopRegel\" WHERE \"ID\" IN ((SELECT \"ID\" FROM public.\"Verkoop\" WHERE \"Klant_ID\" = ?))";
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				if(stmt.executeUpdate(query) == 1){
					result = true;
					
				
				}
			} 
			catch (SQLException sqle){
				sqle.printStackTrace(); }
		}
			return result;
	}
	 */
	
	//deze functie zou eerst gebruikt worden voor Use Case: wijzig artikel maar wordt nu gebruikt om het aantal van het artikel te verlagen wanneer er iets verkocht wordt
	public boolean updateArtikel(Artikel artikel){ 
		boolean result = false;
		boolean artikelExists = findByPK(artikel.getArtikelID(), artikel.getMaat(), artikel.getKleur()) != null; //zoek artikel met de primary keys
		
		//update artikel in database
		if(artikelExists){ 
			String query = "UPDATE public.\"Artikel\" "
			+ " SET \"Naam\" = '" 		+ artikel.getNaam()			+"'," 
			+ "  \"Categorie\" = '" 	+ artikel.getCategorie()	+"'," 
			+ "  \"Merk\" = '" 		+ artikel.getMerk()			+"'," 
			+ " \"Inkoopprijs\" = " + artikel.getInkoopprijs() +","
			+ " \"Verkoopprijs\" = "+ artikel.getVerkoopprijs() +","
			+ " \"Aantal\" = " 		+ artikel.getAantal()
			+ " WHERE \"ID\" = " 		+ artikel.getArtikelID()
			+ " AND \"Maat\" = '"  		+ artikel.getMaat()			+"' "
			+ " AND \"Kleur\" = '" 		+ artikel.getKleur()		+"' ";
			
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				if(stmt.executeUpdate(query) == 1){
					result = true;
				}
			}
			
			catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
			return result;
		
	}
}
