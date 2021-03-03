package com.apsol.ecopass.entity.area;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddrId implements Serializable {

    private static final long serialVersionUID = -6394659884564368128L;

    private String hemd;

    private String bemd;

}
