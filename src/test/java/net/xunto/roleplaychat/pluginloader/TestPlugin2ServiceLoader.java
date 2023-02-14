package net.xunto.roleplaychat.pluginloader;

import com.google.auto.service.AutoService;
import net.xunto.roleplaychat.RoleplayChatCore;

@AutoService(IPlugin.class)
public class TestPlugin2ServiceLoader implements IPlugin {
    @Override
    public void init(RoleplayChatCore core) {
        core.register(PluginLoaderTest.MARKER2);
    }
}
