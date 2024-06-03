package com.elice.homealone;

import com.elice.homealone.global.crawler.CrawlerService;
import com.elice.homealone.recipe.service.RecipeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

    private final CrawlerService crawlerService;


    @Override
    public void run(String... args) throws Exception {
        //crawlerService.loadJsonAndSaveRecipe();
        crawlerService.loadFromMongoAndSaveRecipe();
    }
}
