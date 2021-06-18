package echoic.linkgenerator.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
