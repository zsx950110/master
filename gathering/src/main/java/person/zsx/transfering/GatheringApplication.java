package person.zsx.transfering;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
//feign调用服务的注解
@EnableFeignClients
//断路器注解
@EnableCircuitBreaker
@EnableDistributedTransaction
@MapperScan("person.zsx.transfering.dao")
public class GatheringApplication {
    //调用服务的方式之一：使用RestTemplate和Ribbon进行服务调用
@LoadBalanced
@Bean
public RestTemplate restTemplate(){
    return new RestTemplate();
}
    public static void main(String[] args) {
        SpringApplication.run(GatheringApplication.class, args);
    }

}
