package org.green.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.green.community.dto.ReplyDTO;
import org.green.community.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController {
    // field
    // Service 의존 주입
    public final ReplyService replyService;

    //constructor

    // method
    @GetMapping(value ="/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    // @PathVariable 어노테이션은 url에 매개변수에 할당된 값을 받아온다
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno) {
        log.info("bno :" + bno);
        // HttpStauts.OK -> 네트워크에 데이터가 전송이 잘되면 404가 아닌 200번대 응답이 발생하고 그 응답상태를 클라이언트에게 보내준다
        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
    }

    // 댓글 등록
    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO) {
        Long rno = replyService.register(replyDTO);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
        replyService.delete(rno);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO) {
        replyService.modify(replyDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
