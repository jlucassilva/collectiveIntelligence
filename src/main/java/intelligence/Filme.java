package intelligence;

public enum Filme {
    LADY_WATER("Lady in the Water"),
    SNAKES_ON_A_PLANE("Snakes on a Plane"),
    JUST_MY_LUCK("Just My Luck"),
    SUPERMAN_RETURNS("Superman Returns"),
    NIGHT_LISTENER("The Night Listener"),
    YOU_ME_DUPREE("You, Me and Dupree");

    private final String value;

    Filme(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
