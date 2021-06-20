package echoic.linkgenerator.external.linktracking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Base64;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackedUrl
{
    @JsonProperty("id")
    String linkId;

    @JsonProperty("long_url")
    String longUrl;

    @JsonProperty("short_url_with_protocol")
    String shortUrl;

    @JsonProperty("total_clicks")
    String totalClicks;

    @JsonProperty("error")
    LinkError linkError;

    public static TrackedUrl getTrackedUrl(String url, RestTemplate restTemplate)
    {
        HttpHeaders headers = new HttpHeaders();

        String base64Creds = Base64.getEncoder().encodeToString("thami:1280acc1-86b5-4bda-99bc-4a49dbb77813".getBytes());
        headers.set("Authorization", "Basic "+base64Creds);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"urls\": [{\"long_url\": \"" + url + "\"}]}", headers);
        ResponseEntity<TrackedUrlHeader> trackedUrls = restTemplate.postForEntity("https://tiny.cc/tiny/api/3/urls",
                request,
                TrackedUrlHeader.class);
        TrackedUrlHeader trackedUrlHeader = trackedUrls.getBody();
        TrackedUrl trackedUrl = trackedUrlHeader.getUrls().get(0);
        return trackedUrl;
    }





}
