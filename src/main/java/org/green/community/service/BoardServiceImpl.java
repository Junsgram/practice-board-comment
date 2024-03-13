package org.green.community.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.green.community.dto.BoardDTO;
import org.green.community.dto.PageRequestDTO;
import org.green.community.dto.PageResultDTO;
import org.green.community.entity.Board;
import org.green.community.entity.Member;
import org.green.community.repository.BoardRepository;
import org.green.community.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    // 등록
    @Override
    public Long register(BoardDTO dto) {
        Board board = dtoToEntity(dto);
        boardRepository.save(board);
        return board.getBno();
    }
    // 목록
    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
//        Page<Object[]> result =
//                boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Function<Object[], BoardDTO> fn =(en->entityToDto((Board) en[0],(Member) en[1],(Long) en[2]));
        Page<Object[]> result = boardRepository.searchPage(pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending()));
        return new PageResultDTO<>(result, fn);
    }
    // 상세조회

    @Override
    public BoardDTO get(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        return entityToDto((Board) arr[0],(Member) arr[1], (Long) arr[2]);
    }

    @Transactional // 데이터베이스의 연결을 끊겨 다른 메소드를 실행시키지 못해 해당 메소드로 연결을 지속시켜준다.(fetch lazy를 적용하였기에 연결이 끊김)
    @Override
    public void removeWithReply(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = boardRepository.getReferenceById(boardDTO.getBno());
        if(board != null) {
            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());
            boardRepository.save(board);
        }
    }

}
