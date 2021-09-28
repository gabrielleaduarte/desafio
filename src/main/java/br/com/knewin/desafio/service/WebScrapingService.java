package br.com.knewin.desafio.service;

import br.com.knewin.desafio.dto.NewDTO;

import java.io.IOException;
import java.text.ParseException;

public interface WebScrapingService {

    NewDTO addNew(String url) throws IOException, ParseException;

}
