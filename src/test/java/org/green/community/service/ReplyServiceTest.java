package org.green.community.service;

import org.green.community.dto.ReplyDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReplyServiceTest {
    @Autowired
    private ReplyService replyService;

    @DisplayName("댓글 조회")
    @Test
    public void testGetList() {
        List<ReplyDTO> result = replyService.getList(50L);
        result.forEach(re -> System.out.println(re));
    }

    @DisplayName("댓글 등록")
    @Test
    public void testInsert() {
        Long rno = replyService.register(ReplyDTO.builder()
                .replyer("user").text("내용").bno(60L).build());
        System.out.println();
    }
}
