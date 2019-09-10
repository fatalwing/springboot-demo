package com.townmc.boot.web;

import lombok.Data;

@Data
public class AccessRequest {

    private String queryString;
    private String requestURI;
    private String servletPath;
    private String sessionId;

}
