package echoic.socialscouttwitter.core.interfaces;

import echoic.socialscouttwitter.core.TokenizationException;

public interface QueryTokenizer
{
    String getSearchString(String post) throws TokenizationException;
}
