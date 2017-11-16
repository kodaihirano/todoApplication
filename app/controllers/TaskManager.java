package controllers;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import models.TMUser;
import models.Task;
import play.mvc.Controller;

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
		List<TMUser> usersByName = TMUser.find("name = ?1", params.get("name")).fetch();
		if (usersByName.size() == 1) {
			TMUser usr = usersByName.get(0);
			String password = params.get("password");
			byte[] hashedInput = null;
			try {
				Charset charset = StandardCharsets.UTF_8;
				String algorithm = "MD5";
				MessageDigest md = MessageDigest.getInstance(algorithm);
				hashedInput = md.digest(password.getBytes(charset));
			} catch (NoSuchAlgorithmException e) {
			}
			// Logger.info("name:%s", params.get("name"));
			// Logger.info("pass:%s", password);
			// Logger.info("hash:%s", hashedInput.toString());
			// Logger.info("hash:%s", usr.hashedPassword.toString());
			if (Arrays.equals(hashedInput, usr.hashedPassword)) {
				session.put("user", usr.name);
				list();
			} else {
				signInForm();
			}
		} else {
			validation.current().addError("field", "this account name does not exist", "variables1", "variables2");
			params.flash();
			validation.keep();
			signInForm();
		}
	}

	public static void signUp() {
		// Logger.info("name:%s", session.get("name"));
		// Logger.info("pass:%s", session.get("password"));
		String name = session.get("name");
		String password = session.get("password");
		TMUser usr = new TMUser(name, password);
		usr.save();
		session.put("user", usr.name);
		list();
	}

	public static void signUpConfirm() {

		validation.required("name", params.get("name"));
		if (!TMUser.find("name = ?1", params.get("name")).fetch().isEmpty()) {
			validation.current().addError("field", "this account name is already being used", "variables1", "variables2");
		}
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			signUpForm();
		}
		session.put("name", params.get("name"));
		session.put("password", params.get("password"));
		render();
	}

	public static void list() {
//		List<Task> tasks = Task.find("taskHolder = ?1", session.get("user")).fetch();
		List<Task> tasks = Task.find("isEnd = ?1", false).fetch();
		renderArgs.put("entries", tasks);
		render();
	}

	public static void addTask() {
		Task task = new Task(session.get("user"), params.get("taskName"), params.get("comment"), params.get("deadLine"));
		task.save();
		list();
	}

	public static void accomplishTask() {
		Long taskId = Long.parseLong(params.get("taskId"));
//		System.out.println("\n\n" + taskId + " is accomplished" + "\n\n");
		Task task = Task.findById(taskId);
		task.accomplishTask();
		list();
	}

	public static void login() {
		render();
	}
}