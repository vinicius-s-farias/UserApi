package com.okta.springbootspa.configuration;

import lombok.Data;

@Data
public class TreinamentoDefaultException extends java.lang.Exception {

    public TreinamentoDefaultException() {super();}

    public TreinamentoDefaultException(String message) {
        super(message);
    }

}