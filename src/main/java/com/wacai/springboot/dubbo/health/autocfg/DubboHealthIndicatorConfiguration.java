package com.wacai.springboot.dubbo.health.autocfg;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.rpc.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnClass(name = {"com.alibaba.dubbo.rpc.Exporter"})
public class DubboHealthIndicatorConfiguration {
    @Autowired
    HealthAggregator healthAggregator;

    @Autowired(required = false)
    Map<String, ReferenceBean> references;

    @Bean
    public HealthIndicator dubboHealthIndicator() {

        Map<String, HealthIndicator> indicators = new HashMap<>();

        for (String key : references.keySet()) {
            final ReferenceBean bean = references.get(key);
            indicators.put(key.startsWith("&") ? key.replaceFirst("&", "") : key, new DubboHealthIndicator(bean));
        }

        return new CompositeHealthIndicator(healthAggregator, indicators);
    }

    private static class DubboHealthIndicator extends AbstractHealthIndicator {
        private final ReferenceBean bean;

        public DubboHealthIndicator(ReferenceBean bean) {
            this.bean = bean;
        }

        @Override
        protected void doHealthCheck(Health.Builder builder) throws Exception {
            builder.withDetail("interface", bean.getObjectType());
            final EchoService service = (EchoService) bean.getObject();
            service.$echo("hi");
            builder.up();
        }
    }
}
