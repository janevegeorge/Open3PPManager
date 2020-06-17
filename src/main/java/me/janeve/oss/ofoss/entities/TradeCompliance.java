package me.janeve.oss.ofoss.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class TradeCompliance {
    private String countryCode;
    private String euECCN;
    private String usECCN;
    private String bisAuthorization;
    private String encryptionProtocol;
}