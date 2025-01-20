package com.example.pracainynierska.dictionary

import android.content.Context
import com.example.pracainynierska.R

class RankDictionary(context: Context) {
    val levelNames: Map<Int, String> = context.resources
        .getStringArray(R.array.rank_names)
        .mapIndexed { index, name -> index + 1 to name }
        .toMap()
}
