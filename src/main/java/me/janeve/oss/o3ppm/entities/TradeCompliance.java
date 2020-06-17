package me.janeve.oss.o3ppm.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Getter @Setter
@ToString
public class TradeCompliance {
    @Pattern(regexp = "^[a-zA-Z]{2}$") private String countryCode;
    private String euECCN;
    private String usECCN;
    private String bisAuthorization;
    private String encryptionProtocol;
}