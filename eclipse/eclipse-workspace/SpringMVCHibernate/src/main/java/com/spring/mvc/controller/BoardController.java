package com.spring.mvc.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.mvc.dao.Paging;
import com.spring.mvc.service.BoardService;
import com.spring.mvc.vo.BoardVO;

@Controller
public class BoardController {
    Logger log = Logger.getLogger(this.getClass());
	private int pageSize = 10;
	private int blockCount = 10;
    
    @Resource(name="boardService")
    private BoardService boardService;

    @RequestMapping(value="/board/boardList.do")
    public ModelAndView boardMain(Map<String,Object> commandMap, 
    		@RequestParam(value = "pageNum", defaultValue = "1") int currentPage,
			@RequestParam(value = "keyField", defaultValue = "") String keyField,
			@RequestParam(value = "keyWord", defaultValue = "") String keyWord) throws Exception{
        
    	String pagingHtml = "";
    	
    	//ModelAndView mv = new ModelAndView("/board");
    	
    	@SuppressWarnings({ "unchecked", "rawtypes" })
		HashMap<String, Object> map = new HashMap();
		
		map.put("keyField", keyField);
		map.put("keyWord", keyWord);
		
		int count = this.boardService.getCount(map);
		
		Paging page = new Paging(keyField, keyWord, currentPage, count,
				this.pageSize, this.blockCount, "boardList.do");
		
		pagingHtml = page.getPagingHtml().toString();

		map.put("start", Integer.valueOf(page.getStartCount()));
		map.put("end", Integer.valueOf(page.getEndCount()));
		
         
		List<BoardVO> list = null;
		
		if (count > 0) {
			list = this.boardService.selectBoardList(map);
		} else {
			list = Collections.emptyList();
		}
		int number = count - (currentPage - 1) * this.pageSize;
		
		ModelAndView mv = new ModelAndView("/board");
		mv.addObject("count", Integer.valueOf(count));
		mv.addObject("currentPage", Integer.valueOf(currentPage));
		mv.addObject("boardList", list);
		mv.addObject("pagingHtml", pagingHtml);
		mv.addObject("number", Integer.valueOf(number));
		
        log.debug("DATA SIZE() = "+list.size());
         
        return mv;
    }
    
    @RequestMapping(value="/board/boardWrite.do")
    public ModelAndView boardWrite(Map<String,Object> commandMap) throws Exception{
        
    	ModelAndView mv = new ModelAndView("/boardWriteForm");
        log.debug("인터셉터 테스트2");
         
        List<BoardVO> list = boardService.selectBoardList(commandMap);
        mv.addObject("boardList", list);
         
        return mv;
    }
        
    
    @RequestMapping(value="/board/getBoardContent.do")
    @ResponseBody
    public BoardVO getBoardContent(@RequestParam(value="userSeq")String boardSeq) throws Exception{
    	int getBoardSeq = Integer.parseInt(boardSeq);
    	BoardVO boardContent = boardService.selectBoardContent(getBoardSeq);
    	log.debug("call Board Content Seq : "+boardSeq+" / get Board Content Seq : "+boardContent.getM_boardseq());
    	
    	return boardContent;
    }
    
}
