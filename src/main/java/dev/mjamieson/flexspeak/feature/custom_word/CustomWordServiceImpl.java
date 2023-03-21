package dev.mjamieson.flexspeak.feature.custom_word;

import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.aac.AAC_Service;
import dev.mjamieson.flexspeak.feature.integration.flat_icon.service.our_api.FlatIconFacadeService;
import dev.mjamieson.flexspeak.feature.user.User;
import dev.mjamieson.flexspeak.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomWordServiceImpl implements CustomWordService {

    private final FlatIconFacadeService flatIconFacadeService;
    private final CustomWordRepository customWordRepository;

    private final AAC_Service aac_service;

    private final UserRepository userRepository;


    @Override
    public Void post(@CurrentUsername String username, CustomWordDTO customWordDTO) {

        User user = userRepository.findByEmail(username).orElseThrow();

        CustomWord customWordExists = customWordRepository.findByUserAndGridColumnAndGridRowAndGridTitleEnum(
                user,
                customWordDTO.gridColumn(),
                customWordDTO.gridRow(),
                customWordDTO.gridTitleEnum()
        );
        if (Objects.nonNull(customWordExists)) {
            if(Objects.nonNull(customWordDTO.wordToDisplay())) customWordExists.setWordToDisplay(customWordDTO.wordToDisplay());
            if(Objects.nonNull(customWordDTO.wordToSpeak())) customWordExists.setWordToSpeak(customWordDTO.wordToSpeak());
            if(Objects.nonNull(customWordDTO.gridTitleEnum())) customWordExists.setGridTitleEnum(customWordDTO.gridTitleEnum());
            customWordRepository.save(customWordExists);
        } else {
            CustomWord customWord = new CustomWord();
            customWord.setWordToDisplay(customWordDTO.wordToDisplay());
            customWord.setWordToSpeak(customWordDTO.wordToSpeak());
            customWord.setImagePath(customWordDTO.imagePath());
            customWord.setGridRow(customWordDTO.gridRow());
            customWord.setGridColumn(customWordDTO.gridColumn());
            customWord.setGridTitleEnum(customWordDTO.gridTitleEnum());
            customWord.setUser(user);
            customWordRepository.save(customWord);
        }


        return null;
    }

    @Override
    public List<CustomWordDTO> get(@CurrentUsername String username) {
        User user = userRepository.findByEmail(username).orElseThrow();
        List<CustomWord> customWordByUser = customWordRepository.findByUser(user);
        return CustomWordDTO.from(customWordByUser);
    }
}
