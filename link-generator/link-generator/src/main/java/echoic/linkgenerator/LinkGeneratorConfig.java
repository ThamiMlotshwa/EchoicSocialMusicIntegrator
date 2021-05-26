package echoic.linkgenerator;

import be.ceau.itunesapi.Search;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkGeneratorConfig
{
    @Bean
    public AppleSpecificGenerator appleSpecificGenerator()
    {
        return new AppleSpecificGenerator();
    }
}
