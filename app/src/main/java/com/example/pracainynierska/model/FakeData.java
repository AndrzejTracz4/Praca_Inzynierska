package com.example.pracainynierska.model;

import com.example.pracainynierska.ui_view_components.components.TaskMode;

public class FakeData {

    public static final Task fakeTask = new Task(
            1,
            "Przebiegnij maraton",
            "Trudny",
            "Trening",
            "2024-12-01",
            "2024-12-31",
            7,
            "dni",
            TaskMode.CYKLICZNE,
            "Aktywne",
            "Opis"
    );
}
