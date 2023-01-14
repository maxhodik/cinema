package entities;

public enum WeekDays {
    SUNDAY("sunday", "НЕДІЛЯ", 6),
    MONDAY("monday", "ПОНЕДІЛОК", 0),
    TUESDAY("tuesday", "ВІВТОРОК", 1),
    WEDNESDAY("wednesday", "СЕРЕДА", 2),
    THURSDAY("thursday", "ЧЕТВЕР", 3),
    FRIDAY("friday", "П'ЯТНИЦЯ", 4),
    SATURDAY("saturday", "СУБОТА", 5);


    final String name;
    final String nameUkr;
    final int number;

    WeekDays(String name, String nameUkr, int number) {
        this.name = name;
        this.nameUkr = nameUkr;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public static WeekDays getByNameIgnoringCase(String name) {
        for (WeekDays value : WeekDays.values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Day not found " + name);
    }

    public static boolean contains(String name) {
        try {
            getByNameIgnoringCase(name);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
