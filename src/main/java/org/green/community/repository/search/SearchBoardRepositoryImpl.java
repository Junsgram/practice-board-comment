package org.green.community.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.green.community.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2 // 로그를 확인하기 위한 어노테이션
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{
    // field
    // constructor
    public SearchBoardRepositoryImpl() {
        super(Board.class); //Entity 클래스명을 작성해서 사용한다
    }
    // method

    // title 검색 값 log로 출력
    @Override
    public Board search1() {
        log.info("search..................");
        QBoard qBoard = QBoard.board; // QBoard 객체 생성
        // Querydsl의 from 메소드를 사용하여 JPQLQuery객체를 생성 및 Q타입의 객체를 인자로 할당해줌
        JPQLQuery<Board> jpqlQuery = from(qBoard);
        jpqlQuery.select(qBoard).where(qBoard.title.eq("테스트"));
        log.info("jpql쿼리의 값 : " + jpqlQuery);
        List<Board> result = jpqlQuery.fetch();
        log.info("result의 값 : " + result);
        return null;
    }

    // join 활용하여 검색
    @Override
    public Board search2() {
        log.info("search2...........");
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.select(board, member).where(board.bno.eq(1L));
        log.info(jpqlQuery);
        List<Board> result = jpqlQuery.fetch();
        log.info(result);
        return null;
    }

    @Override
    public Board search3() {
        log.info("search3...........");
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count()).where(board.title.eq("테스트"));
        tuple.groupBy(board,member);
        List<Tuple> result = tuple.fetch();
        log.info(result);
        return null;
    }

    // 검색 조건 search 및 paging
    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage.........1");
        // Q도메인 객체 생성
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;
        // JPQLQuery객체 생성 + 조인

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board,member, reply.count());
        // 조회
        // where 절
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);
        booleanBuilder.and(expression);

        if(type!=null) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for(String i : typeArr) {
                switch(i) {
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        tuple.where(booleanBuilder);
        tuple.groupBy(board,member);
        // paging 추가
        this.getQuerydsl().applyPagination(pageable,tuple);
        List<Tuple> result = tuple.fetch();
        log.info(result);
        // 튜플의 갯수를 long 타입으로 전
        long count = tuple.fetchCount();
        log.info(count);
        // toArray -> tuple을 배열로 변경, toList -> 배열을 리스트로 변경
        return new PageImpl<Object[]>(result.stream().map(Tuple::toArray).collect(Collectors.toList()),pageable,count);
        // <Object[]>(result.stream().map(Tuple::toArray).collect(Collectors.toList()).pageable,count);
    }
}
