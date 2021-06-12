package echoic.linkgenerator;

import be.ceau.itunesapi.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import be.ceau.itunesapi.Search;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/returnLinks",
        produces="application/json")
@CrossOrigin(origins="*")
public class LinkReturnerRestController
{
    SpecificGenerator specificGenerator;

    @Autowired
    public LinkReturnerRestController(SpecificGenerator specificGenerator)
    {
        this.specificGenerator = specificGenerator;
    }

    @GetMapping("/test")
    public String test()
    {
        Search search = new Search();
        Response response = search.setTerm("Lovesick Espacio")
                .execute();
        return response.toString();
    }

    @GetMapping("{searchTerm}")
    public MusicEntity getLinks(@PathVariable("searchTerm") String searchTerm)
    {
        MusicEntity musicEntity = specificGenerator.generateMusicEntity(searchTerm);
        List<MusicEntity> musicEntities = new ArrayList<>();
        musicEntities.add(musicEntity);

        return musicEntity;
    }
}
