package net.xunto.roleplaychat.pluginloader.exceptions;

public class PluginLoadException extends RuntimeException {

  public PluginLoadException(String message) {
    super(message);
  }

  public PluginLoadException(Exception cause) {
    super(cause);
  }
}
