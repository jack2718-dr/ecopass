package com.apsol.ecopass.entity.bulky;

import com.apsol.ecopass.annotations.TwoWayEncryption;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 대형폐기물 품목
 */
@Entity
@Table(name = "bulky_items")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class BulkyItem {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    /**
     * 대형폐기물 분류 FK
     */
    @ManyToOne
    @JoinColumn(name = "bulky_category", nullable = false, insertable = false, updatable = false)
    @NotNull(message = "분류는 null일수 없습니다.")
    private BulkyCategory bulkyCategory;

    /**
     * 품목명
     */
    @Column(name = "name", nullable = false)
    @NotNull(message = "품목명은 필수항목 입니다.")
    @TwoWayEncryption///////////////////////////////////////////////////////////////
    private String name;

    /**
     * 규격
     */
    @Column(name = "standard", nullable = false)
    @NotNull(message = "규격은 필수항목 입니다.")
    private String standard;

    /**
     * 수수료
     */
    @Column(name = "price", nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    /**
     * 삭제 유무
     */
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

}
