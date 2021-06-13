package study.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass // 상속관계에서 컬럼을 내리는
@Getter @Setter
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @PrePersist // 영속화 하기 전에 동작
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate // 수정 하기 전에 동작작
    public void PreUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
