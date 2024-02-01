package com.agent.common.enums;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LoginFailureEnum {
	
	BadCredentialsException("NOT_PW_EXIST"),
    UsernameNotFoundException("NOT_NM_EXIST"),
    AccountExpiredException("USER_AC_EXPIRED"),
    CredentialsExpiredException("USER_PW_EXPIRED"),
    DisabledException("USER_ACT_LOCK"),
    LockedException("USER_ACC_LOCK"),
    UNKNOWN("UNKNOWN");

    private String value;

    LoginFailureEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
    
    private static final Map<String, LoginFailureEnum> valMap = Collections.unmodifiableMap(Stream.of(values())
    																	.collect(Collectors.toMap(LoginFailureEnum::getValue, Function.identity())));

    public static LoginFailureEnum findOf(String findValue) {    	
        return Optional.ofNullable(valMap.get(findValue)).orElse(UNKNOWN);
    }
}
