package com.bobe.urlshortner.service;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import com.bobe.urlshortner.model.Url;
import com.bobe.urlshortner.payload.UrlDto;
import com.bobe.urlshortner.repository.UrlRepository;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private ModelMapper mapper;

    public String CreateShortenUrl(UrlDto urlDto) {
        var newUrl = toModel(urlDto);
        var urlShorten = generateShortenUrl();
        var newSource = new HashMap<String,Integer>();
        if (checkShortenUrl(urlShorten)) {
            urlShorten = generateShortenUrl();
        }
        newUrl.setUrlShortened(urlShorten);
        newUrl.setRequestNums(0);
        newUrl.setRegisterDate(LocalDateTime.now().toString());
        newUrl.setSource(newSource);
        return "http://localhost:8080/api/" + urlRepository.save(newUrl).getUrlShortened();
    }

    public RedirectView redirectToPage(String urlShorten, String source){
        var longUrl = urlRepository.findByUrlShortened(urlShorten).orElseThrow(()->new RuntimeException("Url nao econtrada"));
        var sourceList = longUrl.getSource();
        longUrl.setRequestNums(longUrl.getRequestNums() + 1);
        longUrl.setLastAccess(LocalDateTime.now().toString());
        if(sourceList.containsKey(source)){
            var newValue = sourceList.get(source);
            newValue += 1; 
            sourceList.put(source, newValue);
        }
        urlRepository.save(longUrl);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(longUrl.getUrl());
        return redirectView;
    }


    public UrlDto getInfoFromShortenUrl(String shortenUrl){
        var url = urlRepository.findByUrlShortened(shortenUrl).orElseThrow(()-> new RuntimeException("Url nao encontrada"));
        return toDto(url);
    }

    private String generateShortenUrl() {
        char[] alphanumeric = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder random = new StringBuilder();

        for (int i = 0; i <= 5; i++) {
            int index = (int) (Math.random() * alphanumeric.length);
            random.append(alphanumeric[index]);
        }
        return random.toString();
    }

    private boolean checkShortenUrl(String shortenUrl) {
        if (!urlRepository.findByUrlShortened(shortenUrl).isEmpty()) {
            return true;
        }
        return false;
    }

    private UrlDto toDto(Url url) {
        var urlDto = mapper.map(url, UrlDto.class);
        return urlDto;
    }

    private Url toModel(UrlDto urlDto) {
        var url = mapper.map(urlDto, Url.class);
        return url;
    }
}
