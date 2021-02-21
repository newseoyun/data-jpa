package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String name, int age);
    List<Member> findTop3HelloBy();

    //@Query(name = "Member.findByUsername") 이름이 기본 형식에 맞아서 여길 생략해도 잘 되긴 함.
    List<Member> findByUsername(@Param("username") String username);

    // 실무에서 많이 사용하는 방식
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);
}
