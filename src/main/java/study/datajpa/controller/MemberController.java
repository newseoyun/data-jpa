package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }


    // 도메인 클래스 컨버터. 스프링이 바로 인젝션 처리해줌. 간단한 조회용으로만 사용.
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }


    // 페이징과 정렬. 엔티티를 바로 반환하지 말고 DTO 를 사용할 것.
    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size=5) Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(MemberDto::new);
    }


    @PostConstruct
    public void init() {
        for (int i=0; i<100; i++) {
            memberRepository.save(new Member("userA"+i, i));
        }

    }
}
