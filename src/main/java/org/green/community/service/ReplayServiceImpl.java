package org.green.community.service;

import lombok.RequiredArgsConstructor;
import org.green.community.dto.ReplyDTO;
import org.green.community.entity.Board;
import org.green.community.entity.Reply;
import org.green.community.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplayServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO dto) {
        Reply reply = dtoToEntity(dto);
        replyRepository.save(reply);
        return reply.getRno();
    }

    @Override
    public List<ReplyDTO> getList(Long bno) {
        List<Reply> result = replyRepository.getRepliesByBoardOrderByRnoDesc(Board.builder().bno(bno).build());
        return result.stream().map(reply->entityToDto(reply)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDTO dto) {
        replyRepository.save(dtoToEntity(dto));
    }

    @Override
    public void delete(Long rno) {
        replyRepository.deleteById(rno);
    }
}
