package org.green.community.repository;

import org.green.community.entity.Board;
import org.green.community.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // 댓글 전체 삭제
    @Modifying //JPQL을 이용하여 update, delete를 사용할 시 해당 어노테이션을 적용해야한다.
    // 게시물 삭제 요청 시 댓글 삭제
    @Query("delete from Reply r where r.board.bno = :bno")
    void deleteByBno(@Param(value="bno")Long bno);

    // 게시물로 댓글 가져오기(댓글 조회) - 쿼리메소드
    List<Reply> getRepliesByBoardOrderByRnoDesc(Board board);
}
