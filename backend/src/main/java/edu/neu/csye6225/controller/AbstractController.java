package edu.neu.csye6225.controller;

import org.springframework.http.HttpHeaders;

public class AbstractController {

    public HttpHeaders getHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("content-type", "application/json; charset=utf-8");
        responseHeaders.set("Access-Control-Allow-Credentials", "true");
        //responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Cache-Control", "no-cache");
        responseHeaders.set("content-encoding", "gzip");
        responseHeaders.set("Access-Control-Allow-Headers", "X-Requested-With,Content-Type,Accept,Origin");
        return responseHeaders;
    }
}
