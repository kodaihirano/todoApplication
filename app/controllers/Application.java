package controllers;

import play.*;

import play.mvc.*;

import java.util.*;

import models.*;
import controllers.*;

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
}