package com.dhlee.mvel.test;

import org.mvel2.MVEL;

public class MVELExample {
    public static void main(String[] args) {
        // MVEL ��ũ��Ʈ ����
        String expression = "concat(str1, str2)";

        // concat �Լ� ����
        String script = 
            "function concat(str1, str2) {" +
            "    System.out.println( str1 + \" \"+ str2);" +
            "    return str1 + str2;" +
            "}";

        // MVEL ȯ�� ���� (�⺻ MVEL ȯ�� ���)
        java.util.Map<String, Object> variables = new java.util.HashMap<>();
        variables.put("str1", "Hello, ");
        variables.put("str2", "World!");

        // MVEL ��ũ��Ʈ�� �����ϰ� ��� ���
        MVEL.eval(script, variables);  // concat �Լ� ���� ����
        Object result = MVEL.eval(expression, variables);  // concat �Լ� ȣ��

        // ��� ���
        System.out.println(result);  // ���: Hello, World!
    }
}