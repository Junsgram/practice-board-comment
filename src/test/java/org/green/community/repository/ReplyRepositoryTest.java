package org.green.community.repository;

import org.green.community.entity.Board;
import org.green.community.entity.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
public class ReplyRepositoryTest {
    @Autowired
    private ReplyRepository replyRepository;

    //@Test
    public void insertReply() {
        IntStream.rangeClosed(1,300).forEach(i -> {
            long bno = (long) (Math.random()*100)+1;
            Board board = Board.builder().bno(bno).build();
            Reply reply = Reply.builder()
                    .text("Reply..." + i)
                    .board(board)
                    .replyer("guest")
                    .build();
            replyRepository.save(reply);
        });
    }

    @DisplayName("게시글 댓글 조회")
    @ParameterizedTest
    @MethodSource("findByBno")
    public void testListByBoard(long value) {
        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(value).build());
        replyList.forEach(reply -> System.out.println("reply : " + reply));
    }

    private static Stream<Arguments> findByBno() {
        return Stream.of(
                arguments(27L),
                arguments(31L),
                arguments(19L)
        );
    }
}
