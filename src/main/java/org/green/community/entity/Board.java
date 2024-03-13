package org.green.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
// 연관관계 설정하게 되면 join을 진행하고 성능이 저하되어 toString어노테이션에서 연관관계 변수명을 제외하여 검색 등 필요에 맞게 사용
@ToString(exclude = "writer")
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "boardSeq")
    @SequenceGenerator(name = "boardSeq", sequenceName = "board_seq", initialValue = 1, allocationSize = 1)
    private Long bno;
    private String title;
    private String content;

    //연관 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    public void changeTitle(String title) {
        this.title = title;
    }
    public void changeContent(String content) {
        this.content = content;
    }
}
