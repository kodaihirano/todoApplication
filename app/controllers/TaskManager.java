package controllers;

import play.*;

import play.mvc.*;

import java.util.*;

import javax.xml.transform.Result;

import models.*;
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
		if (true) {
			list();
		}
		// else {
		// }
	}

	public static void signUp() {
		
	}

	public static void signUpConfirm() {
		validation.required(params.get("name"));
		session.put("name", params.get("name"));
		session.put("password", params.get("password"));
		render();
	}
	public static void list() {
		render();
	}
	public static void login(){
		render();
	}
}