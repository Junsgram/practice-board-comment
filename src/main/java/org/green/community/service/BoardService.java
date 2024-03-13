package org.green.community.service;

import org.green.community.dto.BoardDTO;
import org.green.community.dto.PageRequestDTO;
import org.green.community.dto.PageResultDTO;
import org.green.community.entity.Board;
import org.green.community.entity.Member;

public interface BoardService {
    // 등록
    Long register(BoardDTO dto);
    // 게시글 목록 조회
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
    // 상세조회
    BoardDTO get(Long bno);
    // 게시글 삭제
    void removeWithReply(Long bno);
    // 게시글 수정
    void modify(BoardDTO boardDTO);

    //dto -> entity
    default Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member).build();
        return board;
    }

    // entity -> dto
    default BoardDTO entityToDto(Board board, Member member, Long replyCount){
        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .replyCount(replyCount.intValue())
                .build();
        return dto;
    }
}
