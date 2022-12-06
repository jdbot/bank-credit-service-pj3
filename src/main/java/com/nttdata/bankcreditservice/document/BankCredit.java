package com.nttdata.bankcreditservice.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * Bank Credit Document.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bank-credits")
public class BankCredit {

    @Id
    private String id;
    //number credit of the bank credit
    private String numberCredit;
    //amount of the bank credit
    private Float amount;
    //credit
    private Float credit;
    //start date of the bank credit
    private String startDate;
    //end date of the bank credit
    private String endDate;
    //payment date of the bank credit
    private String paymentDate;
    //id of the client
    private String customerId;
    //type of the bank credit
    private String type;
    //creation date of the bank account
    private String creationDate;
}
