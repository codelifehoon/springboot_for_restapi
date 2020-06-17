package com.aws.atf.domain.order.payload;

import java.util.Date;
import java.util.List;

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
public class Order {

    String  ordNo;
    String  userNm;
    Date    ordDt;

    List<OrderDetail> orderDetail;

}
