package store.ppingpong.board.common;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private EntityStatus entityStatus;

    public void setEntityStatusWhenCreate(EntityStatus entityStatus) {
        this.entityStatus = entityStatus;
    }
}
