package net.xunto.roleplaychat.features.middleware.remember;

import net.xunto.roleplaychat.api.ISpeaker;
import net.xunto.roleplaychat.features.Translations;
import net.xunto.roleplaychat.features.middleware.distance.Distance;
import net.xunto.roleplaychat.features.middleware.distance.DistanceMiddleware;
import net.xunto.roleplaychat.framework.api.Environment;
import net.xunto.roleplaychat.framework.api.Priority;
import net.xunto.roleplaychat.framework.api.Request;
import net.xunto.roleplaychat.framework.api.Stage;
import net.xunto.roleplaychat.framework.middleware_flow.Flow;

import java.util.HashMap;
import java.util.UUID;

public class RecallDistanceMiddleware extends AbstractRecallMiddleware {
    private HashMap<UUID, Distance> ranges = new HashMap<>();

    @Override
    public Priority getPriority() {
        return Priority.HIGHEST;
    }

    @Override
    public Stage getStage() {
        return Stage.PRE;
    }

    @Override
    public void process(Request request, Environment environment, Flow flow) {
        Distance storedRange = ranges.getOrDefault(request.getRequester().getUniqueID(), null);

        if (isSetRequest(request.getText(), new String[]{"!", "="})) {
            ISpeaker requester = request.getRequester();
            Distance forcedRange = DistanceMiddleware.processDistanceState(request, environment);

            if (storedRange != forcedRange) {
                ranges.put(requester.getUniqueID(), forcedRange);
                sendSetDistanceMessage(requester, forcedRange);
            } else {
                ranges.remove(requester.getUniqueID());
                sendSetMessage(requester, Translations.DISTANCE_RESET);
            }

            return;
        }


        if (storedRange != null)
            environment.getState().setValue(DistanceMiddleware.DISTANCE, storedRange);

        flow.next();
    }

    private void sendSetDistanceMessage(ISpeaker requester, Distance distance) {
        sendSetMessage(requester,
                String.format(Translations.DISTANCE_SET, Translations.stringifyDistance(distance)));
    }
}
