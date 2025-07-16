package pl.edu.pg.chor.strefa_chorzysty.user.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Email {
    private final String value;

    public Email(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Email && Objects.equals(value, ((Email) o).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}