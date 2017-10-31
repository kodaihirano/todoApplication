package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;
import java.nio.charset.*;
import java.security.*;

@Entity
public class TMUser extends Model {
	public int id;
	public String name;
	public byte[] hashedPassword;

	public TMUser(int id, String name, String password) {
		this.id = id;
		this.name = name;
		try {
			Charset charset = StandardCharsets.UTF_8;
			String algorithm = "MD5";
			MessageDigest md = MessageDigest.getInstance(algorithm);
			hashedPassword = md.digest(password.getBytes(charset));
		} catch (NoSuchAlgorithmException e) {
		}
	}
}