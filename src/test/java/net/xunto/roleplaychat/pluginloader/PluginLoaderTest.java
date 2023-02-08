package net.xunto.roleplaychat.pluginloader;

import net.xunto.roleplaychat.RoleplayChatCore;
import net.xunto.roleplaychat.features.middleware.distance.DistanceMiddleware;
import net.xunto.roleplaychat.framework.api.Middleware;
import net.xunto.roleplaychat.pluginloader.exceptions.NotPluginException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PluginLoaderTest {

  public static final Middleware MARKER1 = new DistanceMiddleware();
  public static final Middleware MARKER2 = new DistanceMiddleware();
  private RoleplayChatCore core;
  private PluginLoader loader;

  @Before
  public void setUp() {
    this.core = Mockito.mock(RoleplayChatCore.class);
    this.loader = new PluginLoader(core);
  }

  @Test
  public void testLoadAll() {
    this.loader.loadAll(new String[] {
            TestPlugin.class.getName(),
            TestPlugin2.class.getName()
    });

    Mockito.verify(this.core).register(MARKER1);
    Mockito.verify(this.core).register(MARKER2);
  }

  @Test
  public void testLoadPlugin() {
    this.loader.load(TestPlugin.class.getName());

    Mockito.verify(this.core).register(MARKER1);
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
