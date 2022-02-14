package br.ead.home;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.LoggingListener;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

@Log4j2
public class IntegrationTestRunner {

    public static void main(String[] args) {
        log.info("Running Integration Tests ...");
        SummaryGeneratingListener summaryListener = new SummaryGeneratingListener();
        LoggingListener loggingListener = LoggingListener.forJavaUtilLogging();

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectPackage("br.ead.home.specification"))
                .filters(includeClassNamePatterns(".*Test"))
                .build();

        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(summaryListener, loggingListener);
        launcher.execute(request);

        PrintWriter logger = IoBuilder.forLogger(IntegrationTestRunner.class)
                .setLevel(Level.INFO)
                .setCharset(StandardCharsets.UTF_8)
                .setBufferSize(1000)
                .buildPrintWriter();

        TestExecutionSummary summary = summaryListener.getSummary();
        summary.printTo(logger);
        summary.printFailuresTo(logger);

        TestPlan testPlan = launcher.discover(request);
        loggingListener.testPlanExecutionFinished(testPlan);

        int exitCode = isSuccessful(summary) ? 0 : 1;
        System.exit(exitCode);
    }

    private static boolean isSuccessful(TestExecutionSummary summary) {
        return summary.getTestsFailedCount() == 0 && summary.getTestsSkippedCount() < 10;
    }

}
