package entities;

public enum State {
    NEW("New"), BLOCKED("Blocked"), PAID("Paid"), CANCELED("Canceled");


 State(String name) {
        this.name = name;
    }

    String name;

    public static State getByNameIgnoringCase(String name) {
        for (State value : State.values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("State not found " + name);
    }
}
