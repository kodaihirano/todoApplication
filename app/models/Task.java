package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Task extends Model {
	public String taskHolder;
	public String taskHolderId;
	public String name;
	public String comment;
	public Date deadLine;
	public boolean isEnd;
	public Date createdDay;

	public Task(String taskHolderId, String name, String comment, String deadLine) {
		this.taskHolderId = taskHolderId;
		this.name = name;
		this.comment = comment;
		Calendar c = Calendar.getInstance();
		this.createdDay = c.getTime();
//		String dateStr = "20140101";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
			this.deadLine = sdf.parse(deadLine);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		isEnd = false;
	}
	public void editTask(String name, String comment, String deadLine) {
		this.name = name;
		this.comment = comment;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
			this.deadLine = sdf.parse(deadLine);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        this.save();
	}
	public void toggleIsEnd() {
		isEnd = !isEnd;
//		System.out.println("\n\n" + name + " is "+ isEnd + "\n\n");
	}
}