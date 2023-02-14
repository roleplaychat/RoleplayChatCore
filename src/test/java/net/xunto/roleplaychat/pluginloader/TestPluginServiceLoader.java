package net.xunto.roleplaychat.pluginloader;

import net.xunto.roleplaychat.RoleplayChatCore;
import com.google.auto.service.AutoService;

@AutoService(IPlugin.class)
public class TestPluginServiceLoader implements IPlugin {
    @Override
    public void init(RoleplayChatCore core) {
        core.register(PluginLoaderTest.MARKER1);
    }
}
