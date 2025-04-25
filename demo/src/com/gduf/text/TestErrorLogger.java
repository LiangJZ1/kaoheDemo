package com.gduf.text;

import com.gduf.Data.ErrorLogger;

public class TestErrorLogger {
    public static void main(String[] args) {
        try {
            // 1. 测试除以零异常
            int result = 10 / 0;
        } catch (Exception e) {
            ErrorLogger.logError(e);
            System.out.println("已记录算术异常，请检查Error.txt文件");
        }

        try {
            // 2. 测试数组越界异常
            int[] arr = new int[5];
            System.out.println(arr[10]);
        } catch (Exception e) {
            ErrorLogger.logError(e);
            System.out.println("已记录数组越界异常，请检查Error.txt文件");
        }
    }
}

