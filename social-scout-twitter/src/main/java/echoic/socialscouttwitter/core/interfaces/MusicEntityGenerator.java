package echoic.socialscouttwitter.core.interfaces;

import echoic.socialscouttwitter.core.MusicEntity;

import java.util.Optional;

public interface MusicEntityGenerator
{
    Optional<MusicEntity> getMusicEntity(String searchString);
}
