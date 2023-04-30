package dev.mjamieson.flexspeak.feature.integration.flat_icon.service.external_api;


import dev.mjamieson.flexspeak.feature.integration.flat_icon.binding.FlatIconBinding;
import dev.mjamieson.flexspeak.feature.integration.flat_icon.binding.FlatIconFactory;
import dev.mjamieson.flexspeak.feature.integration.flat_icon.model.FlatIconApiKey;
import dev.mjamieson.flexspeak.feature.integration.request.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlatIconServiceImpl implements FlatIconService {

    private final FlatIconFactory flatIconFactory;
    private final static String version = "v3";
    @Override
//    public List<SpaceXCapsule> getAllCapsules() {
    public List<Object> getAllBy() {
//        postAuth();

        FlatIconBinding flatIconBinding = flatIconFactory.getAPIBinding();
        URI uri = UriComponentsBuilder
                .fromHttpUrl(flatIconBinding.getApiUrl())
                .pathSegment(version,"search", "icons")
                .queryParam("q","arrow")
                .build().toUri();
        ResponseWrapper<String> responseWrapper = flatIconBinding.makeRequest(uri, HttpMethod.GET, String.class);
        return null;
    }

    @Override
    public void postAuth(){
        FlatIconBinding flatIconBinding = flatIconFactory.getAPIBinding();
        URI uri = UriComponentsBuilder
                .fromHttpUrl(flatIconBinding.getApiUrl())
                .pathSegment(flatIconBinding.getVersion(),"app","authentication")
                .build().toUri();
        FlatIconApiKey flatIconApiKey =  FlatIconApiKey.builder()
                .apikey(flatIconBinding.getApiKey())
                .build();
        flatIconBinding.makeRequest(uri, HttpMethod.POST, String.class,flatIconApiKey);


    }
}
