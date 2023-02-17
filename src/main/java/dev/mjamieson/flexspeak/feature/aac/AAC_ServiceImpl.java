package dev.mjamieson.flexspeak.feature.aac;

import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.model.Sentence;
import dev.mjamieson.flexspeak.feature.user.User;
import dev.mjamieson.flexspeak.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AAC_ServiceImpl implements AAC_Service {
    private final UserRepository userRepository;
    private final AAC_Repository aac_repository;
    @Override
    public Void postSentence(@CurrentUsername String username, Sentence sentence) {
        User user = userRepository.findByEmail(username).orElseThrow();
        AAC aac = new AAC();
        int amountOfWords = sentence.words().size();
        aac.setSentence(sentence.sentence());
        aac.setStartTime(sentence.words().get(0).timestamp());
        aac.setEndTime(sentence.words().get(amountOfWords - 1).timestamp());
        aac.setUser(user);
        aac_repository.save(aac);

        return null;
    }
}
