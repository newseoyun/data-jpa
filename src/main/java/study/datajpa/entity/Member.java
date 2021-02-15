package study.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String username;

    protected Member() {
    } // JPA 프록시 객체 생성 시 필요하므로 생성자를 만들어놔야함. private 해놓으면 프록시객체 생성못함

    public Member(String username) {
        this.username = username;
    }

}
