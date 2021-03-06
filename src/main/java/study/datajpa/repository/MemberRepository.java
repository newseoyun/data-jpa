package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String name, int age);

    List<Member> findTop3HelloBy();

    //@Query(name = "Member.findByUsername") 메소드 이름이 기본 형식에 맞아서 여길 생략해도 잘 되긴 함.
    List<Member> findByUsername(@Param("username") String username);

    // 실무에서 많이 사용하는 방식
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto(); // 쿼리에 new 오퍼레이션 꼭 써야함

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);


    List<Member> findListByUsername(String username); // 컬렉션
    Member findMemberByUsername(String name); // 단건
    Optional<Member> findOptionalByUsername(String username); // 단건 Optional


    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);
    // count는 left조인이 필요없으니까 성능을 위해 단순한 쿼리를 따로 지정
    // 실무 상 sort by로 풀기 어려운 복잡한 정렬도 여기다가 정의해주는게 좋다.


    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);
    // 벌크 연산은 영속성 컨텍스트를 무시하기 때문에 주의해서 사용해야 함.
    // 벌크 연산 후 엔티티매니저로 clear 하면서 사용해야 함. @Modifying에 옵션으로 줄 수 있음.
    // JPA는 DB 연산 후 자동으로 flush()를 수행하므로 flush()는 안해도 됨. (JPA 상속 안하고 JPQL 쓰면 flush 해줘야함)


    // fetch 옵션을 주면 한방 쿼리로 team까지 가져온다.
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();


    // Spring Data의 EntityGraph

    /*
    // EntityGraph를 이렇게 오버라이드 해서 쓸 수도 있음. 편리함. 테스트 때문에 주석
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();
    */

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
//    @EntityGraph("Member.all")
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    // 위 EntityGraph 쓴 애들은 모두 한방쿼리로 team까지 가져옴.
    // 단순한데에 쓰기엔 짱이지만 복잡한 쿼리는 JPQL fetch join을 사용하는 것이 나음


    // JPA는 단순 조회만 하고 말 때에도 영속성컨텍스트에 조회한 데이터들을 저장(스냅샷)해놓으므로 메모리를 차지하게 됨.
    // 하이버네이트에는 단순한 조회값은 비워주는 기능이 있어서(readOnly) JPA의 QueryHints로 그걸 불러다 씀.
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String name);
    // 암달의 법칙을 생각할 것.
    // 이 성능 개선에 매달리지 말고 다른 중요한 것에 집중하는 것이 전체적인 성능에 더 도움이 될 것.



    // select for update (일명 비관적인 락) 매우 깊은 내용이기 때문에 이런게 있다 정도만.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);


}
