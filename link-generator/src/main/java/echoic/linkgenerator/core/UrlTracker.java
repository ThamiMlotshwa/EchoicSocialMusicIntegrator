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

public class UrlTracker
{
    private RestTemplate restTemplate;
    private String url;
    private String credentials;

    public UrlTracker(RestTemplate restTemplate, String url, String credentials)
    {
        this.restTemplate = restTemplate;
        this.url = url;
        this.credentials = credentials;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Scheduled(fixedRate = 21600000, initialDelay = 30000) // 6 hour intervals
    public void updateClicks()
    {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Basic "+credentials);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<TrackedUrlHeader> trackedUrlResponse = restTemplate.exchange(
                url + "tiny/api/3/urls",
                HttpMethod.GET,
                request,
                TrackedUrlHeader.class);
        TrackedUrlHeader trackedUrlHeader = trackedUrlResponse.getBody();
        List<TrackedUrl> trackedUrls = trackedUrlHeader.getUrls();

    }
}
