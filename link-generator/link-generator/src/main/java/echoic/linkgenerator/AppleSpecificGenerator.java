package echoic.linkgenerator;

import be.ceau.itunesapi.Search;
import be.ceau.itunesapi.request.Entity;
import be.ceau.itunesapi.response.Response;
import be.ceau.itunesapi.request.search.Attribute;
import be.ceau.itunesapi.response.Result;

import java.util.List;

public class AppleSpecificGenerator implements SpecificGenerator
{
    @Override
    public MusicEntity getEntity(String searchTerm, MusicEntityType type) {
        Search search = new Search();
        Attribute searchAttribute;
        Entity searchEntity;
        String link;

        switch(type)
        {
            case SONG:
                searchAttribute = Attribute.SONG_TERM;
                searchEntity = Entity.MUSIC_TRACK;
                break;
            case ALBUM:
                searchAttribute = Attribute.ALBUM_TERM;
                searchEntity = Entity.ALBUM;
                break;
            case ARTIST:
                searchAttribute = Attribute.ARTIST_TERM;
                searchEntity = Entity.MUSIC_ARTIST
                break;
            default:
                searchAttribute = Attribute.ALL_TRACK_TERM;
                searchEntity = Entity.ALL_TRACK;
        }

        Response response = search.setTerm(searchTerm)
                .setAttribute(searchAttribute)
                .setEntity(searchEntity)
                .execute();
        Result firstResult = response.getResults().get(0);

        switch(type)
        {
            case SONG:
            default:
            case ALBUM:
                link = firstResult.getTrackViewUrl();
                break;
            case ARTIST:
                link = firstResult.getArtistViewUrl();
                break;
        }

        MusicEntity musicEntity = new MusicEntity();
        musicEntity.setLink(link);
        musicEntity.setType(type);

        return musicEntity;
    }
}
