package org.green.community.repository;

import org.green.community.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i +"@naver.com")
                    .password("1234")
                    .name("user"+i)
                    .build();
            memberRepository.save(member);
        });
    }
}
