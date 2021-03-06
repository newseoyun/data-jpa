package study.datajpa.repository;

import study.datajpa.entity.Member;

import java.util.List;

// 사용자 정의 리포지토리 구현 (주로 QueryDSL, JDBC Template 구현 시 사용)
// 1. 인터페이스 생성
// 2. **Impl 구현(Impl 이름은 repository 와 맞춰줘야 함. 이름 규칙을 java config 로 수정할 수 있긴 함.)
// 3. 인터페이스를 repository extents 에 넣어줌
public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
