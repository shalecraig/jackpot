package jackpot.mapper;

public class ToVoidMapper extends AbstractMapper<Object, Void> {

    @Override
    public Void map(Object o) {
        return null;
    }

    @Override
    public Void[] mapMany(Object[] in) {
        return new Void[0];
    }
}
