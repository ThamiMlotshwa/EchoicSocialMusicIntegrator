package echoic.socialscouttwitter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
@Slf4j
@Configuration
public class SocialScoutConfig
{
    @Value("${twitter.consumerapi.key}")
    private String apiKey;
    @Value("${twitter.consumerapi.secretKey}")
    private String apiSecretKey;
    @Value("${twitter.oauthaccess.token}")
    private String accessToken;
    @Value("${twitter.oauthaccess.tokenSecret}")
    private String accessTokenSecret;
    @Value("${twitter.listenerFlag}")
    private String listenerFlag;

    @Bean
    public Twitter twitter()
    {
        var configurationBuilder =  new ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey(this.apiKey)
                .setOAuthConsumerSecret(this.apiSecretKey)
                .setOAuthAccessToken(this.accessToken)
                .setOAuthAccessTokenSecret(this.accessTokenSecret);

        return new TwitterFactory(configurationBuilder.build()).getInstance();
    }

    @Bean
    public TwitterStream twitterStream()
    {
        var configurationBuilder =  new ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey(this.apiKey)
                .setOAuthConsumerSecret(this.apiSecretKey)
                .setOAuthAccessToken(this.accessToken)
                .setOAuthAccessTokenSecret(this.accessTokenSecret);

        var twitterStream = new TwitterStreamFactory(
                configurationBuilder.build()).getInstance();
        return twitterStream;
    }

    @Bean
    public FilterQuery filterQuery()
    {
        return  new FilterQuery()
                    .track("#"+ this.listenerFlag);
    }

    @Bean
    Tweeter tweeter(Twitter twitter)
    {
        return new Tweeter(twitter);
    }
}