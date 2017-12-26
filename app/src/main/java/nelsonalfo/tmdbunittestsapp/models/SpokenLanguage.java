package nelsonalfo.tmdbunittestsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpokenLanguage {

    @SerializedName("iso_639_1")
    @Expose
    public String languageCode;
    @SerializedName("name")
    @Expose
    public String name;

}
