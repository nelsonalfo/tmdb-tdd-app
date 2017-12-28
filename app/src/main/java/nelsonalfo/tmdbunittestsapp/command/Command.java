package nelsonalfo.tmdbunittestsapp.command;

/**
 * Created by nelso on 27/12/2017.
 */

public abstract class Command<T> {
    public abstract void run();

    public abstract void setListener(Listener<T> listener);

    public interface Listener<T> {
        void onReturnValue(T value);

        void onError();
    }
}
