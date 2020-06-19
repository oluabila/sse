package com.universitysys.SSE.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModuleNotFound extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public String ModuleNotFound() {
        return ("Student does not exist");
    }

}