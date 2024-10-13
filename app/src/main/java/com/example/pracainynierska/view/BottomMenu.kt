import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pracainynierska.R
import com.example.pracainynierska.repository.UserRepository
import com.example.pracainynierska.viewmodel.LoginViewModel
import com.example.pracainynierska.viewmodel.LoginViewModelFactory

@Composable
fun BottomMenu(userRepository: UserRepository, userUUID: String?) {
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(userRepository)
    )
    BottomAppBar(
        containerColor = Color(0x4DFFFFFF),
        contentColor = Color.White,
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .height(60.dp)
    ) {
        Spacer(modifier = Modifier.width(10.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .clickable { /*TODO*/ }
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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .clickable { /*TODO*/ }
        ) {
            Image(
                painter = painterResource(id = R.drawable.book_alt),
                contentDescription = "Stats",
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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "AddTask",
                    modifier = Modifier
                        .size(24.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .clickable { /*TODO*/ }
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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .clickable { /*TODO*/ }
        ) {
            Image(
                painter = painterResource(id = R.drawable.shopping_cart),
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
}
