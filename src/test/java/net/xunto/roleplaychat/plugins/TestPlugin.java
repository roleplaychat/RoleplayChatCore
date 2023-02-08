package net.xunto.roleplaychat.plugins;

import net.xunto.roleplaychat.RoleplayChatCore;
import net.xunto.roleplaychat.features.middleware.distance.DistanceMiddleware;
import net.xunto.roleplaychat.framework.api.Middleware;

public class TestPlugin implements IPlugin {
  public static final Middleware MARKER = new DistanceMiddleware();

  @Override
  public void init(RoleplayChatCore core) {
    core.register(MARKER);
  }
}
