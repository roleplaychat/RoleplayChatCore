package net.xunto.roleplaychat.plugins;

import net.xunto.roleplaychat.RoleplayChatCore;
import net.xunto.roleplaychat.api.IServer;
import net.xunto.roleplaychat.api.ISpeaker;
import net.xunto.roleplaychat.api.IWorld;
import net.xunto.roleplaychat.api.Position;
import net.xunto.roleplaychat.plugins.exceptions.NotPluginException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PluginLoaderTest {

  private RoleplayChatCore core;
  private PluginLoader loader;

  @Before
  public void setUp() {
    this.core = Mockito.mock(RoleplayChatCore.class);
    this.loader = new PluginLoader(core);
  }

  @Test
  public void testLoadPlugin() {
    this.loader.load(TestPlugin.class.getName());
  }

  @Test(expected = RuntimeException.class)
  public void testNoClass() {
    this.loader.load("non-existent-class");
  }

  @Test(expected = RuntimeException.class)
  public void testClassNoConstructor() {
    this.loader.load(TestPluginNoConstructor.class.getName());
  }

  @Test(expected = NotPluginException.class)
  public void testNotPlugin() {
    this.loader.load(TestNotPlugin.class.getName());
  }
}
