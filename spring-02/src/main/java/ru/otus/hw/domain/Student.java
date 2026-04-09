package ru.otus.hw.domain;

public record Student(String firstName, String lastName, int age) {
    public String getFullName() {
        return String.format("%s %s %d лет", firstName, lastName, age);
    }
}
