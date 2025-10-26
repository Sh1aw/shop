package ru.innotech.discountapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.innotech.discountapi.repository.DiscountRepository;

@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = {DiscountApiApplication.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {
    private static final String DATABASE_NAME = "discount";

    @Autowired
    protected MockMvc mockMvc;
    @MockitoSpyBean
    protected DiscountRepository discountRepository;
    @Autowired
    protected CacheManager cacheManager;
    @Autowired
    protected ObjectMapper objectMapper;

    @Container
    public static PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:12.3")
            .withReuse(true)
            .withUsername("sa")
            .withPassword("sa")
            .withDatabaseName(DATABASE_NAME);

    @Container
    public static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:8.0-alpine"))
            .withExposedPorts(6379);

    @DynamicPropertySource
    private static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", redis::getFirstMappedPort);
        registry.add("spring.cache.type", () -> "redis");
    }

    @BeforeEach
    public void init() {
        var cacheNames = cacheManager.getCacheNames();
        for (var cache : cacheNames) {
            var cacheObj = cacheManager.getCache(cache);
            if (cacheObj != null) {
                cacheObj.clear();
            }
        }
        discountRepository.deleteAll();
    }
}
