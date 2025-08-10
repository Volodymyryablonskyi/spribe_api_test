package com.spribe.yablonskyi.listeners;

import com.spribe.yablonskyi.util.CustomLogger;
import com.spribe.yablonskyi.util.CustomLogger.LogLevel;
import com.spribe.yablonskyi.util.TimeUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class GlobalTestListener extends AllureTestNg implements ITestListener, ISuiteListener {

    private LocalTime startSuiteTime;
    private static final CustomLogger log = CustomLogger.getLogger(GlobalTestListener.class, false);

    @Override
    public void onStart(ISuite suite) {
        startSuiteTime = TimeUtil.getCurrentTime();
        log.logHeader("[SUITE STARTED]: " + suite.getName(), LogLevel.INFO);
    }

    @Override
    public void onFinish(ISuite suite) {
        LocalTime endSuiteTime = TimeUtil.getCurrentTime();
        long totalSuiteDuration = TimeUtil.getDuration(startSuiteTime, endSuiteTime);
        log.logHeader("[SUITE FINISHED]: " + suite.getName(), LogLevel.INFO);
        log.info("[TOTAL EXECUTION TIME] " + TimeUtil.makeTimeReadable(totalSuiteDuration));
    }

    @Override
    public void onStart(ITestContext context) {
        log.logHeader("TEST CONTEXT STARTED: " + context.getName(), LogLevel.INFO);
    }

    @Override
    public void onFinish(ITestContext context) {
        log.logHeader("TEST CONTEXT FINISHED: " + context.getName(), LogLevel.INFO);
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.logHeader("STARTED: " + result.getMethod().getMethodName(), LogLevel.INFO);
        tryLogDescription(result);
        tryLogTestParameters(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.logHeader("PASSED: " + result.getMethod().getMethodName(), LogLevel.INFO);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.logHeader("FAILED: " + result.getMethod().getMethodName(), LogLevel.ERROR);
        Throwable t = result.getThrowable();
        if (!Objects.isNull(t)) {
            log.error("ERROR: ", t);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.logHeader("SKIPPED: " + result.getMethod().getMethodName(), LogLevel.WARN);
    }

    private void tryLogDescription(ITestResult result) {
        String testDescription = result.getMethod().getDescription();
        if (testDescription != null && !testDescription.isBlank()) {
            log.info("Test Description: " + testDescription);
        }
    }

    private void tryLogTestParameters(ITestResult result) {
        Object[] values = result.getParameters();
        if (values == null || values.length == 0) return;

        Method method = (result.getMethod() != null && result.getMethod().getConstructorOrMethod() != null)
                ? result.getMethod().getConstructorOrMethod().getMethod()
                : null;

        Parameter[] params = method != null ? method.getParameters() : new Parameter[0];
        Map<String, Object> kv = new LinkedHashMap<>();
        for (int i = 0; i < values.length; i++) {
            String key = (params.length == values.length) ? params[i].getName() : "arg" + i;
            String val = stringify(values[i]);
            kv.put(key, val);
            try { Allure.parameter(key, val); } catch (IllegalStateException ignored) {}
        }
        log.logParams(kv);
    }

    private String stringify(Object v) {
        if (v == null) return "null";
        try {
            var m = v.getClass().getMethod("getLogin");
            Object login = m.invoke(v);
            if (login != null) return login.toString();
        } catch (Exception ignored) {}
        return String.valueOf(v);
    }
}
