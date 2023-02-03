package net.xunto.roleplaychat.features.middleware.distance.hearing_gm;

import net.xunto.roleplaychat.api.ISpeaker;
import net.xunto.roleplaychat.features.Translations;

public class DistanceHearingMode implements IHearingMode {
    private int distance;

    public DistanceHearingMode(int distance) {
        this.distance = distance;
    }

    @Override
    public boolean canAvoidHearingRestriction(ISpeaker recipient, ISpeaker speaker) {
        return speaker.getPosition().distance(recipient.getPosition()) <= distance;
    }

    @Override
    public String getHumanReadable() {
        return String.format(Translations.HEARING_MODE_DISTANCE, distance);
    }
}
