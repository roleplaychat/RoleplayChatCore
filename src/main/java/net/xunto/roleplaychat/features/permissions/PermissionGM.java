package net.xunto.roleplaychat.features.permissions;

import net.xunto.roleplaychat.api.IPermission;

public class PermissionGM implements IPermission {
    public static PermissionGM instance = new PermissionGM();

    @Override
    public String getName() {
        return "gm";
    }

    @Override
    public String getDescription() {
        return "Main GM permission.";
    }
}
