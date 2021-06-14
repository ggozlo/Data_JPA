package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int  age);

    List<Member> findTop3By();

//    @Query(name = "Member.findByUsername")
// 네임드 쿼리 명 사실 없어도됨 엔티티명.메서드명 으로 네임드 쿼리를 검색함 없으면 메서드 규칙으로 생성
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m FROM Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsername();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select  m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username);

    Member findMemberByUsername(String username);

    Optional<Member> findOptionalByUsername(String username);

    @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m.username) from Member m")
    // 조회 쿼리가 복잡해질 경우 카운트 쿼리의 성능이 저하될수 있다. 이경우 쿼리 어노테이션으로 카운트용 쿼리를 분리할수 있다.
    Page<Member> findByAge(int age, Pageable pageable);
    // Slice, Page, List

    @Modifying(clearAutomatically = true) // .executeUpdate() 메서드랑 같은 역할
    @Query("update Member m set m.age = m.age +1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"}) // 페치조인 기능 어노테이션, 옵션에 들어간 연관관계를 페치조인 하는듯...
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m ")
    List<Member> findMemberEntityGraph();

  //  @EntityGraph(attributePaths = ("team"))
    @EntityGraph("Member.all")
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true") ) // 일기전용으로 작동함
    Member findReadByUsername(String username);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);

    <T> List<T> findProjectionsByUsername( String username, Class<T> type);
    // 프로젝션을 반환 타입에 그대로 입력, 동적으로 제네릭 응용 가능
}
