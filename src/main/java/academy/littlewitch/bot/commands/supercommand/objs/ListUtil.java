package academy.littlewitch.bot.commands.supercommand.objs;

import java.util.ArrayList;

public class ListUtil {

    private ArrayList<ArrayList> lists = new ArrayList<>();

    private int size = 0;

    public <T> int createList(Class<T> clazz) {
        size++;
        lists.add(new ArrayList<T>());
        return size - 1;
    }

    @SuppressWarnings("unchecked")
    public <T> ArrayList<T> getList(int idx, Class<T> clazz) {
        return (ArrayList<T>) lists.get(idx);
    }

    public void clear() {
        lists.clear();
    }
}
