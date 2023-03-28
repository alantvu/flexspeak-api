package dev.mjamieson.flexspeak.feature.custom_word;


import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface CustomWordService {

    Void post(@CurrentUsername String username, MultipartHttpServletRequest request);
    List<CustomWordDTO> get(String username);
}
