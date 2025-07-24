package com.spring.database.chap02.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceInfo {

    private int totalPrice;
    private double averagePrice;
}
