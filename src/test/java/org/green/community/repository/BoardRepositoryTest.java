package org.green.community.repository;

import jakarta.transaction.Transactional;
import org.green.community.entity.Board;
import org.green.community.entity.Member;
import org.green.community.repository.search.SearchBoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder().email("user"+i+"@naver.com").build();
            Board board = Board.builder()
                    .title("title...." + i)
                    .content("content...." + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });
    }

    @Test
    @Transactional
    public void testRead1() {
        Optional<Board> result = boardRepository.findById(100L);
        if (result.isPresent()) {
            Board board = result.get();
            System.out.println("board :" + board);
            System.out.println("작성자 : " + board.getWriter());
        }
    }

    @Test
    public void testReadWithWriter() {
        Object result = boardRepository.getBoardWithWriter(100L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testReadWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(23L);
        for(Object[] o : result) {
            System.out.println(Arrays.toString(o));
        }
    }

    @Test
    public void testReadWithReplyCount() {
        Pageable pageable  = PageRequest.of(0,10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testread3() {
        Object result = boardRepository.getBoardByBno(30L);
        Object[] arr = (Object[]) result;
        for(Object o : arr) {
            System.out.println(o);
        }
    }
    @Test
    public void searchTest() {
        boardRepository.search1();
    }
    @Test
    public void searchTest2() {
        boardRepository.search2();
    }
    @Test
    public void testSearch3() {
        boardRepository.search3();
    }

    @DisplayName("검색 조건에 맞는 결과값 찾는 테스트")
    @ParameterizedTest
    @MethodSource("searchResult")
    public void testSearch4(String type, String keyword) {
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.searchPage(type,keyword,pageable);
        List<Object[]> arr = result.getContent();
        for(Object[] a : arr) {
            System.out.println(a[0].toString());
        }
        System.out.println("결과 :" + result);
    }
    private static Stream<Arguments> searchResult() {
        return Stream.of(
                arguments("t","테스트"),
                arguments("w","user"),
                arguments("c", "테스트")
        );
    }


}
