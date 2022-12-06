package com.nttdata.bankcreditservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class TransactionDto {

    private String accountId;
    private float amount;
    private String debtId;

}
