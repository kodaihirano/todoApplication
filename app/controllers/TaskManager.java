package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	private static void checkSignedIn() {
		if (session.get("userId") == null) {
			index();
		}
		// System.out.println("\n\n" + session.get("user") + "\n\n");
	}
	private static Long getTaskId() {
		return Long.parseLong(params.get("taskId"));
	}
	private static Long getUserId() {
		return Long.parseLong(session.get("userId"));
	}

	public static void signIn() {
		List<TMUser> usersByName = TMUser.find("name = ?1", params.get("name")).fetch();
		if (usersByName.size() == 1) {
			TMUser usr = usersByName.get(0);
			String password = params.get("password");
			boolean isCorrectPass = usr.checkPassword(password);
			if (isCorrectPass) {
				session.put("userId", usr.id);
				list();
			} else {
				validation.current().addError("field", "password is not correct", "variables1", "variables2");
				params.flash();
				validation.keep();
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
		session.put("userId", usr.id);
		list();
	}

	public static void signUpConfirm() {
		validation.required("name", params.get("name"));
		validation.required("password", params.get("password"));
		if (!TMUser.find("name = ?1", params.get("name")).fetch().isEmpty()) {
			validation.current().addError("field", "this account name is already being used", "variables1",
					"variables2");
		}
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			signUpForm();
		}
		renderArgs.put("name", params.get("name"));
		renderArgs.put("password", params.get("password"));
		render();
	}

	public static void list() {
		checkSignedIn();
		int pageOnGoing = 0;
		int pageAccomplished = 0;
		int numTaskAtOnce = 5;
		boolean isOnAccomplished = true;
		if (params.get("isOnAccomplished") != null) {
			isOnAccomplished = params.get("isOnAccomplished").equals("true");
			// System.out.println("\n\n" + isOnAccomplished + "\n\n");
		}
		if (params.get("pageAccomplished") != null) {
			pageAccomplished = Integer.parseInt(params.get("pageAccomplished"));
			pageAccomplished = pageAccomplished < 0 ? 0 : pageAccomplished;
		}

		List<Task> tasksOnGoing = Task.find("taskHolderId = ?1 and isEnd = ?2", session.get("userId"), false).fetch();
		// List<Task> tasks = Task.find("isEnd = ?1", false).fetch();
		if (params.get("pageOnGoing") != null) {
			pageOnGoing = Integer.parseInt(params.get("pageOnGoing"));
			// System.out.println("\n\n" + "PageOnGoing:" + pageOnGoing +
			// "\n\n");
			pageOnGoing = pageOnGoing < 0 ? 0 : pageOnGoing;
		}
		List<Task> entries1 = new ArrayList<Task>();
		for (int i = pageOnGoing * numTaskAtOnce; i < (pageOnGoing + 1) * numTaskAtOnce
				&& i < tasksOnGoing.size(); i++) {
			entries1.add(tasksOnGoing.get(i));
			// System.out.println("\n\n" + "AddPageOnGoing:" + i + "\n\n");
		}

		List<Task> tasksAccomplished = new ArrayList<Task>();
		List<Task> entries2 = new ArrayList<Task>();
		if (isOnAccomplished) {
			tasksAccomplished = Task.find("taskHolderId = ?1 and isEnd = ?2", session.get("userId"), true).fetch();
			for (int i = pageAccomplished * numTaskAtOnce; i < (pageAccomplished + 1) * numTaskAtOnce
					&& i < tasksAccomplished.size(); i++) {
				entries2.add(tasksAccomplished.get(i));
			}
		}

		renderArgs.put("entries", entries1);
		renderArgs.put("entries2", entries2);
		renderArgs.put("pageOnGoing", pageOnGoing);
		renderArgs.put("pageAccomplished", pageAccomplished);
		renderArgs.put("pageOnGoingIsLast", numTaskAtOnce * (pageOnGoing + 1) >= tasksOnGoing.size());
		renderArgs.put("pageAccomplishedIsLast", numTaskAtOnce * (pageAccomplished + 1) >= tasksAccomplished.size());
		renderArgs.put("isOnAccomplished", isOnAccomplished);
		render();
	}

	public static void addTask() {
		checkSignedIn();
		System.out.println("\n\n" + params.get("deadLine") + " is accomplished" + "\n\n");
		Task task = new Task(session.get("userId"), params.get("taskName"), params.get("comment"),
				params.get("deadLine"));
		task.save();
		list();
	}

	public static void toggleIsEnd() {
		checkSignedIn();
		// System.out.println("\n\n" + taskId + " is accomplished" + "\n\n");
		Task task = Task.findById(getTaskId());
		task.toggleIsEnd();
		task.save();
		list();
	}

	public static void editTask() {
		checkSignedIn();
		Long taskId = getTaskId();
		Task task = Task.findById(taskId);
		renderArgs.put("taskId", taskId);
		renderArgs.put("taskName", task.name);
		renderArgs.put("comment", task.comment);
		if (task.deadLine != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String deadLine = sdf.format(task.deadLine);
			renderArgs.put("deadLine", deadLine);
		}
		render();
	}
	public static void changeTaskName() {
		checkSignedIn();
		Task task = Task.findById(getTaskId());
		task.changeName(params.get("taskName"));
		task.save();
		list();
	}
	public static String changeAjax(String taskName,String taskId) {
		Task task = Task.findById(Long.parseLong(taskId));
		task.changeName(taskName);
		task.save();
		return taskName;
	}

	public static void postEditedTask() {
		checkSignedIn();
		Task task = Task.findById(getTaskId());
		task.editTask(params.get("taskName"), params.get("comment"), params.get("deadLine"));
		list();
	}

	public static void deleteTask() {
		checkSignedIn();
		Task task = Task.findById(getTaskId());
		task.delete();
		list();
	}

	public static void userSettings() {
		checkSignedIn();
		renderArgs.put("user", TMUser.findById(getUserId()));
		render();
	}

	public static void changePassword() {
		checkSignedIn();
		render();
	}

	public static void deleteUser() {
		checkSignedIn();
		TMUser usr = TMUser.findById(getUserId());
		usr.delete();
		List<Task> tasks = Task.find("taskHolderId = ?1", session.get("userId")).fetch();
		tasks.forEach(e->e.delete());
		session.remove("userId");
		index();
	}
	public static void signOut() {
		session.remove("userId");
		index();
	}

	public static void postChangedPassword() {
		checkSignedIn();
		TMUser usr = TMUser.findById(getUserId());
		String oldPassword = params.get("oldPassword");
		boolean isCorrectPass = usr.checkPassword(oldPassword);
		if (isCorrectPass) {
			usr.changePassword(params.get("newPassword"));
			usr.save();
		} else {
			validation.current().addError("field", "old password is not correct", "variables1", "variables2");
			params.flash();
			validation.keep();
			changePassword();
		}
		userSettings();
	}


}