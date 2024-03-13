package org.green.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.green.community.dto.BoardDTO;
import org.green.community.dto.PageRequestDTO;
import org.green.community.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Log4j2
public class BoardController {

    private final BoardService boardService;
    @GetMapping("/list")
    public void list(@ModelAttribute PageRequestDTO pageRequestDTO, Model model){
        log.info("list................"+pageRequestDTO);
        model.addAttribute("result",boardService.getList(pageRequestDTO));
    }
    @GetMapping("/register")
    public void register(){

    }
    @PostMapping("/register")
    public String registerpro(BoardDTO dto, RedirectAttributes redirectAttributes){
        Long bno = boardService.register(dto);
        redirectAttributes.addFlashAttribute("msg",bno);
        return "redirect:/board/list";
    }
    @GetMapping({"/read","/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,@RequestParam(value = "bno") Long bno, Model model){
        BoardDTO boardDTO =  boardService.get(bno);
        model.addAttribute("dto",boardDTO);
    }
    @PostMapping("/remove")
    public String remove(@RequestParam(value = "bno") long bno, RedirectAttributes redirectAttributes){
        boardService.removeWithReply(bno);
        redirectAttributes.addFlashAttribute("msg",bno);
        return "redirect:/board/list";
    }
    @PostMapping("/modify")
    public String modify(BoardDTO dto, PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        boardService.modify(dto);
        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());
        redirectAttributes.addAttribute("bno",dto.getBno());
        return "redirect:/board/read";
    }

}
