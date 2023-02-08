package net.xunto.roleplaychat.plugins.exceptions;

public class PluginLoadException extends RuntimeException {

  public PluginLoadException(String message) {
    super(message);
  }

  public PluginLoadException(Exception cause) {
    super(cause);
  }
}
