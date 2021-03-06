package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") // 연관관계를 맺었을 때, 외래키가 없는 곳에 mappedBy 적어준다
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

}
