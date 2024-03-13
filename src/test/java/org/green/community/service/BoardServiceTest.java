package org.green.community.service;

import org.green.community.dto.BoardDTO;
import org.green.community.dto.PageRequestDTO;
import org.green.community.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class BoardServiceTest {
    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        BoardDTO dto = BoardDTO.builder()
                .title("테스트")
                .content("테스트 내용")
                .writerEmail("user1@naver.com")
                .writerEmail("테스트작성자")
                .build();
        Long bno = boardService.register(dto);
        System.out.println("등록된 번호 : " + bno);
    }
    @Test
    public void testList() {
        PageRequestDTO dto = new PageRequestDTO();
        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(dto);
        for(BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet() {
        BoardDTO dto = boardService.get(30L);
        System.out.println(dto);
    }

    @Test
    public void testRemove() {
        boardService.removeWithReply(100L);
    }

    @Test
    public void testModify() {
        BoardDTO dto = BoardDTO.builder()
                .bno(12L)
                .title("수정테스트 제목")
                .content("수정테스트 내용").build();
        boardService.modify(dto);
        System.out.println(dto);
    }

}
