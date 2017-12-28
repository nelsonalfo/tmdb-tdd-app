package nelsonalfo.tmdbunittestsapp.command;

/**
 * Created by nelso on 27/12/2017.
 */

public abstract class Command<T> {
    public abstract T returnValue();

    public abstract void run();

    public abstract void setListener(Listener<T> listener);

    public interface Listener<T>{
        void recieveValue(T value);
    }
}
