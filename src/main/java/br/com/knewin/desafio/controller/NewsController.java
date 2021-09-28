package br.com.knewin.desafio.controller;

import br.com.knewin.desafio.dto.NewDTO;
import br.com.knewin.desafio.service.impl.WebScrapingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NewsController {

    @Autowired
    private WebScrapingServiceImpl webScrapingService;

    @PostMapping("/addNews")
    public ResponseEntity<NewDTO> addNew(@RequestBody String url) throws Exception {

        NewDTO response = webScrapingService.addNew(url);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
