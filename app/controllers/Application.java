package controllers;

import play.mvc.Controller;

public class Application extends Controller {

	public static void index() {
		render();
	}

	public static void testJump() {
		controllers.sample.Questionnaire.index();
	}

	public static void RedirectTM() {
		controllers.TaskManager.index();
	}

	public static void redirectDP() {
		controllers.DigitalPappet.index();
	}
}