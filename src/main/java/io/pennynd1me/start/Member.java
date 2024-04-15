package io.pennynd1me.start;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Setter
@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    private String id;

    private String username;

    private Integer age;
}
