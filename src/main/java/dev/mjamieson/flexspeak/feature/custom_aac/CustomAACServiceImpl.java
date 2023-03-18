package dev.mjamieson.flexspeak.feature.custom_aac;

import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.aac.AAC_Service;
import dev.mjamieson.flexspeak.feature.integration.flat_icon.service.our_api.FlatIconFacadeService;
import dev.mjamieson.flexspeak.feature.model.Sentence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAACServiceImpl implements CustomAACService {

    private final FlatIconFacadeService flatIconFacadeService;

    private final AAC_Service aac_service;


    @Override
    public Sentence post(@CurrentUsername String username, Sentence sentence) {

        String processedString = sentence.sentence()
                .trim()
                .toLowerCase();


        return Sentence.builder()
                .sentence("daksdjsj")
                .build();
    }
}
