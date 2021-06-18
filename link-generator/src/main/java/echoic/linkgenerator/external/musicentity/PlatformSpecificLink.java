package echoic.linkgenerator.external.musicentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatformSpecificLink
{
    @JsonProperty("url")
    String url;

    @JsonProperty("entityUniqueId")
    String entityUniqueId;
}
