package com.elice.homealone.member.service;

package com.elice.homealone.member.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NaverService {
    @Value("${naver.client.id}")
    private String clientId;
    @Value("${naver.redirect.uri}")
    private String redirectUri;
    private final String state  = UUID.randomUUID().toString();

    private
    String authorizationUrl = "https://nid.naver.com/oauth2.0/authorize?response_type=code" +
            "&client_id=" + clientId +
            "&state=" + state +
            "&redirect_uri=" + redirectUri;
}
