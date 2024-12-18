package com.dhlee.mvel.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mvel2.MVEL;

import java.util.HashMap;
import java.util.Map;

public class MVELJsonExample {

    public static void main(String[] args) throws Exception {
        // JSON ���ڿ� ����
        String jsonString = "{\"name\": \"John\", \"age\": 30}";

        // JSON�� Map���� ��ȯ
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(jsonString, Map.class);

        // MVEL ��ũ��Ʈ ����: ���� ���� �����ϴ� �Լ�
        String script =
            "function modifyUserInfo(jsonData) {" +
            "    jsonData.age = 32;" + 
            "    jsonData.name = 'Jane';" +
            "    return jsonData;" +        // ������ jsonData�� ����
            "}";

        // MVEL���� ����� ���� ���
        Map<String, Object> variables = new HashMap<>();
        variables.put("jsonData", jsonMap);

        // MVEL ��ũ��Ʈ ����: �Լ� ����
        MVEL.eval(script, variables);

        // �Լ� ȣ�� �� ��� ����
        Object result = MVEL.eval("modifyUserInfo(jsonData)", variables);
        
        // ����� Map���� ĳ���� �� ���
        if (result instanceof Map) {
            Map<String, Object> modifiedResult = (Map<String, Object>) result;
            System.out.println("Modified JSON: " + modifiedResult);
        } else {
            System.out.println("Unexpected result type: " + result.getClass() +" - " + result);
        }
    }
}