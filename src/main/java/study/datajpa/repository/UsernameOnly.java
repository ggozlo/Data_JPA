package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {
//    String getUsername();
    // 클로즈 프로젝션
    // 인터페이스 기반의 프로젝션
    // 가져올 데이터 의 메서드만 제대로 정의하면 알아서 넣어줌

    @Value("#{target.username + ' '+ target.age}") // 오픈 프로젝션 - 지정된 타겟들을 끌어와서 메서드에 넣어줌
    String getUsername();
}
