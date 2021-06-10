package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    @Transactional
    public void testMember() {
        Member member =new Member("memberA");
        Member save = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.find(member.getId());

        assertThat(member.getId()).isEqualTo(findMember.getId());
        assertThat(member).isEqualTo(findMember);
    }
}