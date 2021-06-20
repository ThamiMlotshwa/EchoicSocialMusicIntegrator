package echoic.socialscouttwitter.core;

import java.util.Optional;

public interface MusicEntityGenerator
{
    Optional<MusicEntity> getMusicEntity(String searchString);
}
