public class Pair<K,V> {

    private K first;

    public K getFirst() { return first; }


    private V second;

    public V getSecond() { return second; }

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

}
