package com.acme.catchup.platform.news.domain.model.commands;

public record CreateFavoriteSourceCommand(String newsApiKey, String sourceId) {

    public CreateFavoriteSourceCommand {
        if (newsApiKey == null || newsApiKey.isBlank())
            throw new IllegalArgumentException("newsApiKey cannot be null or empty");
        if (sourceId == null || sourceId.isBlank())
            throw new IllegalArgumentException("sourceId cannot be null or empty");
    }

}
