package echoic.linkgenerator;

import be.ceau.itunesapi.Search;
import be.ceau.itunesapi.request.Entity;
import be.ceau.itunesapi.response.Response;
import be.ceau.itunesapi.response.Result;

import echoic.linkgenerator.external.linktracking.TrackedUrl;
import echoic.linkgenerator.external.linktracking.TrackedUrlHeader;
import echoic.linkgenerator.external.musicentity.ExternalMusicEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Base64;
import java.util.Map;

@Slf4j
public class AppleSpecificGenerator implements SpecificGenerator
{
    private RestTemplate restTemplate;

    @Autowired
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
        /*URI targetUrl = UriComponentsBuilder.fromUriString("https://itunes.apple.com")
                .path("search")
                .queryParam("term", searchTerm)
                .build()
                .encode()
                .toUri();

        //ItunesMusicEntities searchedResult = restTemplate.getForObject(targetUrl, ItunesMusicEntities.class);

        */
        Response response = search.setTerm(searchTerm)
                .setEntity(searchEntity)
                .execute();

        if (response.getResultCount() < 1)
        {
            return null;
        }


        Result firstResult = response.getResults().get(0);


        //ToDO: Put a timeout counter and a fallback function in case of REST failure

        // Get system-agnostic link
        ExternalMusicEntity externalMusicEntity = restTemplate.getForObject("https://api.song.link/v1-alpha.1/links?url={link}",
                ExternalMusicEntity.class,
                firstResult.getTrackViewUrl());

        // Get tracked link
        HttpHeaders headers = new HttpHeaders();

        String base64Creds = Base64.getEncoder().encodeToString("thami:1280acc1-86b5-4bda-99bc-4a49dbb77813".getBytes());
        headers.set("Authorization", "Basic "+base64Creds);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"urls\": [{\"long_url\": \"" + externalMusicEntity.getPageUrl() + "\"}]}", headers);
        //ResponseEntity<TrackedUrl> trackedUrlResponseEntity = restTemplate.exchange("https://kutt.it/api/v2/links", HttpMe)
        ResponseEntity<TrackedUrlHeader> trackedUrls = restTemplate.postForEntity("https://tiny.cc/tiny/api/3/urls",
                request,
                TrackedUrlHeader.class);
        TrackedUrlHeader trackedUrlHeader = trackedUrls.getBody();
        TrackedUrl trackedUrl = trackedUrlHeader.getUrls().get(0);


        // Make and populate MusicEntity object
        MusicEntity musicEntity = new MusicEntity();
        musicEntity.setLink(trackedUrl.getShortUrl());
        musicEntity.setSongName(firstResult.getTrackName());
        musicEntity.setAlbumName(firstResult.getCollectionName());
        musicEntity.setArtistName(firstResult.getArtistName());
        musicEntity.setCode(firstResult.getTrackId());

        //ToDo: Add persistence for the music entity in a database
        //          - Save music entity according to unique identifier
        //          - Add share counter
        //          - Add "clicked" counter (Need social scout integration)


        return musicEntity;
    }
}
