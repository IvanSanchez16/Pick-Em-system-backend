package com.pickemsystem.pickemsystembackend.images;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.pickemsystem.pickemsystembackend.exceptions.ExceptionHandlerController.logException;

@Component
@ConfigurationProperties(prefix = "server.images")
@Data
public class ImageManager {

    private String url;
    private String defaultName;

    @Async
    public void resizeAndSaveFile(MultipartFile originalImage, EntityType type, long id) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(url);
            switch (type) {
                case TEAM:
                    stringBuilder.append("\\teams\\");
                    break;
                case USER:
                    stringBuilder.append("\\users\\");
                    break;
                case PICKEM:
                    stringBuilder.append("\\pickems\\");
                    break;
                case REGION:
                    stringBuilder.append("\\regions\\");
                    break;
                case TOURNAMENT:
                    stringBuilder.append("\\tournaments\\");
                    break;
            }
            stringBuilder.append(id);
            // File extension
            stringBuilder.append('.');
            String name = originalImage.getOriginalFilename();
            if (name != null) {
                String[] originalFileName = name.split("\\.");
                stringBuilder.append(originalFileName[1]);
            } else {
                // Default
                stringBuilder.append("jpg");
            }

            File file = new File(stringBuilder.toString());

            originalImage.transferTo(file);
        } catch (IOException e) {
            logException(e);
        }
    }

    public File getImage(EntityType type, long id) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        switch (type) {
            case TEAM:
                stringBuilder.append("\\teams\\");
                break;
            case USER:
                stringBuilder.append("\\users\\");
                break;
            case PICKEM:
                stringBuilder.append("\\pickems\\");
                break;
            case REGION:
                stringBuilder.append("\\regions\\");
                break;
            case TOURNAMENT:
                stringBuilder.append("\\tournaments\\");
                break;
        }
        stringBuilder.append(id);
        stringBuilder.append(".png");

        File file = new File(stringBuilder.toString());
        if (file.exists())
            return file;

        return new File(String.format("%s\\%s", url, defaultName));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createDirectories() {
        File teamDirectory = new File(String.format("%s%s", url, "\\teams"));
        if (!teamDirectory.isDirectory())
            teamDirectory.mkdir();

        File userDirectory = new File(String.format("%s%s", url, "\\users"));
        if (!userDirectory.isDirectory())
            userDirectory.mkdir();

        File pickemDirectory = new File(String.format("%s%s", url, "\\pickems"));
        if (!pickemDirectory.isDirectory())
            pickemDirectory.mkdir();

        File regionDirectory = new File(String.format("%s%s", url, "\\regions"));
        if (!regionDirectory.isDirectory())
            regionDirectory.mkdir();

        File tournamentDirectory = new File(String.format("%s%s", url, "\\tournaments"));
        if (!tournamentDirectory.isDirectory())
            tournamentDirectory.mkdir();
    }
}
