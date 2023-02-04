package net.xunto.roleplaychat.api;

import net.xunto.roleplaychat.framework.text.Text;

public interface ICompat {
    boolean compat(ISpeaker speaker, Text text);
}
