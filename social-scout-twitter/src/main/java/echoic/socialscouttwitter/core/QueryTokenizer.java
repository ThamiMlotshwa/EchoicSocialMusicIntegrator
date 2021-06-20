package echoic.socialscouttwitter.core;

public interface QueryTokenizer
{
    String getSearchString(String post) throws TokenizationException;
}
