package com.apsol.ecopass.entity.bulky;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 대형폐기물 분류
 */
@Entity
@Table(name = "bulky_categories")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class BulkyCategory {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long code;

    /**
     * 분류명
     */
    @Column(name = "name", nullable = false)
    @NotNull(message = "분류명은 필수항목 입니다.")
    @Setter(AccessLevel.NONE)
    private String name;

    /**
     * 삭제 유무
     */
    @Column(name = "deleted", nullable = false)
    @Setter(AccessLevel.NONE)
    private boolean deleted = false;

}
