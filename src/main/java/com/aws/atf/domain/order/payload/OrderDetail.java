package com.aws.atf.domain.order.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    String prdNo;
    String sellerNm;
    String prdPrice;

}
