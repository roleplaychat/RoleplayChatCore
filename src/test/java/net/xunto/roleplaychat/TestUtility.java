package net.xunto.roleplaychat;

import net.xunto.roleplaychat.framework.middleware_flow.Flow;

import java.util.ArrayList;

public class TestUtility {
    public final static Flow DO_NOTHING = new Flow(
            new ArrayList<>(),
            null,
            null,
            (e) -> {
            }
    );
}
