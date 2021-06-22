package echoic.socialscouttwitter.core;

import echoic.socialscouttwitter.core.interfaces.MusicEntityGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class MusicEntityGeneratorImpl implements MusicEntityGenerator
{

    private RestTemplate restTemplate;
    private String linkGeneratorUrl;

    public MusicEntityGeneratorImpl(RestTemplate restTemplate, String linkGeneratorUrl)
    {
        this.restTemplate = restTemplate;
        this.linkGeneratorUrl = linkGeneratorUrl;
    }

    @Override
    public Optional<MusicEntity> getMusicEntity(String searchTerm)
    {
        MusicEntity musicEntity = null;

        Map<String, String> urlVars = new HashMap<>();
        urlVars.put("searchTerm", searchTerm);
        try {
            ResponseEntity<MusicEntity> response = restTemplate.getForEntity("http://localhost:8081/returnLinks/{searchTerm}",
                    MusicEntity.class, urlVars);

            if (response.hasBody() && response.getStatusCodeValue() == HttpStatus.OK.value())
            {
                musicEntity = response.getBody();
            }
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
            throw e;
        }
        return Optional.ofNullable(musicEntity);
    }
}
