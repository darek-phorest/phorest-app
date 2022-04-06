package pl.szymonmilczarek.phorestapp.util


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.Network
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

import java.util.function.Supplier

class PhorestAppContainerConfig extends PhorestAppRestConfig {

    @Autowired
    public TestRestTemplate template

    static final PostgreSQLContainer<?> postgres

    static final Logger logger = LoggerFactory.getLogger(PhorestAppContainerConfig.class)

    static {
        Network network = Network.newNetwork()

        // 👏 great to see docker being used for IT tests
        postgres = new PostgreSQLContainer<>("postgres:latest")
                .withUsername("phorest-app")
                .withPassword("phorest-app")
                .withDatabaseName("phorest-app")
                .withNetworkAliases("phorest-app-postgres")
                .withLogConsumer(new Slf4jLogConsumer(logger))
                .waitingFor(Wait.forLogMessage(".*database system is ready to accept connections.*\\s", 1))
                .withNetwork(network)
        postgres.start()
    }

    @DynamicPropertySource
    static void initializeDatasource(DynamicPropertyRegistry registry) {
        String dbUrl = String.format(POSTGRES_URL_BASE_TEMPLATE, postgres.getHost(), postgres.getLivenessCheckPortNumbers().iterator().next())
        registry.add("spring.datasource.url", { dbUrl } as Supplier)
    }
}
