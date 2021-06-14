package echoic.linkgenerator;

import be.ceau.itunesapi.Search;
import be.ceau.itunesapi.request.Entity;
import be.ceau.itunesapi.response.Response;
import be.ceau.itunesapi.request.search.Attribute;
import be.ceau.itunesapi.response.Result;
import echoic.linkgenerator.external.ExternalMusicEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class AppleSpecificGenerator implements SpecificGenerator
{
    private RestTemplate restTemplate;

    public AppleSpecificGenerator(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }
    @Override
    public MusicEntity generateMusicEntity(String searchTerm) {
        Search search = new Search();
        Entity searchEntity = Entity.SONG;
        String link;

        //ToDO: Put a timeout counter and a fallback function in case of REST failure
        Response response = search.setTerm(searchTerm)
                .setEntity(searchEntity)
                .execute();

        if (response.getResultCount() < 1)
            return null;



        Result firstResult = response.getResults().get(0);

        //ToDO: Put a timeout counter and a fallback function in case of REST failure
        ExternalMusicEntity externalMusicEntity = restTemplate.getForObject("https://api.song.link/v1-alpha.1/links?url={link}",
                                                                                ExternalMusicEntity.class,
                                                                                firstResult.getTrackViewUrl());

        MusicEntity musicEntity = new MusicEntity();
        musicEntity.setLink(externalMusicEntity.getPageUrl());
        musicEntity.setSongName(firstResult.getTrackName());
        musicEntity.setAlbumName(firstResult.getCollectionName());
        musicEntity.setArtistName(firstResult.getArtistName());


        //ToDo: Add persistence for the music entity in a database
        //          - Save music entity according to unique identifier
        //          - Add share counter
        //          - Add "clicked" counter (Need social scout integration)

        return musicEntity;
    }
}
