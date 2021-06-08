package echoic.linkgenerator;

import lombok.Data;

import java.io.Serializable;

@Data
public class MusicEntity implements Serializable
{
    private String artistName;
    private String albumName;
    private String songName;
    private String link;
}

enum MusicEntityType
{
    SONG, ALBUM, ARTIST
}
