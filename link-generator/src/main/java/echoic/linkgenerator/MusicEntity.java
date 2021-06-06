package echoic.linkgenerator;

import lombok.Data;

@Data
public class MusicEntity
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
