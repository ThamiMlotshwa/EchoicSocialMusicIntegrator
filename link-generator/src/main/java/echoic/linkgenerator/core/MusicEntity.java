package echoic.linkgenerator.core;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "musicEntity")
public class MusicEntity implements Serializable
{
    @Id
    private String id;
    private String artistName;
    private String albumName;
    private String songName;
    private String link;
    private String code;
    private Long clicks;


}
