package jackpot.mapper;

import java.util.Collection;

public interface Mapper<IN, OUT> {
    OUT map(IN in);

    OUT[] mapMany(IN[] in);

    Collection<OUT> mapMany(Collection<IN> in);
}
