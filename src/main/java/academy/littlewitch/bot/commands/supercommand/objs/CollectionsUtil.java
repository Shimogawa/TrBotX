package academy.littlewitch.bot.commands.supercommand.objs;

import academy.littlewitch.utils.GoodStrBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsUtil {

    public <T extends Comparable<? super T>> void sort(List<T> list) {
        Collections.sort(list);
    }

    public <T extends Comparable<? super T>> int search(List<T> list, T key) {
        return Collections.binarySearch(list, key);
    }

    public <T extends Comparable<? super T>> T min(Collection<T> collection) {
        return Collections.min(collection);
    }

    public <T extends Comparable<? super T>> T max(Collection<T> collection) {
        return Collections.max(collection);
    }

    public <T> String printArr(T[] arr) {
        GoodStrBuilder sb = new GoodStrBuilder();
        sb.append('[');
        for (T t : arr) {
            String s = t.toString();
            sb.append(s).append(", ");
            if (s.length() > 20)
                sb.append('\n');
        }
        sb.deleteLastChar().deleteLastChar().append(']');
        return sb.toString();
    }
}
