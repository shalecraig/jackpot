package jackpot.mapper;

import java.util.Collection;

public class PassthroughMapper<T> implements Mapper<T, T> {
    @Override
    public T map(T t) {
        return t;
    }

    @Override
    public T[] mapMany(T[] in) {
        return in;
    }

    @Override
    public Collection<T> mapMany(Collection<T> in) {
        return in;
    }
}
