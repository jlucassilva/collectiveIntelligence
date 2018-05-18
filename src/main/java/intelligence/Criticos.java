package intelligence;

public enum Criticos {

    Rose("Lisa Rose"),
    Seymour("Gene Seymour"),
    Phillips("Michael Phillips"),
    Puig("Claudia Puig"),
    LaSalle("Mick LaSalle"),
    Matthews("Jack Matthews"),
    Toby("Toby");

    private final String name;

    Criticos(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
