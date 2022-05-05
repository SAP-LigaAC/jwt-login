package sap.ligaac.jwtproxy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data                   //Generates methods for hash, equals and get/set for each member
@Builder                //Generates builder (TokenInfoContract.builder().<...>.build())
@NoArgsConstructor      //Generates simple constructor with no arguments
@AllArgsConstructor     //Generates constructor with arguments for every member
public class TokenInfoContract {
    private String grantType;
    private String clientId;
    private String subaccountId;
    private String subdomain;
    private String logonName;
    private String familyName;
    private String givenName;
    private String email;
    private Collection<String> authorities;
    private Collection<String> scopes;
}
