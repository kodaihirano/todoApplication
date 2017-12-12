package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class DPQuest extends Model {
//	private static final Integer[][][] boards = new Integer[][][]{
//			{
//				{1,2,3},
//				{4,5,6},
//				{7,8,9}
//			},{
//				{1,0,0},
//				{0,0,0},
//				{0,0,1}
//			},{
//				{2,0,0},
//				{0,0,0},
//				{0,0,2}
//			}
//		};
//	private int[][] board;
	private int[][] board;
	private Long userId;
	private boolean isClear;

	public DPQuest(Long userId, int[][] board){
		this.board = board;
		this.userId = userId;
		isClear = false;
	}
	public void clear(){
		isClear = true;
	}
	public int[][] getBoard(){
		return board;
	}
	public boolean isCleared(){
		return isClear;
	}
}