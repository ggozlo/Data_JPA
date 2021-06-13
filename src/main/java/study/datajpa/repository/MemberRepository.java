package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int  age);

    List<Member> findTop3By();

//    @Query(name = "Member.findByUsername")
// 네임드 쿼리 명 사실 없어도됨 엔티티명.메서드명 으로 네임드 쿼리를 검색함 없으면 메서드 규칙으로 생성
    List<Member> findByUsername(@Param("username") String username);


}
