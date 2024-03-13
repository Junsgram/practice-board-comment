package org.green.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    private String email;
    private String password;
    private String name;
}
