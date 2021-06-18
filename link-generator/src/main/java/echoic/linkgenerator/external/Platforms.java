package echoic.linkgenerator.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Platforms
{
    @JsonProperty("appleMusic")
    PlatformSpecificLink appleMusic;

    @JsonProperty("spotify")
    PlatformSpecificLink spotify;

    @JsonProperty("tidal")
    PlatformSpecificLink tidal;

    @JsonProperty("youtubeMusic")
    PlatformSpecificLink youtube;
}
