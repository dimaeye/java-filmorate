package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Friends {
    @NonNull
    private int userId;
    @NonNull
    private int friendId;
    @NonNull
    private FriendshipStatus friendshipStatus;
}
