package com.spribe.yablonskyi.listeners;

import com.spribe.yablonskyi.base.BaseTest;
import com.spribe.yablonskyi.util.CustomLogger;
import com.spribe.yablonskyi.util.CustomLogger.LogLevel;
import com.spribe.yablonskyi.util.TimeUtil;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.*;

import java.time.LocalTime;
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
        String methodName = result.getMethod().getMethodName();
        if (BaseTest.getRerun()) {
            log.logHeader("RETRYING: " + methodName, LogLevel.INFO);
        } else {
            log.logHeader("STARTED: " + methodName, LogLevel.INFO);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        if (BaseTest.getRerun()) {
            log.logHeader("PASSED IN RETRY: " + methodName, LogLevel.INFO);
        } else {
            log.logHeader("PASSED: " + methodName, LogLevel.INFO);
        }
        BaseTest.setRerun(false);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        if (BaseTest.getRerun()) {
            log.logHeader("FAILED IN RETRY: " + methodName, LogLevel.WARN);
        } else {
            log.logHeader("FAILED: " + methodName, LogLevel.ERROR);
            Throwable throwable = result.getThrowable();
            if (!Objects.isNull(throwable)) {
                log.error("ERROR: ", throwable);
            }
        }
        BaseTest.setRerun(false);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        log.logHeader("SKIPPED: " + methodName, LogLevel.WARN);
        BaseTest.setRerun(false);
    }


}
