package com.githhub.damola.server.dto;


import java.util.Map;

public record ValidationError(Map<String, String> errors) {
}
