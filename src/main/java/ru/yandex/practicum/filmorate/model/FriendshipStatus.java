package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.util.Objects;

@Data
public class FriendshipStatus {
    @NonNull
    private int id;
    @NonNull
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendshipStatus that = (FriendshipStatus) o;
        return id == that.id && status.equals(that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }
}
