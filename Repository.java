package lesson1;

public interface Repository<T> {
    void save(T text);
    T load();

}
