package net.xunto.roleplaychat.features.middleware.distance.hearing_gm;

import net.xunto.roleplaychat.api.ISpeaker;
import net.xunto.roleplaychat.features.Translations;

public class InfiniteHearingMode implements IHearingMode {
    public static InfiniteHearingMode instance = new InfiniteHearingMode();

    @Override
    public boolean canAvoidHearingRestriction(ISpeaker recipient, ISpeaker speaker) {
        return true;
    }

    @Override
    public String getHumanReadable() {
        return Translations.HEARING_MODE_INFINITY;
    }
}
