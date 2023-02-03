package net.xunto.roleplaychat.features.middleware.distance.hearing_gm;

import net.xunto.roleplaychat.api.ISpeaker;
import net.xunto.roleplaychat.features.Translations;

public class NoExtraHearingMode implements IHearingMode {
    public static NoExtraHearingMode instance = new NoExtraHearingMode();

    @Override
    public boolean canAvoidHearingRestriction(ISpeaker recipient, ISpeaker speaker) {
        return false;
    }

    @Override
    public String getHumanReadable() {
        return Translations.HEARING_MODE_DEFAULT;
    }
}
