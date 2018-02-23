package nelsonalfo.tmdbunittestsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Genre {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }
}
