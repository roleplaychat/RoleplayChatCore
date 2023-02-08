package net.xunto.roleplaychat.plugins;

import net.xunto.roleplaychat.RoleplayChatCore;

public class TestPlugin2 implements IPlugin {
  @Override
  public void init(RoleplayChatCore core) {
    core.register(PluginLoaderTest.MARKER2);
  }
}
