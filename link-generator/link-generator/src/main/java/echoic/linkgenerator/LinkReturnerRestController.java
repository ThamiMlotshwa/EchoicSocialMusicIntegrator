package echoic.linkgenerator;

import be.ceau.itunesapi.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import be.ceau.itunesapi.Search;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LinkReturnerRestController
{
    private AppleSpecificGenerator appleSpecificGenerator;


    @Autowired
    public LinkReturnerRestController(AppleSpecificGenerator appleSpecificGenerator)
    {
        this.appleSpecificGenerator = appleSpecificGenerator;
    }

    @GetMapping("/test")
    public String test()
    {
        Search search = new Search();
        Response response = search.setTerm("Lovesick Espacio")
                .execute();
        return response.toString();
    }

    @GetMapping("getLinks")
    public List<MusicEntity> getLinks(String searchTerm, String type)
    {
        MusicEntity musicEntity = appleSpecificGenerator.getEntity(searchTerm, MusicEntityType.valueOf(type));
        List<MusicEntity> musicEntities = new ArrayList<>();
        musicEntities.add(musicEntity);
        return musicEntities;
    }
}
