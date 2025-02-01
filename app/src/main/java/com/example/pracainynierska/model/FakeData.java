package com.example.pracainynierska.model;

import com.example.pracainynierska.API.model.Category;
import com.example.pracainynierska.API.model.Statistic;
import com.example.pracainynierska.API.model.Task;
import com.example.pracainynierska.dictionary.types.TaskTypes;

import java.util.ArrayList;
import java.util.Collections;

public class FakeData {

    public static final Task fakeTask = new Task(
            1,
            TaskTypes.ONE_TIME,
            "Maraton Warszawski",
            "Siema",
            new Category(1,"Test", new ArrayList<>(Collections.singleton(new Statistic(1, "Statistic", "knowledge_bar", 80, 1)))
            ),
            "Trudny",
            7,
            "dni",
            "2024-12-01",
            "2024-12-02",
            "Nowe"
    );
}
