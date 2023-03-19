package dev.mjamieson.flexspeak.feature.custom_word;

import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.aac.AAC_Service;
import dev.mjamieson.flexspeak.feature.integration.flat_icon.service.our_api.FlatIconFacadeService;
import dev.mjamieson.flexspeak.feature.model.Sentence;
import dev.mjamieson.flexspeak.feature.user.User;
import dev.mjamieson.flexspeak.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomWordServiceImpl implements CustomWordService {

    private final FlatIconFacadeService flatIconFacadeService;
    private final CustomWordRepository customWordRepository;

    private final AAC_Service aac_service;

    private final UserRepository userRepository;


    @Override
    public Sentence post(@CurrentUsername String username, CustomWordRequest customWordRequest) {

        User user = userRepository.findByEmail(username).orElseThrow();
        CustomWord customWord = new CustomWord();
        customWord.setWordToDisplay(customWordRequest.wordToDisplay());
        customWord.setWordToSpeak(customWordRequest.wordToSpeak());
        customWord.setImagePath(customWordRequest.imagePath());
        customWord.setGridRow(customWordRequest.gridRow());
        customWord.setGridColumn(customWordRequest.gridColumn());
        customWord.setGridTitleEnum(customWordRequest.gridTitleEnum());
        customWord.setUser(user);
        customWordRepository.save(customWord);

        return Sentence.builder()
                .sentence("daksdjsj")
                .build();
    }
}
