package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;
import java.nio.charset.*;
import java.security.*;
import java.math.*;

import javax.xml.bind.*;

@Entity
public class TMUser extends Model {
	public String name;
	public int hight;
	public byte[] hashedPassword;

	public TMUser(String name, String password) {
		this.name = name;
		try {
			Charset charset = StandardCharsets.UTF_8;
			String algorithm = "MD5";
			MessageDigest md = MessageDigest.getInstance(algorithm);
			hashedPassword = md.digest(password.getBytes(charset));
		} catch (NoSuchAlgorithmException e) {
		}
		hight = 150;
	}
	public void changePassword(String password) {
		try {
			Charset charset = StandardCharsets.UTF_8;
			String algorithm = "MD5";
			MessageDigest md = MessageDigest.getInstance(algorithm);
			hashedPassword = md.digest(password.getBytes(charset));
		} catch (NoSuchAlgorithmException e) {
		}
	}
}