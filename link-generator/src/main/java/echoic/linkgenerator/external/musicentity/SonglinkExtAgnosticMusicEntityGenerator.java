package echoic.linkgenerator.external.musicentity;

import echoic.linkgenerator.core.unittests.ExternalAgnosticMusicEntityGenerator;
import echoic.linkgenerator.external.ExternalAgnosticMusicEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class SonglinkExtAgnosticMusicEntityGenerator implements ExternalAgnosticMusicEntityGenerator
{
    public static final String HOST = "https://api.song.link/v1-alpha.1";
    public static final String HOST_NAME = "Songlink";

    private RestTemplate restTemplate;

    public SonglinkExtAgnosticMusicEntityGenerator(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }


    @Override
    public Optional<ExternalAgnosticMusicEntity> getAgnosticMusicEntity(String url) {

        ExternalAgnosticMusicEntity externalAgnosticMusicEntity = null;

        ResponseEntity<ExternalAgnosticMusicEntity> responseEntity = restTemplate.getForEntity("https://api.song.link/v1-alpha.1/links?url={link}",
                ExternalAgnosticMusicEntity.class,
                url);
        if (responseEntity.hasBody() && responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
            externalAgnosticMusicEntity = responseEntity.getBody();
        }

        return Optional.ofNullable(externalAgnosticMusicEntity);
    }

    @Override
    public String getGeneratorHost() {
        return HOST_NAME;
    }
}
