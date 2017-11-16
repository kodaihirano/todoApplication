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

	public Task(String taskHolder, String name, String comment, String deadLine) {
		this.taskHolder = taskHolder;
		this.name = name;
		this.comment = comment;
		Calendar c = Calendar.getInstance();
		this.deadLine = c.getTime();
//		String dateStr = "20140101";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
			this.deadLine = sdf.parse(deadLine);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		isEnd = false;
	}
	public void accomplishTask() {
		isEnd = true;
//		System.out.println("\n\n" + name + " is "+ isEnd + "\n\n");
		this.save();
	}
}