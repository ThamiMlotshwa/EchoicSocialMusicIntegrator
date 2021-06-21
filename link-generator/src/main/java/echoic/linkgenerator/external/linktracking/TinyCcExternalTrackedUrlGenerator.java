package echoic.linkgenerator.external.linktracking;

import echoic.linkgenerator.core.unittests.ExternalTrackedUrlGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
public class TinyCcExternalTrackedUrlGenerator implements ExternalTrackedUrlGenerator
{
    public static final String HOST = "https://tiny.cc/tiny/api/3/";
    public static final String HOST_NAME = "TinyCC";


    private RestTemplate restTemplate;
    private String credentials;

    public TinyCcExternalTrackedUrlGenerator(RestTemplate restTemplate, String credentials)
    {
        this.restTemplate = restTemplate;
        this.credentials = credentials;
    }

    @Override
    public Optional<TrackedUrl> getTrackedUrl(String url) {

        TrackedUrl trackedUrl = null;

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Basic " + credentials);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(getJsonPostBody(url), headers);
        ResponseEntity<TrackedUrlHeader> responseEntity = restTemplate.postForEntity(HOST +"urls",
                request,
                TrackedUrlHeader.class);
        if (responseEntity.hasBody() && responseEntity.getStatusCodeValue() == HttpStatus.OK.value())
        {
            TrackedUrlHeader trackedUrlHeader = responseEntity.getBody();
            TrackedUrl temp = trackedUrlHeader.getUrls().get(0);
            if (temp.linkError.getCode() == 0)
                trackedUrl = trackedUrlHeader.getUrls().get(0);
            else
                {
                log.info("Error with generating TinyCC TrackedLink [code :" + temp.linkError.getCode() +
                        ", message:  " + temp.linkError.getMessage() +
                        ", details: " + temp.linkError.details + "]");
            }
        }

        return Optional.ofNullable(trackedUrl);
    }

    @Override
    public String getGeneratorHost() {
        return null;
    }

    private String getJsonPostBody(String url)
    {
        return "{\"urls\": [{\"long_url\": \"" + url + "\"}]}";
    }
}
