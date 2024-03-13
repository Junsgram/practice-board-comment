package org.green.community.repository;

import org.green.community.entity.Board;
import org.green.community.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {
    // 연관 관계 Join - 연관 관계가 있으면 on을 사용하지 않아도 된다.
    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);
    // 연관 관계가 없는 Join - 연관 관계가 없을 경우 on을 사용하여 값을 조회한다.
    @Query("select b, r from Board b  left join Reply r on r.board = b where b.bno = :bno")
    List<Object[]>  getBoardWithReply(@Param("bno") Long bno);

    @Query(value = "select b, w, count(r) from Board b left join b.writer w " +
            " left join Reply r on r.board = b group by b,w", countQuery = "select count(b) from Board b")
    //countQuery = Paging을 진행할 때 페이지마다 튜플의 개수를 가져오기 위해 사용하는 구문
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query(value = "select b, w, count(r) from Board b left join b.writer w " +
            " left join Reply r on r.board = b" +
            " where b.bno=:bno group by b, w")
    Object getBoardByBno(@Param("bno") Long bno);

}
