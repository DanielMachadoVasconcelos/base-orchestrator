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
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.DockerImageName;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

@Log4j2
public class IntegrationTestRunner {

    private static final Network network = Network.newNetwork();

    private static final ElasticsearchContainer elasticsearch = new ElasticsearchContainer(
            DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:7.14.2"))
            .withExposedPorts(9200, 9300)
            .withNetwork(network)
            .withNetworkAliases("integration-test")
            .withEnv("discovery.type", "single-node")
            .waitingFor(Wait.defaultWaitStrategy())
            .withReuse(true);

    private static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.0.0"))
            .withEmbeddedZookeeper()
            .withExposedPorts(9092, 9093)
            .withNetwork(network)
            .withNetworkAliases("integration-test")
            .waitingFor(Wait.defaultWaitStrategy())
            .withReuse(true);

    public static final GenericContainer sales = new GenericContainer(
            new ImageFromDockerfile().withDockerfile(Path.of("sales/Dockerfile")))
            .withExposedPorts(8082, 9082)
            .withReuse(true)
            .withNetwork(network)
            .withEnv("ACTIVE_PROFILE", "integration-test")
            .withNetworkAliases("integration-test")
            .waitingFor(Wait.defaultWaitStrategy());

    public static void main(String[] args) {
        log.info("Running Integration Tests ...");

        elasticsearch.start();
        log.info("Elasticsearch started..");
        kafka.start();
        log.info("Kafka started..");
        sales.start();
        log.info("Sales started..");

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
                .setLevel(Level.DEBUG)
                .setCharset(StandardCharsets.UTF_8)
                .setBufferSize(1000)
                .buildPrintWriter();

        TestExecutionSummary summary = summaryListener.getSummary();
        summary.printTo(logger);
        summary.printFailuresTo(logger);

        TestPlan testPlan = launcher.discover(request);
        loggingListener.testPlanExecutionFinished(testPlan);

        int exitCode = isSuccessful(summary) ? 0 : 1;
        log.info("Running Integration Tests exit code: {}", exitCode);
        System.exit(exitCode);
    }

    private static boolean isSuccessful(TestExecutionSummary summary) {
        return summary.getTestsFailedCount() == 0 && summary.getTestsSkippedCount() < 10;
    }

}
