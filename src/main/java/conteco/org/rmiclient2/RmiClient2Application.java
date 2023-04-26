package conteco.org.rmiclient2;

import conteco.org.rmiclient2.calculator.Calculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@SpringBootApplication
public class RmiClient2Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RmiClient2Application.class, args);

        Calculator calculator = context.getBean(Calculator.class);

        System.out.println(calculator.add(1, 2));
    }

    //@Bean
    RmiProxyFactoryBean rmiProxy(
            @Value("${rmi.service.name}") String serviceName,
            @Value("${rmi.service.port}") int servicePort
    ) {
        RmiProxyFactoryBean bean = new RmiProxyFactoryBean();
        bean.setServiceInterface(Calculator.class);
        bean.setServiceUrl("rmi://localhost:"+servicePort+"/"+serviceName);
        return bean;
    }

    @Bean
    public HttpInvokerProxyFactoryBean invoker() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8080/calculator");
        invoker.setServiceInterface(Calculator.class);
        return invoker;
    }
}
