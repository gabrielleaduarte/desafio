package br.com.knewin.desafio.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewDTO {

    @NotNull
    private String url;

    @NotNull
    private String title;

    @NotNull
    private String subtitle;

    @NotNull
    private String author;

    @NotNull
    private String date;

    @NotNull
    private String content;
}
