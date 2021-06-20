package echoic.linkgenerator.core.interfaces;

import echoic.linkgenerator.core.MusicEntity;

public interface MusicEntityConverter<T>
{
    MusicEntity convertToMusicEntity(T t, ExternalAgnosticMusicEntityGenerator linkGenerator, ExternalTrackedUrlGenerator trackedUrlGenerator);
}
