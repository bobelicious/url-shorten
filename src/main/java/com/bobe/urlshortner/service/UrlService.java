package com.bobe.urlshortner.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import com.bobe.urlshortner.model.Source;
import com.bobe.urlshortner.model.Url;
import com.bobe.urlshortner.payload.UrlDto;
import com.bobe.urlshortner.repository.SourceRepository;
import com.bobe.urlshortner.repository.UrlRepository;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private ModelMapper mapper;

    public String CreateShortenUrl(UrlDto urlDto) {
        var newUrl = toModel(urlDto);
        var urlShorten = generateShortenUrl();
        if (checkShortenUrl(urlShorten)) {
            urlShorten = generateShortenUrl();
        }
        newUrl.setUrlShortened(urlShorten);
        newUrl.setRequestNums(0);
        newUrl.setRegisterDate(LocalDateTime.now().toString());
        return "http://localhost:8080/api/" + urlRepository.save(newUrl).getUrlShortened();
    }

    public RedirectView redirectToPage(String urlShorten, String source) {
        var longUrl = urlRepository.findByUrlShortened(urlShorten)
                .orElseThrow(() -> new RuntimeException("Url nao econtrada"));
        longUrl.setRequestNums(longUrl.getRequestNums() + 1);
        longUrl.setLastAccess(LocalDateTime.now().toString());
        insertOrUpdateSource(longUrl, source);
        urlRepository.save(longUrl);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(longUrl.getUrl());
        return redirectView;
    }

    private void insertOrUpdateSource(Url url, String source) {
        var returnedSource = sourceRepository.findByNameIgnoreCaseAndUrl(source, url);
        if (returnedSource.isEmpty()) {
            var newSource = new Source();
            newSource.setName(source);
            newSource.setAccess(1);
            newSource.setUrl(url);
            sourceRepository.save(newSource);
        } else {
            returnedSource.get().setAccess(returnedSource.get().getAccess() + 1);
            sourceRepository.save(returnedSource.get());
        }
    }

    public UrlDto getInfoFromShortenUrl(String shortenUrl) {
        var url = urlRepository.findByUrlShortened(shortenUrl)
                .orElseThrow(() -> new RuntimeException("Url nao encontrada"));
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
