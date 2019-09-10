package com.townmc.boot.utils;

import org.slf4j.MDC;

public class MDCUtil {

    public static void initLogId() {
        MDC.clear();
        String logId = IdCreator.createId();
        MDC.put("LOG_ID", logId);
    }

    public static String getLogId() {
        return MDC.get("LOG_ID");
    }
}
