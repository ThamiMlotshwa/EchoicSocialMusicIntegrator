package echoic.linkgenerator.external.linktracking;

import echoic.linkgenerator.core.interfaces.ExternalTrackedUrlGenerator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Optional;

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

        // put this in the config
        String base64Creds = Base64.getEncoder().encodeToString("thami:1280acc1-86b5-4bda-99bc-4a49dbb77813".getBytes());

        headers.set("Authorization", "Basic "+base64Creds);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"urls\": [{\"long_url\": \"" + url + "\"}]}", headers);
        ResponseEntity<TrackedUrlHeader> responseEntity = restTemplate.postForEntity(HOST +"urls",
                request,
                TrackedUrlHeader.class);
        if (responseEntity.hasBody())
        {
            TrackedUrlHeader trackedUrlHeader = responseEntity.getBody();
            trackedUrl = trackedUrlHeader.getUrls().get(0);
        }

        return Optional.ofNullable(trackedUrl);
    }

    @Override
    public String getGeneratorHost() {
        return null;
    }
}
