package calendarschedules.commons;

/**
 * SANTIAGO, Jerome R.
 * Developer
 * 06/27/2019
 */


import java.util.ArrayList;
import java.util.Date;

public class ProjectPlan {
	private String projectName;
	private Date startDate;
	private Date endDate;
	private ArrayList<String> taskList;
	
	//setters
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}
	public void setTaskList(ArrayList<String> taskList){
		this.taskList = taskList;
	}
	
	//getters
	public String getProjectName(){
		return this.projectName;
	}
	public Date getStartDate(){
		return this.startDate;
	}
	public Date getEndDate(){
		return this.endDate;
	}
	public ArrayList<String> getTaskList(){
		return this.taskList;
	}
}
