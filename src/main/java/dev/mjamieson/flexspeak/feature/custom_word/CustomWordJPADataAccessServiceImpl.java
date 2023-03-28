package dev.mjamieson.flexspeak.feature.custom_word;

import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.user.User;
import dev.mjamieson.flexspeak.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository("jpa")
@RequiredArgsConstructor
public class CustomWordJPADataAccessServiceImpl implements CustomWordDAO {
    private final CustomWordRepository customWordRepository;
    private final UserRepository userRepository;

    @Override
    public void save(@CurrentUsername String username, CustomWordDTO customWordDTO) {
        User user = userRepository.findByEmail(username).orElseThrow();
        CustomWord customWordExists = customWordRepository.findByUserAndGridColumnAndGridRowAndGridTitleEnum(
                user,
                customWordDTO.gridColumn(),
                customWordDTO.gridRow(),
                customWordDTO.gridTitleEnum()
        );

        if (Objects.nonNull(customWordExists)) updateCustomWord(customWordExists,customWordDTO);
        else createCustomWord(customWordDTO,user);

    }
    @Override
    public List<CustomWordDTO> get(@CurrentUsername String username) {
        User user = userRepository.findByEmail(username).orElseThrow();
        List<CustomWord> customWordByUser = customWordRepository.findByUser(user);
        return CustomWordDTO.from(customWordByUser);
    }

    private void updateCustomWord(CustomWord customWordExists, CustomWordDTO customWordDTO){
        if(Objects.nonNull(customWordDTO.wordToDisplay())) customWordExists.setWordToDisplay(customWordDTO.wordToDisplay());
        if(Objects.nonNull(customWordDTO.wordToSpeak())) customWordExists.setWordToSpeak(customWordDTO.wordToSpeak());
        if(Objects.nonNull(customWordDTO.gridTitleEnum())) customWordExists.setGridTitleEnum(customWordDTO.gridTitleEnum());
        customWordRepository.save(customWordExists);
    }

    private void createCustomWord(CustomWordDTO customWordDTO, User user){
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



}
