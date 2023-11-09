package com.kh.spring.board.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagenation;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
//	@RequestMapping("list.bo")
//	public String selectList(@RequestParam(value="cpage", defaultValue="1") int currentPage, Model model) {
//		int boarCount = boardService.selectListCount();
//		
//		PageInfo pi = Pagenation.getPageInfo(boarCount, currentPage, 10, 5);
//		
//		ArrayList<Board> list = boardService.selectList(pi);
//		
//		model.addAttribute("list", list);
//		model.addAttribute("pi",pi);
//		
//		return "board/boardListView";
//	}
	
	@RequestMapping("list.bo")
	public ModelAndView selectList(@RequestParam(value="cpage", defaultValue="1") int currentPage, 
			ModelAndView mv) {
			
		PageInfo pi = Pagenation.getPageInfo(boardService.selectListCount(), currentPage, 10, 5);
		
		mv.addObject("pi",pi)
		  .addObject("list", boardService.selectList(pi))
		  .setViewName("board/boardListView");
		
		return mv;
	}
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		return "board/boardEnrollForm";
	}
}
