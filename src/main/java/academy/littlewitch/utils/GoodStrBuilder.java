package academy.littlewitch.utils;

public class GoodStrBuilder implements CharSequence {
    private StringBuilder sb;

    public GoodStrBuilder() {
        sb = new StringBuilder();
    }

    public GoodStrBuilder append(int i) {
        sb.append(i);
        return this;
    }

    public GoodStrBuilder append(char c) {
        sb.append(c);
        return this;
    }

    public GoodStrBuilder append(long l) {
        sb.append(l);
        return this;
    }

    public GoodStrBuilder append(String s) {
        sb.append(s);
        return this;
    }

    public GoodStrBuilder append(Object o) {
        sb.append(o);
        return this;
    }

    public GoodStrBuilder append(String... s) {
        for (int i = 0; i < s.length; i++) {
            sb.append(s[i]);
        }
        return this;
    }

    public GoodStrBuilder append(Object... o) {
        for (int i = 0; i < o.length; i++) {
            sb.append(o[i]);
        }
        return this;
    }

    public GoodStrBuilder insert(int idx, char c) {
        sb.insert(idx, c);
        return this;
    }

    public GoodStrBuilder newLine() {
        sb.append('\n');
        return this;
    }

    public GoodStrBuilder writeLine(String s) {
        sb.append(s);
        sb.append('\n');
        return this;
    }

    public GoodStrBuilder writeLine(String format, Object... args) {
        sb.append(String.format(format, args));
        return this;
    }

    public GoodStrBuilder write(String s) {
        sb.append(s);
        return this;
    }

    public GoodStrBuilder deleteCharAt(int index) {
        sb.deleteCharAt(index);
        return this;
    }

    public GoodStrBuilder deleteLastChar() {
        sb.deleteCharAt(sb.length() - 1);
        return this;
    }

    public GoodStrBuilder replaceCharAt(int idx, char rep) {
        sb.deleteCharAt(idx);
        sb.insert(idx, rep);
        return this;
    }

    public GoodStrBuilder replaceLastChar(char rep) {
        sb.deleteCharAt(sb.length() - 1);
        sb.append(rep);
        return this;
    }

    public int length() {
        return sb.length();
    }

    @Override
    public char charAt(int index) {
        return sb.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return sb.subSequence(start, end);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
