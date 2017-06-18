package ipass.JeansNLifestyle.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ipass.JeansNLifestyle.domain.Artikel;
import ipass.JeansNLifestyle.domain.Verkoop;
import ipass.JeansNLifestyle.domain.VerkoopRegel;

public class VerkoopRegelDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;
	private ArtikelDAO artikelDAO = new ArtikelDAO();
	private VerkoopDAO verkoopDAO = new VerkoopDAO();
	
	private List<VerkoopRegel> selectVerkoopRegels(String query){
		List<VerkoopRegel> verkoopregels = new ArrayList<VerkoopRegel>();
		
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			
			while(dbResultSet.next()){
				 int ID = dbResultSet.getInt("ID");
				 int artikelID = dbResultSet.getInt("Artikel_ID");
				 String artikelMaat = dbResultSet.getString("Artikel_Maat");
				 String artikelKleur = dbResultSet.getString("Artikel_Kleur");
				 int aantal = dbResultSet.getInt("Aantal");
				 
				 Artikel newArtikel = artikelDAO.findByPK(artikelID, artikelMaat, artikelKleur);
				 Verkoop newVerkoop = verkoopDAO.findByVerkoopID(ID);
				 
				 VerkoopRegel newVerkoopRegel = new VerkoopRegel(newVerkoop, newArtikel, aantal);
				 verkoopregels.add(newVerkoopRegel);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return verkoopregels;
	}
	
	public List<VerkoopRegel> findAllVerkoopRegels(){
		return selectVerkoopRegels("SELECT \"ID\", \"Artikel_ID\", \"Artikel_Maat\", \"Artikel_Kleur\", \"Aantal\" FROM public.\"VerkoopRegel\"");
	}
	
	public VerkoopRegel findVerkoopRegelByPK(int ID, int artikelID, String artikelMaat, String artikelKleur){
		return selectVerkoopRegels("SELECT \"ID\", \"Artikel_ID\", \"Artikel_Maat\", \"Artikel_Kleur\", \"Aantal\" FROM public.\"VerkoopRegel\"" 
				+ " WHERE \"ID\" = " + ID + " AND \"Artikel_ID\" = " + artikelID + " AND \"Artikel_Maat\" = '" + artikelMaat + "' AND \"Artikel_Kleur\" = '" + artikelKleur +"'").get(0);
	}
	 
	public void saveVerkoopRegel(VerkoopRegel verkoopregel){
		String query = "INSERT INTO public.\"VerkoopRegel\" (\"ID\", \"Artikel_ID\", \"Artikel_Maat\", \"Artikel_Kleur\", \"Aantal\")  VALUES(?,?,?,?,?)"; 
		
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, verkoopregel.getVerkoop().getVerkoopID());
			preparedStatement.setInt(2, verkoopregel.getArtikel().getArtikelID());
			preparedStatement.setString(3,  verkoopregel.getArtikel().getMaat());
			preparedStatement.setString(4, verkoopregel.getArtikel().getKleur());
			preparedStatement.setInt(5, verkoopregel.getAantal());
			preparedStatement.executeUpdate();	
			preparedStatement.close();
		//	VerkoopRegel newVerkoopRegel = new VerkoopRegel(verkoopregel.getID(), verkoopregel.getArtikel_ID(), verkoopregel.getArtikel_Maat(), verkoopregel.getArtikel_Kleur(), verkoopregel.getAantal());
			System.out.println("Verkoopregel: " + verkoopregel.getVerkoop().getVerkoopID() + " " + verkoopregel.getArtikel().getArtikelID() + " " + verkoopregel.getArtikel().getMaat() + " " + verkoopregel.getArtikel().getKleur()  + " saved.");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
}
}
