package echoic.linkgenerator.external.musicentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItunesMusicEntity
{
    Long trackId;
    String artistName;
    String collectionName;
    String trackName;
    String trackViewUrl;
}
