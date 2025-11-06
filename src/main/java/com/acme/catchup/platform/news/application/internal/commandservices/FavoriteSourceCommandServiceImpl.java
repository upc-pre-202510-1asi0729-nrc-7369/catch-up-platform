package com.acme.catchup.platform.news.application.internal.commandservices;

import com.acme.catchup.platform.news.domain.model.aggregates.FavoriteSource;
import com.acme.catchup.platform.news.domain.model.commands.CreateFavoriteSourceCommand;
import com.acme.catchup.platform.news.domain.services.FavoriteSourceCommandService;
import com.acme.catchup.platform.news.infrastructure.persistence.jpa.FavoriteSourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteSourceCommandServiceImpl implements FavoriteSourceCommandService {
    private final FavoriteSourceRepository favoriteSourceRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteSourceCommandServiceImpl.class);
    private final MessageSource messageSource;

    public FavoriteSourceCommandServiceImpl(FavoriteSourceRepository favoriteSourceRepository, MessageSource messageSource) {
        this.favoriteSourceRepository = favoriteSourceRepository;
        this.messageSource = messageSource;
    }

    @Override
    public Optional<FavoriteSource> handle(CreateFavoriteSourceCommand command) {
        if (favoriteSourceRepository.existsByNewsApiKeyAndSourceId(command.newsApiKey(), command.sourceId()))
            throw new IllegalArgumentException("Favorite source with same source ID already exists for this API key");
        var favoriteSource = new FavoriteSource(command);
        var createdFavoriteSource = favoriteSourceRepository.save(favoriteSource);
        return Optional.of(createdFavoriteSource);
    }
}
