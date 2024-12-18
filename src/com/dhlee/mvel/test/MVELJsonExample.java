package com.dhlee.mvel.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mvel2.MVEL;

import java.util.HashMap;
import java.util.Map;

public class MVELJsonExample {

    public static void main(String[] args) throws Exception {
        // JSON 문자열 예시
        String jsonString = "{\"name\": \"John\", \"age\": 30}";

        // JSON을 Map으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(jsonString, Map.class);

        // MVEL 스크립트 정의: 변수 값을 변경하는 함수
        String script =
            "function modifyUserInfo(jsonData) {" +
            "    jsonData.age = 32;" + 
            "    jsonData.name = 'Jane';" +
            "    return jsonData;" +        // 수정된 jsonData를 리턴
            "}";

        // MVEL에서 사용할 변수 등록
        Map<String, Object> variables = new HashMap<>();
        variables.put("jsonData", jsonMap);

        // MVEL 스크립트 실행: 함수 정의
        MVEL.eval(script, variables);

        // 함수 호출 및 결과 저장
        Object result = MVEL.eval("modifyUserInfo(jsonData)", variables);
        
        // 결과를 Map으로 캐스팅 후 출력
        if (result instanceof Map) {
            Map<String, Object> modifiedResult = (Map<String, Object>) result;
            System.out.println("Modified JSON: " + modifiedResult);
        } else {
            System.out.println("Unexpected result type: " + result.getClass() +" - " + result);
        }
    }
}
