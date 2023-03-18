package dev.mjamieson.flexspeak.feature.integration.flat_icon.service.our_api;


import dev.mjamieson.flexspeak.feature.integration.flat_icon.binding.FlatIconFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlatIconFacadeServiceImpl implements FlatIconFacadeService {

    private final FlatIconFactory flatIconFactory;

    @SneakyThrows
    @Override
    public void invokeIntegration() {
        flatIconFactory.
                getAPIBinding()
                .getExecutorService()
                .submit(() -> aggregateOfInvocations()).get();
    }

    private void aggregateOfInvocations(){
//        List<Object> spaceXCapsuleList = spaceXCapsuleService.getAllCapsules();
    }






    @SneakyThrows
    @Override
    public void postIntegration(Object ourAPICapsule) {
//        SpaceXCapsule spaceXCapsule = SpaceXCapsule.from(ourAPICapsule);

//        flatIconFactory.
//                getAPIBinding()
//                .getExecutorService()
//                .submit(() -> spaceXCapsuleService.postCapsule(spaceXCapsule)).get();
//        return null;
    }
}


//                .execute(() -> aggregateOfInvocations());
//https://stackoverflow.com/questions/18730290/what-is-the-difference-between-executorservice-submit-and-executorservice-execut