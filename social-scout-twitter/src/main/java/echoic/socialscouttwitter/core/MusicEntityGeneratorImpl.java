package echoic.socialscouttwitter.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class MusicEntityGeneratorImpl implements MusicEntityGenerator
{

    public RestTemplate restTemplate;

    public MusicEntityGeneratorImpl(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
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

            if (response.hasBody())
            {
                musicEntity = response.getBody();
            }
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(musicEntity);
    }
}
