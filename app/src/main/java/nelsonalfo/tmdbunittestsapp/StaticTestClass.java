package nelsonalfo.tmdbunittestsapp;

/**
 * Created by nelso on 26/12/2017.
 */

class StaticTestClass {
    public String returnFormattedValue() {
        return "<" + StaticTestClass.getStubValue() + ">";
    }

    public static String getStubValue() {
        return "stubValue";
    }
}
