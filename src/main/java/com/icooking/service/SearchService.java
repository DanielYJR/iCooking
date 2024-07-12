package com.icooking.service;

import com.icooking.entity.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SearchService {

    List<Recipe> findAllRecipesByWord(String word);

}
