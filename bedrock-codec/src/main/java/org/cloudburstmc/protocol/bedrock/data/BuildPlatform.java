package org.cloudburstmc.protocol.bedrock.data;

public enum BuildPlatform {
    UNKNOWN(-1), GOOGLE(1), IOS(2), OSX(3), AMAZON(4), GEAR_VR(5), HOLOLENS(6), UWP(7),
    WIN32(8), DEDICATED(9), TV_OS(10), SONY(11), NX(12), XBOX(13), WINDOWS_PHONE(14), LINUX(15);

    private static final BuildPlatform[] VALUES = values();
    private final int id;
    BuildPlatform(int id) { this.id = id; }
    public int getId() { return id; }
    public static BuildPlatform from(int id) {
        for (BuildPlatform value : VALUES) if (value.id == id) return value;
        return UNKNOWN;
    }
}
