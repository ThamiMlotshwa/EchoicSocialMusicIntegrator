package echoic.linkgenerator.core;

import echoic.linkgenerator.external.linktracking.TrackedUrl;
import echoic.linkgenerator.external.linktracking.TrackedUrlHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

@Component
public class UrlTracker
{
    private RestTemplate restTemplate;

    public UrlTracker(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Scheduled(fixedRate = 300000, initialDelay = 30000)
    public void updateClicks()
    {
        HttpHeaders headers = new HttpHeaders();

        String base64Creds = Base64.getEncoder().encodeToString("thami:1280acc1-86b5-4bda-99bc-4a49dbb77813".getBytes());
        headers.set("Authorization", "Basic "+base64Creds);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<TrackedUrlHeader> trackedUrlResponse = restTemplate.exchange("https://tiny.cc/tiny/api/3/urls",
                HttpMethod.GET,
                request,
                TrackedUrlHeader.class);
        TrackedUrlHeader trackedUrlHeader = trackedUrlResponse.getBody();
        List<TrackedUrl> trackedUrls = trackedUrlHeader.getUrls();
    }
}
