package com.craftdemo.requestfilter.util;

import com.craftdemo.requestfilter.filters.CustomHeaderRequestWrapper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

import static com.craftdemo.requestfilter.util.Constants.REQUEST_HEADER_TRACE_ID;
import static com.craftdemo.requestfilter.util.Constants.THREAD_CONTEXT_TRACE_ID;
import static com.craftdemo.requestfilter.util.Constants.UNDER_SCORE;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestContext {
    public static String getTraceId() {
        Object obj = MDC.get(THREAD_CONTEXT_TRACE_ID);
        if (obj == null) {
            MDC.put(THREAD_CONTEXT_TRACE_ID, generateNewTraceId());
        }
        return MDC.get(THREAD_CONTEXT_TRACE_ID);
    }

    public static void setTraceId(String traceId) {
        MDC.put(THREAD_CONTEXT_TRACE_ID, traceId);
    }

    public static String getHeaderTracer(HttpServletRequest request) {
        return request.getHeader(REQUEST_HEADER_TRACE_ID);
    }

    public static void addHeaderTracer(CustomHeaderRequestWrapper request) {
        request.addHeader(REQUEST_HEADER_TRACE_ID, getTraceId());
    }

    public static String generateNewTraceId() {
        return UUID.randomUUID().toString().replace(UNDER_SCORE, StringUtils.EMPTY);
    }

    public static Map<String, String> getCopyOfThreadContext() {
        return MDC.getCopyOfContextMap();
    }

    public static void setMdcContext(Map<String, String> mdcContext) {
        MDC.setContextMap(mdcContext);
    }
}
