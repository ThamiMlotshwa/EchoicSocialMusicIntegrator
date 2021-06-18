package echoic.linkgenerator.external.linktracking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkError
{
    @JsonProperty("code")
   Long code;

    @JsonProperty("message")
    String message;

    @JsonProperty("details")
    String details;

}
