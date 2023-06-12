package com.bobe.urlshortner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.bobe.urlshortner.payload.UrlDto;
import com.bobe.urlshortner.service.UrlService;

@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService urlService;
    
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody UrlDto urlDto){
        return new ResponseEntity<String>(urlService.CreateShortenUrl(urlDto), HttpStatus.CREATED);
    }

    @GetMapping("/{shortUrl}")
     public RedirectView redirectToOriginalURL(@PathVariable String shortUrl) {
        return urlService.redirectToPage(shortUrl);
     }
}