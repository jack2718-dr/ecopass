package com.apsol.ecopass.entity;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 사진
 */
@Entity
@Table(name = "photos")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class Photo {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long code;

    /**
     * 파일 이름
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 확장자
     */
    @Column(name = "content_type", nullable = false)
    private String contentType = "";

    /**
     * 데이터
     */
    @Column(name = "bytes", nullable = false)
    @Lob
    private byte [] bytes;

}
