package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Adiacenza;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutagesDAO {
	
	public List<Nerc> loadAllNercs(Map<Integer, Nerc> mappa) {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
				mappa.put(n.getId(), n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<Adiacenza> getAdiacenze(Map<Integer, Nerc> mappa){
		String sql = "SELECT nerc_one, nerc_two " + 
					  "FROM nercrelations";
		
		List<Adiacenza> lista = new ArrayList<Adiacenza>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n1 = mappa.get(res.getInt("nerc_one"));
				Nerc n2 = mappa.get(res.getInt("nerc_two"));
				if(n1!=null && n2!=null) {
					lista.add(new Adiacenza(n1, n2, 0));
				}
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return lista;
	}
	
	public Integer getPesoAdiacenza(Nerc n1, Nerc n2) {
		String sql = "SELECT DISTINCT YEAR(p1.date_event_began), MONTH(p1.date_event_began) " + 
					 "FROM poweroutages p1, poweroutages p2 " + 
					 "WHERE p1.nerc_id=? AND p2.nerc_id=? AND YEAR(p1.date_event_began)=YEAR(p2.date_event_began) " + 
					 "		AND MONTH(p1.date_event_began)=MONTH(p2.date_event_began)";
		Integer peso = 0;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n1.getId());
			st.setInt(2, n2.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				peso++;
			}

			conn.close();
			return peso;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public List<PowerOutage> getOutages(Map<Integer, Nerc> mappa){
		String sql = "SELECT id, nerc_id, date_event_began, date_event_finished " + 
					 "FROM poweroutages";
		
		List<PowerOutage> lista = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = mappa.get(res.getInt("nerc_id"));
				if(n!=null) {
					PowerOutage po = new PowerOutage(res.getInt("id"),mappa.get(res.getInt("nerc_id")),
							res.getTimestamp("date_event_began").toLocalDateTime(),
							res.getTimestamp("date_event_finished").toLocalDateTime());
					lista.add(po);
				}
			}

			conn.close();
			return lista;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		
	}
}
