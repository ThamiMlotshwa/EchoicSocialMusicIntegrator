package echoic.linkgenerator.config;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import echoic.linkgenerator.core.SpotifySearcherConverter;
import echoic.linkgenerator.core.unittests.AgnosticLinkGenerator;
//import echoic.linkgenerator.core.AppleAgnosticLinkGenerator;
import echoic.linkgenerator.core.SpotifyAgnosticLinkGenerator;
import echoic.linkgenerator.core.UrlTracker;
import echoic.linkgenerator.core.unittests.ExternalAgnosticMusicEntityGenerator;
import echoic.linkgenerator.core.unittests.ExternalTrackedUrlGenerator;
import echoic.linkgenerator.core.unittests.SearcherConverter;
import echoic.linkgenerator.external.linktracking.TinyCcExternalTrackedUrlGenerator;
import echoic.linkgenerator.external.musicentity.SonglinkExtAgnosticMusicEntityGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;

@Slf4j
@Configuration
@EnableScheduling
public class LinkGeneratorConfig
{
    @Value("${spotify.clientApi.id}")
    private String spotifyClientId;

    @Value("${spotify.clientApi.secret}")
    private String spotifyClientSecret;

    @Value("${tinycc.user}")
    private String tinyCcUser;

    @Value("${tinycc.key}")
    private String tinyCcKey;


    @Bean
    public SpotifyApi spotifyApi() throws IOException, SpotifyWebApiException, ParseException {
        SpotifyApi spotifyApi =  new SpotifyApi.Builder()
                            .setClientId(spotifyClientId)
                            .setClientSecret(spotifyClientSecret)
                            .setRedirectUri(URI.create("http://localhost:8081/spotify"))
                            .build();

        try {
            ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                    .build();
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            return spotifyApi;
        }
        catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e)
        {
            log.info(e.toString());
            throw e;
        }
    }

    @Bean
    public ClientCredentialsRequest clientCredentialsRequest(SpotifyApi spotifyApi)
    {
        return spotifyApi.clientCredentials().build();
    }

    @Bean
    public AgnosticLinkGenerator agnosticLinkGenerator(SpotifyApi spotifyApi,
                                                       RestTemplate restTemplate,
                                                       SearcherConverter searcherConverter,
                                                       ExternalAgnosticMusicEntityGenerator externalAgnosticMusicEntityGenerator,
                                                       ExternalTrackedUrlGenerator externalTrackedUrlGenerator)
    {
        return new SpotifyAgnosticLinkGenerator(spotifyApi, restTemplate, searcherConverter,
                externalAgnosticMusicEntityGenerator, externalTrackedUrlGenerator);
    }

    @Bean
    public SearcherConverter searcherConverter(SpotifyApi spotifyApi){
        return new SpotifySearcherConverter(spotifyApi);
    }

    @Bean
    public ExternalAgnosticMusicEntityGenerator agnosticMusicEntityGenerator(RestTemplate restTemplate)
    {
        return new SonglinkExtAgnosticMusicEntityGenerator(restTemplate);
    }

    @Bean
    public ExternalTrackedUrlGenerator externalTrackedUrlGenerator(RestTemplate restTemplate)
    {
        String base64Creds = Base64.getEncoder().encodeToString((tinyCcUser + ":" + tinyCcKey).getBytes());
        return new TinyCcExternalTrackedUrlGenerator(restTemplate, base64Creds);
    }

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplateBuilder().build();
    }

    /*@Bean
    public AgnosticLinkGenerator specificGenerator()
    {
        return new AppleAgnosticLinkGenerator(restTemplate());
    }*/



    /*@Bean
    public UrlTracker trackedUrl(RestTemplate restTemplate)
    {
        return new UrlTracker(restTemplate);
    }*/

}
