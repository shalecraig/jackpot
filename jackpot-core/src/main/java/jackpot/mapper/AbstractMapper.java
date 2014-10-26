package jackpot.mapper;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import java.util.Collection;

public abstract class AbstractMapper<IN, OUT> implements Mapper<IN, OUT> {
    private final Function<IN, OUT> mapFunction;

    protected AbstractMapper() {
        this.mapFunction = new Function<IN, OUT>() {
            @Override
            public OUT apply(IN input) {
                return map(input);
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public OUT[] mapMany(IN[] in) {
        // TODO: This code is *totally* invalid.
        OUT out[] = (OUT[]) new Object[in.length];
        for (int i = in.length - 1; i >= 0; --i) {
            out[i] = map(in[i]);
        }
        return out;
    }

    @Override
    public final Collection<OUT> mapMany(Collection<IN> in) {
        return Collections2.transform(in, mapFunction);
    }
}
