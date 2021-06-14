package study.datajpa.repository;

public interface NestedClosedProjections {

    String getUsername();
    TeamInfo getTeam();
    // 중첩구조를 가져올때는 원 대상은 필요한 컬럼만 쿼리문을 보내지만 중첩부터는 모든 컬럼을 가져옴
    interface TeamInfo {
        String getName();
    }
}
