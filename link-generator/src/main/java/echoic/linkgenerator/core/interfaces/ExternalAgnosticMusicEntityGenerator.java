package echoic.linkgenerator.core.interfaces;

import echoic.linkgenerator.external.ExternalAgnosticMusicEntity;

public interface ExternalAgnosticMusicEntityGenerator extends AgnosticMusicEntityGenerator<ExternalAgnosticMusicEntity>
{
    String getGeneratorHost();
}
