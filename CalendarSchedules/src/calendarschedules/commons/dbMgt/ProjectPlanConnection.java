package calendarschedules.commons.dbMgt;

/**
 * SANTIAGO, Jerome R.
 * Developer
 * 06/27/2019
 */

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;
public class ProjectPlanConnection {

	Connection conn = null;
	Statement stmt;
	public Connection connect(){
		try {
			Class.forName("org.sqlite.JDBC");
		
			String dbpath = "jdbc:sqlite:src\\calendarschedules\\resources\\db\\projectplans.db";
			conn = DriverManager.getConnection(dbpath);
			System.out.println("Connected...");
		} catch (Exception e){
			System.out.println("not connected" + e);
		}
	
		return conn;
		
	}
}

