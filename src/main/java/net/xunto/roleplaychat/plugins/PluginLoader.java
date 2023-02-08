package net.xunto.roleplaychat.plugins;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import net.xunto.roleplaychat.RoleplayChatCore;
import net.xunto.roleplaychat.plugins.exceptions.NotPluginException;
import net.xunto.roleplaychat.plugins.exceptions.PluginLoadException;

public class PluginLoader {
  private final RoleplayChatCore core;

  public PluginLoader(RoleplayChatCore core) {
    this.core = core;
  }

  public void load(String pluginClassName) {
    Class<?> pluginClazz;
    try {
      pluginClazz = Class.forName(pluginClassName);
    } catch (ClassNotFoundException e) {
      throw new PluginLoadException(e);
    }

    Constructor<?> constructor;
    try {
      constructor = pluginClazz.getConstructor();
    } catch (NoSuchMethodException e) {
      throw new PluginLoadException(e);
    }

    Object object;
    try {
      object = constructor.newInstance();
    } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
      throw new PluginLoadException(e);
    }

    if (!(object instanceof IPlugin)) {
      throw new NotPluginException(object);
    }

    IPlugin plugin = (IPlugin) object;

    plugin.init(this.core);
  }
}
