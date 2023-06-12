package com.bobe.urlshortner.payload;

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
}
