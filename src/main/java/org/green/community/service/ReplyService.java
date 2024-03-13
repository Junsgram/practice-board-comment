package org.green.community.service;

import org.green.community.dto.ReplyDTO;
import org.green.community.entity.Board;
import org.green.community.entity.Reply;

import java.util.List;

public interface ReplyService {
    // 댓글 등록
    Long register(ReplyDTO dto);
    // 특정 게시물(Board) 댓글 리스트 조회
    List<ReplyDTO> getList(Long bno);
    // 댓글 수정
    void modify(ReplyDTO dto);
    // 댓글 삭제
    void delete(Long rno);
    // entity(Reply) -> dto 변환
    default ReplyDTO entityToDto(Reply reply) {
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate()).build();
        return dto;
    }
    // dto(ReplyDTO) -> entity(Reply) 변환
    default Reply dtoToEntity(ReplyDTO dto) {
        Board board = Board.builder().bno(dto.getBno()).build();
        Reply reply = Reply.builder()
                .rno(dto.getRno())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .board(board)
                .build();
        return reply;
    }
}
