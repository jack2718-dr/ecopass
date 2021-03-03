package com.apsol.ecopass.dto.admin.bulky;

import com.apsol.ecopass.enums.FreeType;
import com.apsol.ecopass.enums.PayType;
import com.apsol.ecopass.dto.common.bulky.BulkyRequestDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class AdminBulkyRequestDto extends BulkyRequestDto {

    private boolean visitCollect = false;

    private boolean visitPay = false;

    private PayType payType;

    private FreeType freeType;


}
