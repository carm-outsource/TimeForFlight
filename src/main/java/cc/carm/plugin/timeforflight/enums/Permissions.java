package cc.carm.plugin.timeforflight.enums;

public enum Permissions {
    UNLIMITED("timeforflight.unlimited"),
    ALLOW_FLIGHT("timeforflight.allowflight"),
    GET_TIME("timeforflight.getflighttime"),
    ADMIN("timeforflight.admin");

    String permissionNode;

    Permissions(String permissionNode) {
        this.permissionNode = permissionNode;
    }


    public String getPermission() {
        return permissionNode;
    }

}
