package net.xunto.roleplaychat.plugins;

import net.xunto.roleplaychat.RoleplayChatCore;

public class TestPlugin implements IPlugin {

  @Override
  public void init(RoleplayChatCore core) {
    core.register(PluginLoaderTest.MARKER1);
  }
}
