package calendarschedules.commons;

/**
 * SANTIAGO, Jerome R.
 * Developer
 * 06/27/2019
 */

import java.awt.EventQueue;

import javax.swing.UIManager;

import calendarschedules.views.CalendarSchedule;

public class CalendarScheduleMain {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					CalendarSchedule frame = new CalendarSchedule();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
