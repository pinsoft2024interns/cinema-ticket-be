package com.pinsoft.interns.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInfoRequest {

    private String creditCardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
}
