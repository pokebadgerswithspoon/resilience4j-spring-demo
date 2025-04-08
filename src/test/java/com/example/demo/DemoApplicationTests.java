package com.example.demo;

import com.example.demo.feign.DemoFeignClient;
import feign.Client;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.ConnectException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class DemoApplicationTests {

    @MockitoBean
    private Client feignClient;

    @Test
    void contextLoads() {
    }

    @Test
    void feignWorks(
        @Autowired DemoFeignClient demo
    ) throws IOException {

        doThrow(new ConnectException("Oops"))
            .doThrow(new ConnectException("Oh no, not again"))
            .doAnswer(inv -> {
               Request request = inv.getArgument(0);
               return Response.builder()
                   .request(request)
                   .status(200)
                   .body("Done".getBytes(StandardCharsets.UTF_8))
                   .build();
                })
            .when(feignClient)
            .execute(any(), any());


        String result = demo.ping();

        assertThat(result)
            .isEqualTo("Done");

        verify(feignClient, times(3)).execute(any(), any());


    }

}
