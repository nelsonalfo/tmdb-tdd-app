package nelsonalfo.tmdbunittestsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductionCountry {

    @SerializedName("iso_3166_1")
    @Expose
    public String countryCode;
    @SerializedName("name")
    @Expose
    public String name;
}
