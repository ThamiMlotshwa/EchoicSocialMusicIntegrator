package echoic.socialscouttwitter;

public class SearchExtractor
{
    public static String getSearchString(String post)
    {
        int index = post.indexOf("\uD83C\uDFB6");

        if (index == -1)
            return null;

        String substring = post.substring(index+2);
        index = substring.indexOf("\uD83C\uDFB6");
        String searchString = substring.substring(0, index);

        return searchString.strip();

    }
}
