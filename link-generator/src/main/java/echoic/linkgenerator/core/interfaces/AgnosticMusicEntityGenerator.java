package echoic.linkgenerator.core.interfaces;

import java.util.Optional;

public interface AgnosticMusicEntityGenerator<T>
{
    Optional<T> getAgnosticMusicEntity(String url);
}
