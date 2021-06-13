package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void testMember() {
        System.out.println("memberRepository = " + memberRepository.getClass());
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(saveMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(saveMember.getId());
        assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member1");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member  findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(member1).isEqualTo(findMember1);
        assertThat(member2).isEqualTo(findMember2);

        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        Long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        Long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.get(0)).isEqualTo(member2);
    }

    @Test
    public void findHelloBy() {
        List<Member> helloBy = memberRepository.findTop3By();
    }

    @Test
    public void namedQuery() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsername(member1.getUsername());
        assertThat(result.get(0)).isEqualTo(member1);
    }

    @Test
    public void annotationQuery() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findUser(member1.getUsername(), member1.getAge());
        assertThat(result.get(0)).isEqualTo(member1);
    }

    @Test
    public void testQuery() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<String > result = memberRepository.findUsername();

        List<String> list = new ArrayList<>();
        list.add(member1.getUsername());
        list.add(member2.getUsername());

        assertThat(result).isEqualTo(list);
    }

    @Test
    public void findMemberDto() {
        Member member1 = new Member("AAA", 10);
        memberRepository.save(member1);

        Team team = new Team("teamA");
        teamRepository.save(team);
        member1.setTeam(team);


        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }

    @Test
    public void findByNames() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByNames(List.of(member1.getUsername(), member2.getUsername()));

        assertThat(result).isEqualTo(List.of(member1, member2));
    }

    @Test
    public void returnType() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Optional<Member> result = memberRepository.findOptionalByUsername("asdqw");
        System.out.println("list.get() = " + result );
        
    }

    @Test
    public void paging() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age =10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by("username").descending() );
        // Data Jpa 의 페이징은 0번 부터 시작
        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        //Page는 토탈카운트+페이지, Slice 는 Page+1, List는 조건에 맞는 것만
        // slice는 다음 페이지가 있는지 없는지 정도로만 페이징을 한다.
        // 통상적인 페이징은 page로

        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        // entity -> dto 로 변활할수 있는 map 메서드 제공

        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        assertThat(content.size()).isEqualTo(3);
        // 현재 페이지의 크기
        assertThat(totalElements).isEqualTo(5);
        // 전체 크기
        assertThat(page.getNumber()).isEqualTo(0);
        // 현재 페이지 번호 0번부터 시작
        assertThat(page.getTotalPages()).isEqualTo(2);
        // 전체 페이지 수
        assertThat(page.isFirst()).isTrue();
        // 현재 페이지가 첫장 인지
        assertThat(page.hasNext()).isTrue();
        // 다음 페이지가 있는지
    }

    @Test
    public void bulkUpdate() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        int resultC0unt = memberRepository.bulkAgePlus(20);
        // 벌크 연산은 영속성 컨텍스트를 무시하고 쿼리들을 flush 시켜버리기 떄문에 영속성 컨텍스트의 값과 DB의 값이 불일치 하게 될수 있다.
        // 때문에 flush 되었다면 영속성 컨텍스트를 초기화 하고 다시 db에서 값을 가져와야 동일성이 보장된다
        // 하지만 Data JPA 는 Modifying 어노테이션에서 자동 클리어 옵션을 제공한다!
//        entityManager.flush();
//        entityManager.clear();
        // 그래서 벌크연산 실행 이후 영속성 컨텍스트를 초기화 하는것이 좋다

        List<Member> result = memberRepository.findListByUsername("member5");
        Member member5 = result.get(0);
        System.out.println("member5 = " + member5);

        assertThat(resultC0unt).isEqualTo(3);
    }
    
    @Test
    public void findMemberLazy() {

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        entityManager.flush();
        entityManager.clear();

        List<Member> members = memberRepository.findEntityGraphByUsername("member1");
        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }
    }

}