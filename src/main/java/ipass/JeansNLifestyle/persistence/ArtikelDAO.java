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
//	public List<Artikel> artikelen;
	
	private List<Artikel> selectArtikelen(String query){
		List<Artikel> artikelen = new ArrayList<Artikel>();
		
		
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			
			while(dbResultSet.next()){
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
	
	public Artikel findByPK(int ID, String maat, String kleur){
		return selectArtikelen("SELECT \"ID\", \"Naam\", \"Categorie\", \"Maat\", \"Kleur\", \"Merk\", \"Inkoopprijs\", \"Verkoopprijs\", \"Aantal\" "
			+ "FROM public.\"Artikel\" " 
				+ " WHERE \"ID\" = " + ID + "AND \"Maat\" = '" + maat + "' AND \"Kleur\" = '" + kleur + "'").get(0);
	}
	 
	public void saveArtikel(Artikel artikel){
		String query = "INSERT INTO public.\"Artikel\" (\"ID\", \"Naam\", \"Categorie\", \"Maat\", \"Kleur\", \"Merk\", \"Inkoopprijs\", \"Verkoopprijs\", \"Aantal\")  VALUES(?,?,?,?,?,?,?,?,?)"; 
		
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, artikel.getArtikelID());
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
			Artikel newArtikel = new Artikel(artikel.getArtikelID(), artikel.getNaam(), artikel.getCategorie(), artikel.getMaat(), artikel.getKleur(), artikel.getMerk(), artikel.getInkoopprijs(), artikel.getVerkoopprijs(), artikel.getAantal());
			//artikelen.add(newArtikel);

			System.out.println("Artikel: " + artikel.getNaam()  + " saved.");
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
}
	
	
	public boolean deleteArtikel(Artikel artikel){
		boolean result = false;
		boolean artikelExists = findByPK(artikel.getArtikelID(), artikel.getMaat(), artikel.getKleur()) != null;
		
		if(artikelExists){
			String query = "DELETE FROM public.\"Artikel\" WHERE \"ID\" = " + artikel.getArtikelID() + "AND \"Maat\" = '" + artikel.getMaat() + "' AND \"Kleur\" = '" + artikel.getKleur() +"'";
			
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				if(stmt.executeUpdate(query) == 1){
					result = true;
					//artikelen.remove(artikel);
				
				}
			} 
			catch (SQLException sqle){
				sqle.printStackTrace(); }
		}
			return result;
	}
	
	public boolean updateArtikel(Artikel artikel){
		boolean result = false;
		boolean artikelExists = findByPK(artikel.getArtikelID(), artikel.getMaat(), artikel.getKleur()) != null;
		
		
		if(artikelExists){
			String query = "UPDATE public.\"Artikel\" SET \"Naam\" = " + artikel.getNaam() 
			+ " SET \"Categorie\" = " + artikel.getCategorie() 
			+ " SET \"Merk\" = " + artikel.getMerk() 
			+ " SET \"Inkoopprijs\" = " + artikel.getInkoopprijs() 
			+ " SET \"Verkoopprijs\" = " + artikel.getVerkoopprijs() 
			+ " SET \"Aantal\" = " + artikel.getAantal()
			+ " WHERE \"ID\" = " + artikel.getArtikelID()
			+ "AND \"Maat\" = "  + artikel.getMaat()
			+ " AND \"Kleur\" =" + artikel.getKleur();
			
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
