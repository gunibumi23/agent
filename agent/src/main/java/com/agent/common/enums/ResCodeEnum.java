package com.agent.common.enums;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ResCodeEnum {
	
	SUCCESS("SUCCESS"),
	ERROR("ERROR"),
    UNKNOWN("UNKNOWN");	

    private String value;

    ResCodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
    
    private static final Map<String, ResCodeEnum> valMap = Collections.unmodifiableMap(Stream.of(values())
    																	.collect(Collectors.toMap(ResCodeEnum::getValue, Function.identity())));

    public static ResCodeEnum findOf(String findValue) {
    	return Optional.ofNullable(valMap.get(findValue)).orElse(UNKNOWN);
    }
}
