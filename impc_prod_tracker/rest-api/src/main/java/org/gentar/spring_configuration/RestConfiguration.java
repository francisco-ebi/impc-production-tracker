package org.gentar.spring_configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import javax.servlet.Filter;

@Configuration
public class RestConfiguration
{
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder)
    {
        return builder.build();
    }

    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

    @Bean
    public HalConfiguration linkRelationBasedPolicy() {
        return new HalConfiguration()
            .withRenderSingleLinks(HalConfiguration.RenderSingleLinks.AS_ARRAY)
            .withRenderSingleLinksFor(
                LinkRelation.of("self"), HalConfiguration.RenderSingleLinks.AS_SINGLE);
    }

}
