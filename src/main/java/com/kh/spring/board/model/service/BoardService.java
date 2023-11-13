package com.kh.spring.board.model.service;

import java.util.ArrayList;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardService {

	//게시글 총 갯수 가져오기
	int selectListCount();
	//게시글 리스트 조회
	ArrayList<Board> selectList(PageInfo pi);
	
	//게시글 작성 서비스(insert)
	int insertBoard(Board b);
	
	//게시글 상세조회
	Board selectBoard(int boardNo);
	
	//게시글 조회수증가(update)
	int increaseCount(int boardNo);
	
	//게시글 삭제서비스(update)
	int deleteBoard(int boardNO);
	
	//게시글 수정 서비스(update)
	int updateBoard(Board b);
}
