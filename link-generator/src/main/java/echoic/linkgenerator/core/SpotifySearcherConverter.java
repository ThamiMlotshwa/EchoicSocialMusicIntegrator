package echoic.linkgenerator.core;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import echoic.linkgenerator.core.interfaces.*;
import echoic.linkgenerator.external.ExternalAgnosticMusicEntity;
import echoic.linkgenerator.external.linktracking.TrackedUrl;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class SpotifySearcherConverter implements SearcherConverter<Track>
{
    private SpotifyApi spotifyApi;

    public SpotifySearcherConverter(SpotifyApi spotifyApi)
    {
        this.spotifyApi = spotifyApi;
    }

    @Override
    public Optional<Track> getSearchResult(String searchTerm) {

        Track track = null;
        SearchTracksRequest tracksRequest = spotifyApi.searchTracks(searchTerm)
                .limit(1)
                .market(CountryCode.ZA)
                .market(CountryCode.GB)
                .market(CountryCode.BR)
                .market(CountryCode.US)
                .market(CountryCode.JP)
                .market(CountryCode.NG)
                .market(CountryCode.ML)
                .build();

        try
        {
            Paging<Track> trackPaging = tracksRequest.execute();

            if (trackPaging.getTotal()>0) {
                track = trackPaging.getItems()[0];
                log.info("Spotify track returned: " + track);
            }
        }
        catch (IOException | SpotifyWebApiException | ParseException e)
        {
            log.info(e.toString());
        }

        return Optional.ofNullable(track);
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
}
