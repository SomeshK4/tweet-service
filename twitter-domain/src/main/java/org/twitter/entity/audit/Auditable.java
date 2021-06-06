package org.twitter.entity.audit;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public abstract class Auditable {

    @CreationTimestamp
    @Column(name = "created_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "last_modified_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime lastModifiedDate;
}
