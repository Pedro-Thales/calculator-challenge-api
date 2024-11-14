package com.pedrovisk.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "random-api", url = "${random-api.url}")
public interface RandomApi {

    @GetMapping("/strings/?num=1&len=16&digits=on&upperalpha=on&loweralpha=on&unique=on&format=plain&rnd=new")
    String generateRandomString();

}
