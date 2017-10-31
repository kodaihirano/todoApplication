package controllers;

import play.*;

import play.mvc.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.math.*;

import javax.xml.transform.Result;
import javax.xml.bind.DatatypeConverter;

import models.*;
import models.sample.SampleEntry;
import controllers.*;

public class TaskManager extends Controller {

	public static void index() {
		render();
	}

	public static void signInForm() {
		render();
	}

	public static void signUpForm() {
		render();
	}

	public static void signIn() {
		TMUser usr = TMUser.find("name = ?1", params.get("name")).first();
		String password = params.get("password");
		byte[] hashedInput = null;
		try {
			Charset charset = StandardCharsets.UTF_8;
			String algorithm = "MD5";
			MessageDigest md = MessageDigest.getInstance(algorithm);
			hashedInput = md.digest(password.getBytes(charset));
		} catch (NoSuchAlgorithmException e) {
		}
		Logger.info("name:%s", params.get("name"));
		Logger.info("pass:%s", password);
		Logger.info("hash:%s", hashedInput.toString());
		Logger.info("hash:%s", usr.hashedPassword.toString());
		if (Arrays.equals(hashedInput, usr.hashedPassword)) {
			session.put("user", usr);
			list();
		} else {
			String name = params.get("name");
			TMUser user = new TMUser(name, password);
			user.save();
			signInForm();
		}
	}

	public static void signUp() {
		Logger.info("name:%s", session.get("name"));
		Logger.info("pass:%s", session.get("password"));
		String name = session.get("name");
		String password = session.get("password");
		TMUser usr = new TMUser(name, password);
		usr.save();
		signInForm();
	}

	public static void signUpConfirm() {
		// validation.required(params.get("name"));
		session.put("name", params.get("name"));
		session.put("password", params.get("password"));
		render();
	}

	public static void list() {
		List<Task> tasks = Task.all().fetch();
		renderArgs.put("entries", tasks);
		render();
	}

	public static void login() {
		render();
	}
}