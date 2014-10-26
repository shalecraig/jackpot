package jackpot.mapper;

public class Mappers {
    private Mappers() {
    }

    public static boolean canMap(Class<?> from, Class<?> to) {
        // TODO: there's got to be a better way to do this.
        return to.equals(Void.class) || to.equals(Void.TYPE) || to.equals(from);
    }

    public static <IN, OUT> Mapper<IN, OUT> map(Class<IN> from, Class<OUT> to) {
        // TODO: there's got to be a better way to do this.
        if (to.equals(Void.class) || to.equals(Void.TYPE)) {
            return (Mapper<IN, OUT>) new ToVoidMapper();
        } else if (to.equals(from)) { // i.e. in == out
            return (Mapper<IN, OUT>) new PassthroughMapper<IN>();
        } else {
            return null;
        }
    }
}
