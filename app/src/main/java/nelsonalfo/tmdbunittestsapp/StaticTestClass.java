package nelsonalfo.tmdbunittestsapp;

/**
 * Created by nelso on 26/12/2017.
 */

class StaticTestClass {
    public String returnFormattedValue() {
        return "<" + StaticTestClass.getDummyValue() + ">";
    }

    public static String getDummyValue() {
        return "stubValue";
    }
}
