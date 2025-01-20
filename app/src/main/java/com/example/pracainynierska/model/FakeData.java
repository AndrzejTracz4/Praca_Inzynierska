package com.example.pracainynierska.model;

import com.example.pracainynierska.API.model.Task;
import com.example.pracainynierska.dictionary.types.TaskTypes;

public class FakeData {

    public static final Task fakeTask = new Task(
            1,
            TaskTypes.ONE_TIME,
            "Maraton Warszawski",
            "Siema",
            "Trening",
            "Trudny",
            7,
            "dni",
            "2024-12-01",
            "2024-12-02",
            "Nowe"
    );
}
