package nelsonalfo.tmdbunittestsapp.api;

/**
 * Created by nelso on 28/12/2017.
 */

public interface ApiStatus {
    String NO_RESULT = "NO_RESULT";
    String SERVER_ERROR = "SERVER_ERROR";
    String CLIENT_ERROR = "CLIENT_ERROR";
    String NETWORK_ERROR = "NETWORK_ERROR";

    interface Code {
        int SERVER_ERROR = 500;
        int CLIENT_ERROR = 401;
        int UNSATISFIABLE_REQUEST_ERROR = 504;
    }
}
