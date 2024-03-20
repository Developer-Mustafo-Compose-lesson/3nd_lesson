package uz.coder.jklesson3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import uz.coder.jklesson3.ui.theme.JkLesson3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.GRAY))
        setContent {
            JkLesson3Theme {
                Column {
                    Scaffold(modifier = Modifier.fillMaxWidth(), topBar = { MyTopBar(appName = stringResource(id = R.string.app_name), modifier = Modifier) }){
                        MainScreen(it)
                    }
                }
            }
        }
    }
    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun MainScreen(values: PaddingValues) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(values)){
            val scope = rememberCoroutineScope()
            val tabs = listOf(
                TabItem(title = home, iconSelected = Icons.Filled.Home, iconUnselected = Icons.Outlined.Home),
                TabItem(title = profile, iconSelected = Icons.Filled.AccountCircle, iconUnselected = Icons.Outlined.AccountCircle),
                TabItem(title = settings, iconSelected = Icons.Filled.Settings, iconUnselected = Icons.Outlined.Settings)
            )
            val state = rememberPagerState()
            ScrollableTabRow(selectedTabIndex = state.currentPage,modifier = Modifier.height(80.dp)) {
                    tabs.forEachIndexed{index,item->
                        Tab(selected = index == state.currentPage, text = { Text(text = item.title) },icon = { when(index == state.currentPage){
                            true -> Icon(imageVector = item.iconSelected, contentDescription = "")
                            false -> Icon(imageVector = item.iconUnselected, contentDescription = "")
                        } }, onClick = { scope.launch{ state.animateScrollToPage(index) } })
                    }
            }
            HorizontalPager(count = tabs.size, modifier = Modifier.fillMaxSize(),state = state) {
                Text(text = tabs[it].title)
            }
        }   
    }
    private val home by lazy { getString(R.string.home) }
    private val settings by lazy { getString(R.string.settings) }
    private val profile by lazy { getString(R.string.profile) }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MyTopBar(appName: String, modifier: Modifier) {
        val showMenu = remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current
        TopAppBar(title = { Text(text = appName) }, modifier = modifier, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue, titleContentColor = Color.White), actions = { IconButton(onClick = { showMenu.value = !showMenu.value }) {
            Icon(Icons.Default.Menu, contentDescription ="", tint = Color.White)
        }
            DropdownMenu(expanded = showMenu.value, onDismissRequest = { showMenu.value = false }) {
                DropdownMenuItem(leadingIcon = { Icon(Icons.Default.Home, "") }, text = { Text(text = home) }, onClick = {
                    showMenu.value = false
                    Toast.makeText(context, home, Toast.LENGTH_SHORT).show()
                })
                DropdownMenuItem(leadingIcon = { Icon(Icons.Default.Person, "") }, text = { Text(text = profile) }, onClick = {
                    showMenu.value = false
                    Toast.makeText(context, profile, Toast.LENGTH_SHORT).show()
                })
                DropdownMenuItem(leadingIcon = { Icon(Icons.Default.Settings, "") }, text = { Text(text = settings) }, onClick = {
                    showMenu.value = false
                    Toast.makeText(context, settings, Toast.LENGTH_SHORT).show()
                })
            }

        })

    }
}
data class TabItem(
    val title: String,
    val iconSelected: ImageVector,
    val iconUnselected: ImageVector
)
