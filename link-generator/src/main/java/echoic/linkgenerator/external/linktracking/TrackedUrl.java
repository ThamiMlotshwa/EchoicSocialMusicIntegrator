package echoic.linkgenerator.external.linktracking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

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

    //LocalDateTime creationDate;





}
