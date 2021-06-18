package echoic.linkgenerator.external.linktracking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackedUrlHeader
{
    @JsonProperty("urls")
    List<TrackedUrl> urls;
}
