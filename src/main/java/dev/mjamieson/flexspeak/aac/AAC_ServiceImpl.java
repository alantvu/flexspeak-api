package dev.mjamieson.flexspeak.aac;

import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.model.FirstClass;
import dev.mjamieson.flexspeak.user.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AAC_ServiceImpl implements AAC_Service {

    private final JwtService jwtService;


    @Override
    public Void postSentence(@CurrentUsername String username, FirstClass firstClass) {
//        jwtService.extractUsername();

        return null;
    }
}
