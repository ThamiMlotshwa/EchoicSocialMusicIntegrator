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
    public MusicEntity generateMusicEntity(String searchTerm) {
        Search search = new Search();
        Entity searchEntity = Entity.SONG;
        String link;

        Response response = search.setTerm(searchTerm)
                .setEntity(searchEntity)
                .execute();

        if (response.getResultCount() < 1)
            return null;

        Result firstResult = response.getResults().get(0);
        MusicEntity musicEntity = new MusicEntity();
        musicEntity.setLink(firstResult.getTrackViewUrl());
        musicEntity.setSongName(firstResult.getTrackName());
        musicEntity.setAlbumName(firstResult.getCollectionName());
        musicEntity.setArtistName(firstResult.getArtistName());

        return musicEntity;
    }
}
