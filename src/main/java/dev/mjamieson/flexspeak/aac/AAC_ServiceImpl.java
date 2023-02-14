package dev.mjamieson.flexspeak.aac;

import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.model.FirstClass;
import dev.mjamieson.flexspeak.user.User;
import dev.mjamieson.flexspeak.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AAC_ServiceImpl implements AAC_Service {
    private final UserRepository userRepository;
    private final AAC_Repository aac_repository;
    @Override
    public Void postSentence(@CurrentUsername String username, FirstClass firstClass) {
        User user = userRepository.findByEmail(username).orElseThrow();
        AAC aac = new AAC();
        aac.setSentence(firstClass.stringy());
        aac_repository.save(aac);

        return null;
    }
}
