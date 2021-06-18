package echoic.linkgenerator.external.musicentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalMusicEntity
{
    @JsonProperty("entityUniqueId")
    String entityUniqueId;

    @JsonProperty("pageUrl")
    String pageUrl;

    @JsonProperty("linksByPlatform")
    Platforms linksByPlatform;
}
