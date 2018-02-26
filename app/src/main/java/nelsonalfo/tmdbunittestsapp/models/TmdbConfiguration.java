package nelsonalfo.tmdbunittestsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class TmdbConfiguration {

    @SerializedName("images")
    @Expose
    public Images images;
    @SerializedName("change_keys")
    @Expose
    public List<String> changeKeys = null;

    @Override
    public String toString() {
        return "TmdbConfiguration{" +
                "images=" + images +
                ", changeKeys=" + changeKeys +
                '}';
    }
}
