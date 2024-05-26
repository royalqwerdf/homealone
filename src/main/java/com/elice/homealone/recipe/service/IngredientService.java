package com.elice.homealone.recipe.service;

import com.elice.homealone.recipe.repository.IngredientRepository;
import com.elice.homealone.recipe.entity.Ingredient;
import com.elice.homealone.recipe.entity.RecipeIngredient;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    // 입력된 레시피 재료와 재료의 이름이 유사한 지 확인 하여 유사하면 연결, 유사하지 않다면 재료 엔티티를 생성하고 연결시켜주어 반환한다.
    public Ingredient addIngredient(RecipeIngredient recipeIngredient) {
        String name = recipeIngredient.getName();
        Optional<Ingredient> ingredient = similarIngredientExists(name);
        if(ingredient.isEmpty()){
            Ingredient newIngredient = Ingredient.builder()
                .name(name)
                .build();
            ingredientRepository.save(newIngredient);
            newIngredient.addIngredient(recipeIngredient);
            return newIngredient;
        }
        ingredient.get().addIngredient(recipeIngredient);
        return ingredient.get();
    }

    // RecipeIngredient와 이름이 유사한 Ingredient가 있는지 확인하는 메소드
    // name에서 영어와 한글, 숫자만 남기고, 소문자는 대문자로 치환해서 비교
    public Optional<Ingredient> similarIngredientExists(String name) {
        StringBuilder sb = new StringBuilder();

        for(char c : name.toCharArray()) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '가' && c <= '힣') || Character.isDigit(c)) {
                sb.append(Character.toUpperCase(c));
            }
        }
        return ingredientRepository.findByName(sb.toString());
    }
}
