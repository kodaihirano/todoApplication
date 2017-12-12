package controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import models.DPQuest;
import models.DPUser;
import play.mvc.Controller;

public class DigitalPappet extends Controller {
	 private static int[][][] boards = new int[][][]{
		{
			{1,2,2,2,2},
			{0,0,0,0,2},
			{0,0,0,0,2},
			{0,0,0,0,2},
			{0,0,0,0,3}
		},{
			{1,0,0,6,3},
			{0,0,0,7,3},
			{0,0,1,1,6},
			{0,0,2,9,4},
			{0,0,2,9,4}
		},{
			{2,0,0,2,7},
			{0,0,0,7,3},
			{0,0,2,9,4},
			{0,0,2,9,4},
			{0,0,2,9,4}
		}
	};
	public static void index(){
		if (session.get("userId") == null){
			DPUser user = new DPUser("myname", "pass");
			user.save();
			for(int i = 0; i < 3; i++){
//				System.out.println("\n\n"+boards[i]+"\n\n");
				DPQuest quest = new DPQuest(user.id, boards[i]);
				quest.save();
			}
			session.put("userId", user.id);
		}
		Long userId = Long.parseLong(session.get("userId"));
		List<DPQuest> quests = DPQuest.find("userId = ?1", userId).fetch();
		renderArgs.put("quests", quests);
		render();
	}

	public static void quest(){
		Long questId = Long.parseLong(params.get("questId"));
		DPQuest quest = DPQuest.findById(questId);
		renderArgs.put("board", quest.getBoard());
		render();
	}

	public static void enchantjsTest(){
		render();
	}
}