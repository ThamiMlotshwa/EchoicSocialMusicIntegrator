package echoic.linkgenerator.core.unittests;

import java.util.Optional;

public interface AgnosticMusicEntityGenerator<T>
{
    Optional<T> getAgnosticMusicEntity(String url);
}
