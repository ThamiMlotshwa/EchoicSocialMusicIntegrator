package echoic.socialscouttwitter.core.unittests;

import echoic.socialscouttwitter.core.TokenizationException;
import echoic.socialscouttwitter.core.interfaces.QueryTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QueryTokenizerTest_UnitTests {

    @Autowired
    QueryTokenizer queryTokenizer;

    public static String TOKEN = "\uD83C\uDFB6";
    public static String SEARCH_STRING = "searchString";

    @Test
    @DisplayName("getSearchString (Successful)")
    void testGetSearchString_Successful() throws TokenizationException
    {
        String post = "This is correct " + TOKEN + SEARCH_STRING
                        + TOKEN + " #echoicmusic";

        String searchString = queryTokenizer.getSearchString(post);

        Assertions.assertEquals(searchString, SEARCH_STRING);
    }

    @Test
    @DisplayName("getSearchString (One token unsuccessful)")
    void testGetSearchString_OneTokenUnsuccessful()
    {
        String post = "This is incorrect " + TOKEN + SEARCH_STRING
                + " #echoicmusic";

        Assertions.assertThrows(TokenizationException.class, () ->
                queryTokenizer.getSearchString(post),
                "One token found");
    }

    @Test
    @DisplayName("getSearchString (No tokens unsuccessful)")
    void testGetSearchString_NoTokensUnsuccessful()
    {
        String post = "This is incorrect " + SEARCH_STRING
                + " #echoicmusic";

        Assertions.assertThrows(TokenizationException.class, () ->
                        queryTokenizer.getSearchString(post),
                "No tokens found");
    }

    @Test
    @DisplayName("getSearchString (Three tokens successful)")
    void testGetSearchString_ThreeTokensSuccessful() throws TokenizationException
    {
        String post = "This should be fixed " + TOKEN + SEARCH_STRING
                + TOKEN + " String " + TOKEN + " #echoicmusic";

        String searchString = queryTokenizer.getSearchString(post);

        Assertions.assertEquals(searchString, SEARCH_STRING);
    }

    @Test
    @DisplayName("getSearchString (Adj. tokens unsuccessful)")
    void testGetSearchString_AdjTokensUnsuccessful()
    {
        String post = "This is correct " + TOKEN
                + TOKEN + " #echoicmusic";

        Assertions.assertThrows(TokenizationException.class,
                                () -> queryTokenizer.getSearchString(post),
                        "Empty search string");
    }

    @Test
    @DisplayName("getSearchString (Padded successful)")
    void testGetSearchString_WhiteSpaceSuccessful() throws TokenizationException
    {
        String post = "This is correct " + TOKEN + "           " + SEARCH_STRING
                + "           " +TOKEN + " #echoicmusic";

        String searchString = queryTokenizer.getSearchString(post);

        Assertions.assertEquals(searchString, SEARCH_STRING);
    }



}