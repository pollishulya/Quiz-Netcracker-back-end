package com.example.service.impl;

import com.example.exception.DeleteEntityException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.GameCategory;
import com.example.repository.GameCategoryRepository;
import com.example.service.interfaces.GameCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameCategoryServiceImpl implements GameCategoryService {

    private final GameCategoryRepository gameCategoryRepository;
    private final MessageSource messageSource;

    @Autowired
    public GameCategoryServiceImpl(GameCategoryRepository gameCategoryRepository, MessageSource messageSource) {
        this.gameCategoryRepository = gameCategoryRepository;
        this.messageSource = messageSource;
    }

    @Override
    public GameCategory findGameCategoryById(UUID id) {
        UUID[] args = new UUID[]{ id };
        return gameCategoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
    }

    @Override
    public List<GameCategory> findAllGameCategory() {
        return gameCategoryRepository.findAll();
    }

    @Override
    public GameCategory saveGameCategory(GameCategory category) {
        return gameCategoryRepository.save(category);
    }

    @Override
    public GameCategory updateGameCategory(UUID categoryId, GameCategory categoryRequest) {
        UUID[] args = new UUID[]{ categoryId };
        return gameCategoryRepository.findById(categoryId).map(category -> {
            category.setDescription(categoryRequest.getDescription());
            category.setTitle(categoryRequest.getTitle());
            return gameCategoryRepository.save(category);
        }).orElseThrow(()-> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
    }

    @Override
    public void deleteGameCategory(UUID categoryId) {
        try {
            gameCategoryRepository.deleteById(categoryId);
        }
        catch (RuntimeException exception) {
            UUID[] args = new UUID[]{ categoryId };
            throw new DeleteEntityException(ErrorInfo.DELETE_ENTITY_ERROR,
                    messageSource.getMessage("message.DeleteEntityError", args, LocaleContextHolder.getLocale()));
        }
    }


}

