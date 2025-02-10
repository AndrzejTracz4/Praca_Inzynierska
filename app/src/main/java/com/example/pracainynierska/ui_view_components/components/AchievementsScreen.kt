package com.example.pracainynierska.ui_view_components.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pracainynierska.API.model.Achievement
import com.example.pracainynierska.dictionary.AchievementStatus
import com.example.pracainynierska.view_model.AchievementViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AchievementsScreen(
    viewModel: AchievementViewModel,
    completedTasks: Int
) {

    var achievements by remember { mutableStateOf(viewModel.getPlayerAchievements()) }

    var claimableAchievements by remember { mutableStateOf(emptyList<Achievement>()) }
    var incompleteAchievements by remember { mutableStateOf(emptyList<Achievement>()) }
    var claimedAchievements by remember { mutableStateOf(emptyList<Achievement>()) }

    fun updateAchievements() {
        claimableAchievements = achievements.filter { achievement ->
            val progressValue = completedTasks.toFloat() / achievement.requiredValue
            !achievement.completed && progressValue >= 1.0f
        }
        incompleteAchievements = achievements.filter { achievement ->
            val progressValue = completedTasks.toFloat() / achievement.requiredValue
            !achievement.completed && progressValue < 1.0f
        }
        claimedAchievements = achievements.filter { achievement ->
            achievement.completed
        }
    }

    LaunchedEffect(achievements, completedTasks) {
        updateAchievements()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        if (claimableAchievements.isNotEmpty()) {
            item { SectionTitle(AchievementStatus.CLAIMABLE.displayName) }
            items(claimableAchievements) { achievement ->
                AchievementCard(achievement, completedTasks) {
                    viewModel.claimAchievement(achievement.id, {
                        Log.d("Achievement", "Claim success - reloading")
                        achievements = viewModel.getPlayerAchievements()
                        updateAchievements()
                    }, {
                        Log.d("Achievement", "Claim failed")
                    })
                }
            }
        }

        if (incompleteAchievements.isNotEmpty()) {
            item { SectionTitle(AchievementStatus.INCOMPLETE.displayName) }
            items(incompleteAchievements) { achievement ->
                AchievementCard(achievement, completedTasks) { }
            }
        }

        if (claimedAchievements.isNotEmpty()) {
            item { SectionTitle(AchievementStatus.CLAIMED.displayName) }
            items(claimedAchievements) { achievement ->
                AchievementCard(achievement, completedTasks) { }
            }
        }
        item { Spacer(modifier = Modifier.height(70.dp)) }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}