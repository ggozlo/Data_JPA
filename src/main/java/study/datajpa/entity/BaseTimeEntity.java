package study.datajpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 엔티티 이벤트 구현 클래스
public class BaseTimeEntity {

    @CreatedDate // 생성시간 기록
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate // 수정시간 기록
    private LocalDateTime lastModifiedDate;
}
