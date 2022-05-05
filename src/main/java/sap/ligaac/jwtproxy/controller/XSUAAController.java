package sap.ligaac.jwtproxy.controller;


import com.sap.cloud.security.xsuaa.token.Token;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sap.ligaac.jwtproxy.dto.JwtRequestContract;
import sap.ligaac.jwtproxy.dto.JwtResponseContract;
import sap.ligaac.jwtproxy.dto.TokenInfoContract;
import sap.ligaac.jwtproxy.service.XSUAAService;

import javax.xml.ws.WebServiceException;
import java.util.Objects;

@Log4j2
@RestController
public class XSUAAController {
    private XSUAAService xsuaaService;

    @Autowired
    public XSUAAController(XSUAAService xsuaaService) {
        this.xsuaaService = xsuaaService;
    }

    @GetMapping("/xsuaa/tokenInfo")
    public TokenInfoContract getTokenInfo(@AuthenticationPrincipal Token token) {
        try {
            if (Objects.nonNull(token))
                return xsuaaService.getUserInfo(token);
            else throw new WebServiceException("The Token provided is null");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The application was unable to handle your request due to: " + e.getMessage(), e);
        }
    }

    @PostMapping("/xsuaa/getToken")
    public JwtResponseContract createJwt(@RequestBody JwtRequestContract requestContract) {
        try {
            return xsuaaService.createJwt(requestContract);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The application was unable to handle your request due to: " + e.getMessage(), e);
        }
    }
}
