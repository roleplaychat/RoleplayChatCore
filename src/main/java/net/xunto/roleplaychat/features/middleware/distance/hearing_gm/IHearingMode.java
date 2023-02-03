package net.xunto.roleplaychat.features.middleware.distance.hearing_gm;

import net.xunto.roleplaychat.api.ISpeaker;

public interface IHearingMode {
    boolean canAvoidHearingRestriction(ISpeaker recipient, ISpeaker speaker);

    String getHumanReadable();
}
