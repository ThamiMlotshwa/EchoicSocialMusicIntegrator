package echoic.linkgenerator;

import be.ceau.itunesapi.Search;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@Slf4j
@Configuration
public class LinkGeneratorConfig
{
    @Value("${spotify.clientApi.id}")
    private String spotifyClientId;

    @Value("${spotify.clientApi.secret}")
    private String spotifyClientSecret;

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

    /*@Bean
    public SpecificGenerator specificGenerator(SpotifyApi spotifyApi)
    {
        return new SpotifySpecificGenerator(spotifyApi);
    }*/

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplateBuilder().build();
    }

    @Bean
    public SpecificGenerator specificGenerator()
    {
        return new AppleSpecificGenerator(restTemplate());
    }
}
