package calendarschedules.commons.dbMgt;

/**
 * SANTIAGO, Jerome R.
 * Developer
 * 06/27/2019
 */


import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.omg.CORBA.Request;

public class ProjectPlanReadData {
	PreparedStatement pstmt = null;
	public String getProjectID(Connection conn, String statement){
		try{
			pstmt = conn.prepareStatement(statement);
			ResultSet r1 = pstmt.executeQuery();
			String strProjectID = "";
			if(r1.next())
				 strProjectID = r1.getString(1);
			r1.close();
			
			return strProjectID;
		} catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}	
	private long getDuration(String startDate, String endDate){
		try{
			String strFormattedStart = startDate.split("/")[2] + "-" +
					startDate.split("/")[0] + "-" + 
					startDate.split("/")[1];
			String strFormattedEnd = endDate.split("/")[2] + "-" +
					endDate.split("/")[0] + "-" + 
					endDate.split("/")[1];
			System.out.println("start: " + strFormattedStart);
			System.out.println("end: " + strFormattedEnd);
			LocalDate start = LocalDate.parse(strFormattedStart, DateTimeFormatter.ISO_LOCAL_DATE);
			LocalDate end = LocalDate.parse(strFormattedEnd, DateTimeFormatter.ISO_LOCAL_DATE);
			Duration duration = Duration.between(start.atStartOfDay(), end.atStartOfDay());
			
			return duration.toDays();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return 0;
	}
	public ArrayList<String> getTaskList(Connection conn, String statement){
		ArrayList<String> taskList = new ArrayList<String>();
		try{
			pstmt = conn.prepareStatement(statement);
			ResultSet result = pstmt.executeQuery();
			while(result.next()){
				taskList.add("" +
						result.getString(1) + "~" +
						result.getString(2) + "~" +
						result.getString(3) + "~" +
						getDuration(result.getString(2), result.getString(3)) + "~" +
						result.getString(4) + "~" +
						result.getString(5) + "~" +
						result.getString(6) +
						"");
			}
			
			return taskList;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}
