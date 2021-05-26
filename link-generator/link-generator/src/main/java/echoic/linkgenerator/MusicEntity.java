package echoic.linkgenerator;

import lombok.Data;

@Data
public class MusicEntity
{
    private String link;
    private MusicEntityType type;
}

enum MusicEntityType
{
    SONG, ALBUM, ARTIST;
}
