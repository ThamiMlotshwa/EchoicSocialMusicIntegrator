package echoic.socialscouttwitter;

import lombok.Data;

import java.io.Serializable;

@Data
public class MusicEntity implements Serializable
{
    private String artistName;
    private String albumName;
    private String songName;
    private String link;

    @Override
    public String toString()
    {
        return "MusicEntity[songName="+songName+", " +
                            "artistName="+artistName+", " +
                            "albumName="+albumName+", " +
                            "link="+link+"]";
    }
}