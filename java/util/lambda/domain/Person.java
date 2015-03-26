import java.util.function.Function;

public final class Person {

    public static final Function<Person, String> FIRST = person ->  person.first;

    public static final Function<Person, String> LAST = person ->  person.last;

    public static final Function<Person,Integer> AGE = person -> person.age;
    
    private final String first;
    private final String last;
    private final int age;

    public Person(String first, String last, int age) {
        this.first = first;
        this.last = last;
        this.age = age;
    }

    public Person(String first, String last) {
        this(first, last, 100);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person");
        sb.append("{first='").append(this.first).append('\'');
        sb.append(", last='").append(this.last).append('\'');
        sb.append(", age=").append(this.age);
        sb.append('}');
        return sb.toString();
    }
}
