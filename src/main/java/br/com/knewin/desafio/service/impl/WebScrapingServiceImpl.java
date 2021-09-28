package br.com.knewin.desafio.service.impl;

import br.com.knewin.desafio.dto.NewDTO;
import br.com.knewin.desafio.entity.New;
import br.com.knewin.desafio.repository.NewRepository;
import br.com.knewin.desafio.service.WebScrapingService;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Builder
public class WebScrapingServiceImpl implements WebScrapingService {

    @Autowired
    private NewRepository newRepository;

    @Override
    public NewDTO addNew(String url) throws IOException, ParseException {

        WebClient client = new WebClient();
        try {
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final HtmlPage page = client.getPage(url);

        DomElement title = page.getFirstByXPath("//h1[@class='page-title-1']");

        DomElement subtitle = page.getFirstByXPath("//p[@class='article-lead']");

        DomElement author = page.getFirstByXPath("//span[@class='author-name']");

        DomElement date = page.getFirstByXPath("//time[@datetime]");
        String extractedDate = date.getAttribute("datetime").replace("-03:00","");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date formattedDate = format.parse(extractedDate);
        SimpleDateFormat requiredDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        List<DomElement> content = page.getByXPath("//div[@class='col-md-9 col-lg-8 col-xl-6  m-sm-auto m-lg-0 article-content']/p | //div[@class='col-md-9 col-lg-8 col-xl-6  m-sm-auto m-lg-0 article-content']/h2");

        String result = new String();

        for (int i=0; i<content.size(); i++){
            result += (content.get(i).asNormalizedText()) + " ";
        }

        New newsInformation = new New();
        newsInformation.setUrl(url);
        newsInformation.setTitle(title.asNormalizedText());
        newsInformation.setSubtitle(subtitle.asNormalizedText());
        newsInformation.setAuthor(author.asNormalizedText().replace("Por ", ""));
        newsInformation.setDate(requiredDate.format(formattedDate));
        newsInformation.setContent(result.replace(" Clique aqui para ouvir a história do Magazine Luiza.", ""));

        newRepository.save(newsInformation);

        return NewDTO.builder()
                .url(url)
                .title(title.asNormalizedText())
                .subtitle(subtitle.asNormalizedText())
                .author(author.asNormalizedText().replace("Por ", ""))
                .date(requiredDate.format(formattedDate))
                .content(result.replace(" Clique aqui para ouvir a história do Magazine Luiza.", ""))
                .build();
    }
}