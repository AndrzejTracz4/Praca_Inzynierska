import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.dictionary.ViewRoutes
import com.example.pracainynierska.ui_view_components.components.BottomMenuItem
import com.example.pracainynierska.ui_view_components.components.navigateIfNotCurrent


@Composable
fun BottomMenu(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomAppBar(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            modifier = Modifier
                .padding(0.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .height(60.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF070709),
                            Color(0xFF1B1871)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
        ) {
            BottomMenuItem(
                icon = R.drawable.stats,
                label = stringResource(R.string.categories),
                onClick = { navigateIfNotCurrent(navController, ViewRoutes.CATEGORIES.viewName) },
                modifier = Modifier.weight(1f)
            )

            BottomMenuItem(
                icon = R.drawable.achievements,
                label = stringResource(R.string.achievements),
                onClick = { navigateIfNotCurrent(navController, ViewRoutes.ACHIEVEMENTS.viewName) },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.weight(1f))

            BottomMenuItem(
                icon = R.drawable.calendar,
                label = stringResource(R.string.calendar),
                onClick = { navigateIfNotCurrent(navController, ViewRoutes.CALENDAR.viewName) },
                modifier = Modifier.weight(1f)
            )

            BottomMenuItem(
                icon = R.drawable.shop,
                label = stringResource(R.string.shop),
                onClick = { navigateIfNotCurrent(navController, ViewRoutes.SHOP.viewName) },
                modifier = Modifier.weight(1f)
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset(y = (-25).dp)
                .size(60.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF070709),
                            Color(0xFF1B1871)
                        ),
                        start = Offset(-10f, 0f),
                        end = Offset(200f, 50f)
                    )
                )
        ) {
            IconButton(onClick = { navigateIfNotCurrent(navController, ViewRoutes.ADDTASK.viewName) }) {
                Image(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = stringResource(R.string.icon_add_task_description),
                    modifier = Modifier
                        .size(30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}
