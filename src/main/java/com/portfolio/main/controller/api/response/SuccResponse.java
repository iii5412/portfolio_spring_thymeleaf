package com.portfolio.main.controller.api.response;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SuccResponse {
    private final Map<String, String> data;

    public SuccResponse() {
        data = new HashMap<>();
    }

    public SuccResponse(Map<String, String> data) {
        this.data = data;
    }
}
