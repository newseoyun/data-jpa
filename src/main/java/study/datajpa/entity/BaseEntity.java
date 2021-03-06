package study.datajpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(AuditingEntityListener.class) // Application Class에 @EnableJpaAuditing 추가해야 함
@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity { // time 속성만 쓰는 애들도 많으니까 타임을 더 상위로 빼줌

    // Application Class 에 작성자 수정자 Auditing 주입 해야 함.
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

}
