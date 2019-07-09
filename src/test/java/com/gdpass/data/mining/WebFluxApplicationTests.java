package com.gdpass.data.mining;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class WebFluxApplicationTests {
    @Autowired
    private WebTestClient client;

    @Test
	public void contextLoads() {

    }

    @Test
    @WithUserDetails("test")
    public void testCollectionReport() {
        client.get().uri("/collection/year-rpt")
            .exchange().expectStatus().is4xxClientError()
            .expectBody().equals("年份参数不能为空，参数格式为：yyyy-MM-dd");
        client.get().uri("/collection/year-rpt?year=2018-01-01")
            .exchange().expectStatus().isOk();
    }
}