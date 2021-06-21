package echoic.linkgenerator.core;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import echoic.linkgenerator.core.interfaces.*;
import echoic.linkgenerator.external.ExternalAgnosticMusicEntity;
import echoic.linkgenerator.external.linktracking.TinyCcExternalTrackedUrlGenerator;
import echoic.linkgenerator.external.linktracking.TrackedUrl;
import echoic.linkgenerator.external.linktracking.TrackedUrlHeader;
import echoic.linkgenerator.external.musicentity.SonglinkExtAgnosticMusicEntityGenerator;
import echoic.linkgenerator.repos.mongo.MusicEntityRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Component
@Slf4j
@Transactional
public class SpotifyAgnosticLinkGenerator implements AgnosticLinkGenerator

{
    private SpotifyApi spotifyApi;
    private RestTemplate restTemplate;
    private SearcherConverter searcherConverter;
    private ExternalAgnosticMusicEntityGenerator agnosticMusicEntityGenerator;
    private ExternalTrackedUrlGenerator trackedUrlGenerator;


    @Autowired
    private MusicEntityRepo musicEntityRepo;

    public SpotifyAgnosticLinkGenerator(SpotifyApi spotifyApi, RestTemplate restTemplate,
                                        SearcherConverter searcherConverter,
                                        ExternalAgnosticMusicEntityGenerator agnosticMusicEntityGenerator,
                                        ExternalTrackedUrlGenerator trackedUrlGenerator)
    {
        this.spotifyApi = spotifyApi;
        this.restTemplate = restTemplate;
        this.searcherConverter = searcherConverter;
        this.agnosticMusicEntityGenerator = agnosticMusicEntityGenerator;
        this.trackedUrlGenerator = trackedUrlGenerator;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, timeout = 7000)
    public MusicEntity generateMusicEntity(String searchTerm, SearcherConverter searcherConverter)
    {
        MusicEntity musicEntity = null;
        Track track = (Track) searcherConverter.getSearchResult(searchTerm).orElseThrow();
        musicEntity = searcherConverter.convertToMusicEntity(track, agnosticMusicEntityGenerator, trackedUrlGenerator);
        //musicEntity = musicEntityRepo.save(musicEntity);
        return musicEntity;
    }
    /*

    @Override
    public SearchTracksRequest getSearchRequest(String searchTerm)
    {
        return spotifyApi.searchTracks(searchTerm)
                .limit(1)
                .market(CountryCode.ZA)
                .market(CountryCode.GB)
                .market(CountryCode.BR)
                .market(CountryCode.US)
                .market(CountryCode.JP)
                .market(CountryCode.NG)
                .market(CountryCode.ML)
                .build();
    }

    @Override
    public MusicEntity convertToMusicEntity(Track track, ExternalAgnosticMusicEntityGenerator agnosticMusicEntityGenerator,
                                            ExternalTrackedUrlGenerator trackedUrlGenerator)
    {
        ExternalAgnosticMusicEntity agnosticMusicEntity = agnosticMusicEntityGenerator.getAgnosticMusicEntity(
                track.getExternalUrls().get("spotify")).orElseThrow();
        TrackedUrl trackedUrl = trackedUrlGenerator.getTrackedUrl(agnosticMusicEntity.getPageUrl()).orElseThrow();
        MusicEntity musicEntity = new MusicEntity();
        musicEntity.setSongName(track.getName());
        musicEntity.setArtistName(track.getAlbum().getArtists()[0].getName());
        musicEntity.setAlbumName(track.getAlbum().getName());
        musicEntity.setLink(trackedUrl.getShortUrl());
        musicEntity.setClicks(0L);
        musicEntity.setCode(track.getId());
        return musicEntity;
    }

    @Override
    public Optional<ExternalAgnosticMusicEntity> getAgnosticMusicEntity(String url)
    {
        ExternalAgnosticMusicEntity externalAgnosticMusicEntity = restTemplate.getForObject("https://api.song.link/v1-alpha.1/links?url={link}",
                ExternalAgnosticMusicEntity.class,
                url);

        return Optional.ofNullable(externalAgnosticMusicEntity);
    }

    @Override
    public String getGeneratorHost()
    {
        return "Song.link and Tiny.cc";
    }

    @Override
    public Optional<TrackedUrl> getTrackedUrl(String url) {

        TrackedUrl trackedUrl = null;

        HttpHeaders headers = new HttpHeaders();

        // put this in the config
        String base64Creds = Base64.getEncoder().encodeToString("thami:1280acc1-86b5-4bda-99bc-4a49dbb77813".getBytes());

        headers.set("Authorization", "Basic "+base64Creds);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"urls\": [{\"long_url\": \"" + url + "\"}]}", headers);
        ResponseEntity<TrackedUrlHeader> responseEntity = restTemplate.postForEntity("https://tiny.cc/tiny/api/3/urls",
                request,
                TrackedUrlHeader.class);
        if (responseEntity.hasBody())
        {
            TrackedUrlHeader trackedUrlHeader = responseEntity.getBody();
            trackedUrl = trackedUrlHeader.getUrls().get(0);
        }

        return Optional.ofNullable(trackedUrl);
    }*/}
