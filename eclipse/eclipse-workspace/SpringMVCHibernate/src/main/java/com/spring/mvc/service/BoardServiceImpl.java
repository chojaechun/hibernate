package com.spring.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.spring.mvc.dao.BoardDAO;
import com.spring.mvc.vo.BoardVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService{

	Logger log = Logger.getLogger(this.getClass());

	@Resource(name="boardDAO")
	private BoardDAO boardDAO;
	
	@Override
	public List<BoardVO> selectBoardList(Map<String, Object> map) throws Exception {
		return boardDAO.selectBoardList(map);
	}

	@Override
	public BoardVO selectBoardContent(int boardSeq) throws Exception {
		return boardDAO.selectBoardContent(boardSeq);
	}

	@Override
	public int getCount(HashMap<String, Object> map) {
		return boardDAO.getCount(map);
	}

	@Override
	public String insertBoardContent(BoardVO boardVO) throws Exception {
		String result;
		try {
			if(boardVO.getM_title().trim() == "" || boardVO.getM_content().trim() == "" || boardVO.getM_userid().trim() == "") {
				result ="Fail";
			}else {
				boardDAO.insertBoardContent(boardVO);
				result ="Success";
			}
			
		} catch (Exception e) {
			result ="Fail";
			System.out.println(e + " / Insert Fail");
		}
		
		return result;
	}

	@Override
	public String updateBoardContent(BoardVO boardVO) throws Exception {
		String result;
		try {
			if(boardVO.getM_title().trim() == "" || boardVO.getM_content().trim() == "" || boardVO.getM_userid().trim() == "") {
				result ="Fail";
			}else {
				boardDAO.updateBoardContent(boardVO);
				result ="Success";
			}
			
		} catch (Exception e) {
			result ="Fail";
			System.out.println(e + " / Update Fail");
		}
		
		return result;
	}

	@Override
	public void updateHitBoardContent(BoardVO boardVO) throws Exception {
		boardDAO.updateHitBoardContent(boardVO);
	}

}
