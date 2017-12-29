package nelsonalfo.tmdbunittestsapp.command;

/**
 * Created by nelso on 27/12/2017.
 */

public interface Command<T> {
    void run();

    void setListener(Listener<T> listener);

    interface Listener<T>{
        void receiveValue(T value);

        void notifyError(String errorStatus);
    }
}
