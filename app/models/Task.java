package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Task extends Model {
	public String taskHolder;
	public Long taskHolderId;
	public String name;
	public String comment;
	public Date deadLine;
	public boolean isEnd;
	public Date createdDay;

	public boolean haveAuthority(Long userId){
		return (userId == taskHolderId);
	}
	public Task(Long taskHolderId, String name, String comment, String deadLine) {
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
	}
	public void toggleIsEnd() {
		isEnd = !isEnd;
//		System.out.println("\n\n" + name + " is "+ isEnd + "\n\n");
	}
	public void changeName(String taskName) {
		this.name = taskName;
	}
}