package nelsonalfo.tmdbunittestsapp.command;

/**
 * Created by nelso on 27/12/2017.
 */

public interface Command<T> {
    void run();

    interface Listener{
        void notifyError(String errorStatus);
    }
}
