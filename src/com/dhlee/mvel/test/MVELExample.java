package com.dhlee.mvel.test;

import org.mvel2.MVEL;

public class MVELExample {
    public static void main(String[] args) {
        // MVEL 스크립트 정의
        String expression = "concat(str1, str2)";

        // concat 함수 정의
        String script = 
            "function concat(str1, str2) {" +
            "    System.out.println( str1 + \" \"+ str2);" +
            "    return str1 + str2;" +
            "}";

        // MVEL 환경 설정 (기본 MVEL 환경 사용)
        java.util.Map<String, Object> variables = new java.util.HashMap<>();
        variables.put("str1", "Hello, ");
        variables.put("str2", "World!");

        // MVEL 스크립트를 실행하고 결과 얻기
        MVEL.eval(script, variables);  // concat 함수 정의 실행
        Object result = MVEL.eval(expression, variables);  // concat 함수 호출

        // 결과 출력
        System.out.println(result);  // 출력: Hello, World!
    }
}
