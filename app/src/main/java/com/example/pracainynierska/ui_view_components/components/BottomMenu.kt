import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavController
import com.example.pracainynierska.R

@Composable
fun BottomMenu(navController: NavController) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomAppBar(
            containerColor = Color.Transparent, // Set to transparent to apply custom background
            contentColor = Color.White,
            modifier = Modifier
                .padding(horizontal = 0.dp, vertical = 0.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .height(60.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF070709), // Start color (Center)
                            Color(0xFF1B1871)  // End color (Bottom-right)
                        ),
                        start = Offset(0f, 0f), // Start near the center (x, y)
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY) // End at the bottom-right
                    )
                )
        ) {
            Spacer(modifier = Modifier.width(10.dp))

            // przycisk statystyk
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        val currentRoute = navController.currentDestination?.route
                        if (currentRoute != "StatisticView") {
                            navController.navigate("StatisticView")
                        }
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.stats),
                    contentDescription = "Stats",
                    modifier = Modifier
                        .size(24.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Statystyki",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 9.sp,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(3f, 1f),
                            blurRadius = 3f
                        )
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // przycisk zadania
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable { /*TODO*/ }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.book_alt),
                    contentDescription = "Tasks",
                    modifier = Modifier
                        .size(24.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Zadania",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 9.sp,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(3f, 1f),
                            blurRadius = 3f
                        )
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // kolumna do plusa
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // przycisk do kalendarza
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        val currentRoute = navController.currentDestination?.route
                        if (currentRoute != "CalendarsView") {
                            navController.navigate("CalendarsView")
                        }
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = "Calendar",
                    modifier = Modifier
                        .size(24.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Kalendarz",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 9.sp,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(3f, 1f),
                            blurRadius = 3f
                        )
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            //przycisk do sklepu
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        val currentRoute = navController.currentDestination?.route
                        if (currentRoute != "ShopView") {
                            navController.navigate("ShopView")
                        }
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.shop),
                    contentDescription = "Shop",
                    modifier = Modifier
                        .size(24.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Sklep",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 9.sp,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(3f, 1f),
                            blurRadius = 3f
                        )
                    )
                )
            }

            Spacer(modifier = Modifier.width(10.dp))
        }

        // przyscisk do dodawania zadania
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset(y = (-25).dp)
                .size(60.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF070709), // Start color (center-ish)
                            Color(0xFF1B1871)  // End color (bottom-right)
                        ),
                        start = Offset(-10f, 0f), // Adjusted to start from near center
                        end = Offset(200f, 50f)    // End at the bottom-right
                    )
                )
        ) {
            IconButton(onClick = {
                val currentRoute = navController.currentDestination?.route
                if (currentRoute != "AddTaskView") {
                    navController.navigate("AddTaskView")
                }
            }) {
                Image(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "AddTask",
                    modifier = Modifier
                        .size(30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}
