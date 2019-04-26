package academy.littlewitch.utils;

public class KeyValuePair<K, V> {
    private K key;
    private V value;

    public KeyValuePair(K k, V v) {
        this.key = k;
        this.value = v;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
