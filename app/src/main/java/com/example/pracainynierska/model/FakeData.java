package com.example.pracainynierska.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.pracainynierska.API.model.Category;
import com.example.pracainynierska.API.model.Reward;
import com.example.pracainynierska.API.model.Statistic;
import com.example.pracainynierska.API.model.Task;
import com.example.pracainynierska.dictionary.types.TaskType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class FakeData {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static final Task fakeTask = new Task(
            1,
            TaskType.ONE_TIME.getKey(),
            "Maraton Warszawski",
            "Maraton Warszawski",
            new Category(1, "Test", new ArrayList<Statistic>()),
            "HARD",
            "NEW",
            new Reward(100, 20),
            getStartDateTime(),
            getStartDateTime(),
            getEndDateTime(),
            "",
            "dni",
            0
    );

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getStartDateTime() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return startOfDay.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getEndDateTime() {
        LocalDate today = LocalDate.now();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return endOfDay.format(formatter);
    }
}
