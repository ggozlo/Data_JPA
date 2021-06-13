package study.datajpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class) // 엔티티 이벤트 구현 클래스
public class BaseEntity extends  BaseTimeEntity {



    @CreatedBy // 생성자 기록, 생성자는 Bean으로 등록된 AudotorAware 인스턴스에서 받아옴
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy // 수정자 기록
    private String lastModifiedBy;
}
