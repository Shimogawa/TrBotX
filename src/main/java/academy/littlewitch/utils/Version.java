package academy.littlewitch.utils;

public class Version implements Comparable<Version> {

    private int major;
    private int minor;
    private int maintainance;
    private int build;

//    private boolean alpha;
//    private boolean beta;

    private Version() {}

    public Version(int major, int minor) {
        this(major, minor, -1, -1);
    }

    public Version(int major, int minor, int maintainance) {
        this(major, minor, maintainance, -1);
    }

    public Version(int major, int minor, int maintainance, int build) {
        this.major = major;
        this.minor = minor;
        this.maintainance = maintainance;
        this.build = build;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getMaintainance() {
        return maintainance;
    }

    public int getBuild() {
        return build;
    }

    @Override
    public int compareTo(Version o) {
        return  this.major != o.major                   ? this.major - o.major
                : this.minor != o.minor                 ? this.minor - o.minor
                : this.maintainance != o.maintainance   ? this.maintainance - o.maintainance
                : this.build != o.build                 ? this.build - o.build
                : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb      .append(major)
                .append('.')
                .append(minor);
        if (maintainance != -1)
            sb.append('.').append(maintainance);
        if (build != -1)
            sb.append('.').append(build);
        return sb.toString();
    }

    public String toString(String prefix) {
        return prefix + toString();
    }

    public String versionString() {
        return toString("v");
    }

    public static Version parse(String s) {
        int startIdx = 0;
        for (; startIdx < s.length(); startIdx++) {
            if (Character.isDigit(s.charAt(startIdx))) {
                break;
            }
        }
        if (startIdx == s.length())
            throw new IllegalArgumentException();
        s = s.substring(startIdx);
        String[] versions = s.split("\\.");
        if (versions.length < 2 || versions.length > 4)
            throw new IllegalArgumentException();
        Version v = new Version();
        try {
            v.major = Integer.parseInt(versions[0]);
            v.minor = Integer.parseInt(versions[1]);
            if (versions.length >= 3)
                v.maintainance = Integer.parseInt(versions[2]);
            if (versions.length == 4)
                v.build = Integer.parseInt(versions[3]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
        return v;
    }
}
