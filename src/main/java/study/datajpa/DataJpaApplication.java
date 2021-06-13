package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing() // Jpa 감사 기능 활성화
@SpringBootApplication
//@EnableJpaRepositories(basePackages = "study.datajpa.repository") 스프링 부트는 메인 클래스 패키지 위치로 잡아냄
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}


	@Bean
	public AuditorAware<String> auditorProvider() { // 사용자 정보를 관리하기 위한 인터페이스 보통 시큐리티 컨텍스트에서 사용자명을 꺼내서 주는데 쓰는듯?
		return () -> Optional.of(UUID.randomUUID().toString());
	}
}
