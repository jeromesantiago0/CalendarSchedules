package calendarschedules.commons.dbMgt;

/**
 * SANTIAGO, Jerome R.
 * Developer
 * 06/27/2019
 */


import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProjectPlanAddTask {
	PreparedStatement pstmt = null;
	public int addTask(Connection conn, String statement){
		try{
			pstmt = conn.prepareStatement(statement);
			return pstmt.executeUpdate();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
}
