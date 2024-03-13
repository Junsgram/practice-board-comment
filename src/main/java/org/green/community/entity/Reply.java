package org.green.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude="board")
public class Reply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myReplySeq")
    @SequenceGenerator(name="myReplySeq", sequenceName = "reply_seq", initialValue = 1, allocationSize = 1)
    private Long rno;
    private String text;
    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
