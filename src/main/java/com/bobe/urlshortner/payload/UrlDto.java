package com.bobe.urlshortner.payload;

import java.util.List;

import com.bobe.urlshortner.model.Source;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlDto {

    private String url;
    private String urlShortened;
    private Integer requestNums;
    private List<Source> source;
    private String lastAccess;
    private String registerDate;
}
