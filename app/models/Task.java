package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Task extends Model {
	public Long taskHolder;
	public String name;
	public String comment;
	public boolean isEnd;

	public Task(Long taskHolder, String name, String comment) {
		this.taskHolder = taskHolder;
		this.name = name;
		this.comment = comment;
		isEnd = false;
	}
}