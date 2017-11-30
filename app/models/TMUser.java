package models;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class TMUser extends Model {
	public String name;
	// public int hight;
	public byte[] hashedPassword;

	public TMUser(String name, String password) {
		this.name = name;
		hashedPassword = hashString(password);
		// hight = 150;
	}

	public boolean checkPassword(String password) {
		byte[] hashedInput = hashString(password);
		if (Arrays.equals(hashedInput, hashedPassword)) {
			return true;
		} else {
			return false;
		}
	}

	public void changePassword(String password) {
		hashedPassword = hashString(password);
	}

	private byte[] hashString(String password){
		byte[] hashed = new byte[0];
		try {
			Charset charset = StandardCharsets.UTF_8;
			String algorithm = "MD5";
			MessageDigest md = MessageDigest.getInstance(algorithm);
			hashed = md.digest(password.getBytes(charset));
		} catch (NoSuchAlgorithmException e) {
		}
		return hashed;
	}
}