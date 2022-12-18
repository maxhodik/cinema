package entities;

public enum WeekDays {
    SUNDAY("sunday",6), MONDAY("monday",0), TUESDAY("tuesday",1), WEDNESDAY("wednesday",2),
    THURSDAY("thursday",3), FRIDAY("friday",4), SATURDAY("saturday",5);


    final String name;
    final int number;

    WeekDays(String name, int number) {
        this.name = name;
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
        throw new IllegalArgumentException("Role not found " + name);
    }
    }
