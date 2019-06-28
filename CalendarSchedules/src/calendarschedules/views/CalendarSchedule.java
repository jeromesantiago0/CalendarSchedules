package calendarschedules.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.AbstractListModel;

import calendarschedules.commons.dbMgt.ProjectPlanAddTask;
import calendarschedules.commons.dbMgt.ProjectPlanConnection;
import calendarschedules.commons.dbMgt.ProjectPlanReadData;

public class CalendarSchedule extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTable tblProjectTasks;
	private JButton btnSave;
	private JScrollPane scrTable;
	private JButton btnGenerateReport;
	private JButton btnAdd;
	private JTextField txtTaskID;
	private JTextField txtTaskStartDate;
	private JTextField txtTaskEndDate;
	private JPanel pnlTeaskEditor;
	private JPanel pnlProjectPlan;
	private JPanel pnlProectTasks;
	private JTextField txtProjectName;
	private JTextField txtTargetStartDate;
	private JTextField txtTargetEndDate;
	
	//Database Management Classes
	ProjectPlanConnection ppConn = null;
	ProjectPlanAddTask ppAddTask = null;
	ProjectPlanReadData ppReadData = null;
	private JTextField txtTaskDependencies;
	private JTextField txtTaskDescription;

	/**
	 * Create the frame.
	 */
	public CalendarSchedule() {
		initialize();
		initializeProjectTaskTable();
		setEnableTaskPanel(false);
		
		//trying connections in database
		ppConn = new ProjectPlanConnection();
		ppConn.connect();
	}
	
	//initializes all the components in the form
	private void initialize(){
		setTitle("Project Plan Calendar Schedule");
		setBackground(Color.WHITE);
		setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 470);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		pnlProjectPlan = new JPanel();
		pnlProjectPlan.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Project Plan", TitledBorder.LEADING, TitledBorder.TOP, null, Color.DARK_GRAY));
		pnlProjectPlan.setBackground(Color.WHITE);
		pnlProjectPlan.setBounds(10, 11, 278, 164);
		contentPane.add(pnlProjectPlan);
		pnlProjectPlan.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Project Name:");
		lblNewLabel.setBounds(10, 29, 82, 14);
		pnlProjectPlan.add(lblNewLabel);
		
		JLabel lblTargetStartdate = new JLabel("Target Start Date:");
		lblTargetStartdate.setBounds(10, 54, 98, 14);
		pnlProjectPlan.add(lblTargetStartdate);
		
		JLabel lblTargetEndDate = new JLabel("Target End Date:");
		lblTargetEndDate.setBounds(10, 79, 98, 14);
		pnlProjectPlan.add(lblTargetEndDate);
		
		txtProjectName = new JTextField();
		txtProjectName.setBounds(121, 26, 147, 20);
		pnlProjectPlan.add(txtProjectName);
		txtProjectName.setColumns(10);
		
		txtTargetStartDate = new JTextField();
		txtTargetStartDate.setColumns(10);
		txtTargetStartDate.setBounds(121, 51, 147, 20);
		pnlProjectPlan.add(txtTargetStartDate);
		
		txtTargetEndDate = new JTextField();
		txtTargetEndDate.setColumns(10);
		txtTargetEndDate.setBounds(121, 76, 147, 20);
		pnlProjectPlan.add(txtTargetEndDate);
		
		btnSave = new JButton("Save");
		btnSave.setForeground(Color.DARK_GRAY);
		btnSave.setBackground(Color.WHITE);
		btnSave.setBounds(179, 130, 89, 23);
		btnSave.addActionListener(this);
		pnlProjectPlan.add(btnSave);
		
		pnlTeaskEditor = new JPanel();
		pnlTeaskEditor.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Task Editor", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		pnlTeaskEditor.setBackground(Color.WHITE);
		pnlTeaskEditor.setBounds(298, 11, 546, 164);
		contentPane.add(pnlTeaskEditor);
		pnlTeaskEditor.setLayout(null);
		
		JLabel lblTaskId = new JLabel("Task ID:");
		lblTaskId.setBounds(10, 29, 46, 14);
		pnlTeaskEditor.add(lblTaskId);
		
		txtTaskID = new JTextField();
		txtTaskID.setBounds(100, 26, 165, 20);
		pnlTeaskEditor.add(txtTaskID);
		txtTaskID.setColumns(10);
		
		JLabel lblStartDate = new JLabel("Start Date:");
		lblStartDate.setBounds(10, 57, 72, 14);
		pnlTeaskEditor.add(lblStartDate);
		
		txtTaskStartDate = new JTextField();
		txtTaskStartDate.setColumns(10);
		txtTaskStartDate.setBounds(100, 54, 165, 20);
		pnlTeaskEditor.add(txtTaskStartDate);
		
		JLabel lblEndDate = new JLabel("End Date:");
		lblEndDate.setBounds(10, 85, 72, 14);
		pnlTeaskEditor.add(lblEndDate);
		
		txtTaskEndDate = new JTextField();
		txtTaskEndDate.setColumns(10);
		txtTaskEndDate.setBounds(100, 82, 165, 20);
		pnlTeaskEditor.add(txtTaskEndDate);
		
		JLabel lblTaskDescription = new JLabel("Task Description:");
		lblTaskDescription.setBounds(10, 110, 100, 14);
		pnlTeaskEditor.add(lblTaskDescription);
		
		//custom border for txtTaskDescription
		Border taskBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		
		JLabel lblDependencies = new JLabel("Dependencies:");
		lblDependencies.setBounds(275, 29, 72, 14);
		pnlTeaskEditor.add(lblDependencies);
		
		btnAdd = new JButton("Add");
		btnAdd.setForeground(Color.DARK_GRAY);
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setBounds(447, 130, 89, 23);
		btnAdd.addActionListener(this);
		pnlTeaskEditor.add(btnAdd);
		
		txtTaskDependencies = new JTextField();
		txtTaskDependencies.setEnabled(false);
		txtTaskDependencies.setColumns(10);
		txtTaskDependencies.setBounds(357, 26, 165, 20);
		pnlTeaskEditor.add(txtTaskDependencies);
		
		txtTaskDescription = new JTextField();
		txtTaskDescription.setEnabled(false);
		txtTaskDescription.setColumns(10);
		txtTaskDescription.setBounds(100, 107, 165, 20);
		pnlTeaskEditor.add(txtTaskDescription);
		
		pnlProectTasks = new JPanel();
		pnlProectTasks.setBackground(Color.WHITE);
		pnlProectTasks.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Project Tasks", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		pnlProectTasks.setBounds(10, 196, 834, 190);
		contentPane.add(pnlProectTasks);
		pnlProectTasks.setLayout(null);
		
		scrTable = new JScrollPane();
		scrTable.setBounds(10, 22, 814, 157);
		pnlProectTasks.add(scrTable);
		
		tblProjectTasks = new JTable();
		scrTable.setViewportView(tblProjectTasks);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.DARK_GRAY);
		separator.setBounds(10, 184, 834, 2);
		contentPane.add(separator);
		
		btnGenerateReport = new JButton("Generate Report");
		btnGenerateReport.setForeground(Color.DARK_GRAY);
		btnGenerateReport.setBackground(Color.WHITE);
		btnGenerateReport.setBounds(669, 397, 163, 23);
		btnGenerateReport.addActionListener(this);
		contentPane.add(btnGenerateReport);
	}

	//initializes Project Task Table with column names
	DefaultTableModel tblModel = null;
	private void initializeProjectTaskTable(){
		String[] columnNames = new String[]{
				"Task ID", "Start-Date", "End-Date", "Duration (Days)", "Task", "Dependencies(Task-ID)", "Remarks"
		};
		
		tblModel = new DefaultTableModel(columnNames, 0);
		
		tblProjectTasks.setModel(tblModel);
	}

	
	//enables and disables Task Editor panel
	private void setEnableTaskPanel(boolean enable){
		//sets the Target Start Date to the first task's start date
		txtTaskStartDate.setText(txtTargetStartDate.getText());
		
		txtTaskID.setEnabled(enable);
		txtTaskStartDate.setEnabled(enable);
		txtTaskEndDate.setEnabled(enable);
		txtTaskDependencies.setEnabled(enable);
		txtTaskDescription.setEnabled(enable);
		pnlTeaskEditor.setBackground(enable?Color.WHITE:Color.LIGHT_GRAY);
	}
	
	//clears all the fields in the task editor panel
	private void clearTaskTextFields(){
		txtTaskID.setText("");
		txtTaskStartDate.setText(txtTaskEndDate.getText());
		txtTaskEndDate.setText("");
		txtTaskDescription.setText("");
		txtTaskDependencies.setText("");
	}
	
	//populate Project tAsk Table after adding task
	private void populateProjectTaskTable(){
		tblModel.setRowCount(0);
		for(String tasks : ppReadData.getTaskList(ppConn.connect(), "SELECT * FROM project_tasks WHERE ProjectID = '"+strProjectID+"'")){
			tblModel.addRow(new String[]{
					tasks.split("~")[0],
					tasks.split("~")[1],
					tasks.split("~")[2],
					tasks.split("~")[3],
					tasks.split("~")[4],
					tasks.split("~")[5],
					getRemarks(tasks.split("~")[1], tasks.split("~")[2])
			});
		}
		clearTaskTextFields();
	}
	
	String strProjectID = "";
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//adding project in the database project
		if(e.getSource()==btnSave){
			String sql = "INSERT INTO project(ProjectName, StartDate, EndDate) " +
					"VALUES('"+txtProjectName.getText()+"', '"+txtTargetStartDate.getText()+"', '"+txtTargetStartDate.getText()+"')";
			ppAddTask = new ProjectPlanAddTask();
			
			System.out.println("project: " + sql);
			if(ppAddTask.addTask(ppConn.connect(), sql)>0){
				JOptionPane.showMessageDialog(null, "Please add a task for project "+txtProjectName.getText()+".", 
						"New Project Plan: " + txtProjectName.getText(), JOptionPane.INFORMATION_MESSAGE);
				setEnableTaskPanel(true);
			}
		}
		
		//adding task for the project in the database
		//showing task list in Task List Panel
		else if(e.getSource()==btnAdd){
			ppReadData = new ProjectPlanReadData();
			strProjectID = ppReadData.getProjectID(ppConn.connect(), "SELECT ProjectID FROM project WHERE ProjectName = '"+txtProjectName.getText().trim()+"'");
			String sql = "INSERT INTO project_tasks(taskID, StartDate, EndDate, TaskDesc, TaskDependencies, ProjectID) " + 
					"VALUES('"+txtTaskID.getText()+"', '"+txtTaskStartDate.getText()+"', '"+txtTaskEndDate.getText()+"', '"+txtTaskDescription.getText()+"', '"+txtTaskDependencies.getText()+"', '"+strProjectID+"')";
			
			System.out.println("task: " + sql);
			if(ppAddTask.addTask(ppConn.connect(), sql)>0){
				JOptionPane.showMessageDialog(null, "Successfully added task "+txtTaskID.getText()+"");
				populateProjectTaskTable();
			}
		}
		
		//generate a formatted output of the project plan
		else if(e.getSource()==btnGenerateReport){
			System.out.println("----------------------------------------------------------------------------------");
			System.out.println("#Project Plan for " + txtProjectName.getText());
			System.out.println("#Target Start Date: " + txtTargetStartDate.getText());
			System.out.println("#Target Completion Date: " + txtTargetEndDate.getText());
			System.out.println();
			System.out.println("#Project Tasks");
			System.out.println("Task-ID\tStart-Date\tEnd-Date\tDuration(Days)\tTask\t\t\tDependencies\t\tRemarks");
			System.out.println("-------\t----------\t--------\t--------------\t----\t\t\t------------\t\t-------");
			for(int i = 0; i < tblModel.getRowCount(); i++){
				System.out.print(tblModel.getValueAt(i, 0) + "\t");
				System.out.print(tblModel.getValueAt(i, 1) + "\t");
				System.out.print(tblModel.getValueAt(i, 2) + "\t");
				System.out.print(tblModel.getValueAt(i, 3) + "\t\t");
				System.out.print(tblModel.getValueAt(i, 4) + "\t\t\t");
				System.out.print(tblModel.getValueAt(i, 5) + "\t\t\t");
				System.out.print(getRemarks(tblModel.getValueAt(i, 1).toString(), tblModel.getValueAt(i, 2).toString()) + "\n");
			}
			System.out.println("----------------------------------------------------------------------------------");
		}
	}
	
	private String getRemarks(String startDate, String endDate){
		String strRemarks = "";
		String strFormattedStart = startDate.split("/")[2] + "-" +
				startDate.split("/")[0] + "-" + 
				startDate.split("/")[1];
		String strFormattedEnd = endDate.split("/")[2] + "-" +
				endDate.split("/")[0] + "-" + 
				endDate.split("/")[1];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start;
		try {
			start = sdf.parse(strFormattedStart);
			Date end = sdf.parse(strFormattedEnd);
			Date today = new Date();
			
			if(start.compareTo(today) > 0)
				strRemarks = "NEW";
			else if(start.compareTo(today) < 0)
				strRemarks = "OPEN";
			else if(today.compareTo(end) > 0)
				strRemarks = "CLOSED";
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return strRemarks;
	}

}
