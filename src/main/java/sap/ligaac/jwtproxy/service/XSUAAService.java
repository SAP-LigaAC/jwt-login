package sap.ligaac.jwtproxy.service;

import com.sap.cloud.security.xsuaa.client.OAuth2TokenResponse;
import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.cloud.security.xsuaa.tokenflows.TokenFlowException;
import com.sap.cloud.security.xsuaa.tokenflows.XsuaaTokenFlows;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import sap.ligaac.jwtproxy.dto.JwtRequestContract;
import sap.ligaac.jwtproxy.dto.JwtResponseContract;
import sap.ligaac.jwtproxy.dto.TokenInfoContract;

import java.util.stream.Collectors;

@Log4j2
@Service
public class XSUAAService {

    private XsuaaTokenFlows tokenFlows;

    @Autowired
    public XSUAAService(XsuaaTokenFlows tokenFlows) {
        this.tokenFlows = tokenFlows;
    }

    public TokenInfoContract getUserInfo(@NonNull Token token) {
        return TokenInfoContract.builder()
                .grantType(token.getGrantType())
                .clientId(token.getClientId())
                .subaccountId(token.getSubaccountId())
                .subdomain(token.getSubdomain())
                .logonName(token.getLogonName())
                .familyName(token.getFamilyName())
                .givenName(token.getGivenName())
                .email(token.getEmail())
                .authorities(token.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .scopes(token.getScopes())
                .build();
    }

    public JwtResponseContract createJwt(JwtRequestContract requestContract) throws TokenFlowException {
        OAuth2TokenResponse response = tokenFlows.passwordTokenFlow()
                .username(requestContract.getUsername())
                .password(requestContract.getPassword())
                .subdomain(requestContract.getSubdomain())
                .execute();

        log.info("Created a new token for user {} and subdomain {}.", requestContract.getUsername(), requestContract.getSubdomain());

        return JwtResponseContract.builder()
                .access_token(response.getAccessToken())
                .build();
    }
}
