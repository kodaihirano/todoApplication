package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class DPUser extends Model {
	String name;

	public DPUser(String name, String Password){
		this.name = name;
	}
}