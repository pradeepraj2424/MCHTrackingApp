package com.drpradeepepi.mchtrackingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drpradeepepi.mchtrackingapp.ui.theme.MCHTrackingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MCHTrackingAppTheme {
                MCHTrackingApp()
            }
        }
    }
}

@Composable
fun MCHTrackingApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "phc_list"
    ) {
        composable("phc_list") {
            PHCListScreen(navController)
        }
        composable("hsc_list/{phcName}") { backStackEntry ->
            val phcName = backStackEntry.arguments?.getString("phcName") ?: ""
            HSCListScreen(navController, phcName)
        }
        composable("mothers_list/{hscName}") { backStackEntry ->
            val hscName = backStackEntry.arguments?.getString("hscName") ?: ""
            MothersListScreen(navController, hscName)
        }
        composable("mother_detail/{motherName}") { backStackEntry ->
            val motherName = backStackEntry.arguments?.getString("motherName") ?: ""
            MotherDetailScreen(navController, motherName)
        }
        composable("add_follow_up/{motherName}") { backStackEntry ->
            val motherName = backStackEntry.arguments?.getString("motherName") ?: ""
            AddFollowUpScreen(navController, motherName)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PHCListScreen(navController: NavController) {
    val phcList = remember {
        listOf(
            PHC("Thiruviyaru PHC"),
            PHC("Naducauvery PHC"),
            PHC("Kalyanapuram PHC")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PHC List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(phcList) { phc ->
                PHCItem(
                    phc = phc,
                    onClick = {
                        navController.navigate("hsc_list/${phc.name}")
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HSCListScreen(navController: NavController, phcName: String) {
    val hscList = remember {
        listOf(
            HSC("HSC-A"),
            HSC("HSC-B"),
            HSC("HSC-C")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HSC List") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(hscList) { hsc ->
                HSCItem(
                    hsc = hsc,
                    onClick = {
                        navController.navigate("mothers_list/${hsc.name}")
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MothersListScreen(navController: NavController, hscName: String) {
    val mothersList = remember {
        listOf(
            Mother(
                name = "Rani",
                registrationDate = "25 Jan",
                edd = "01-02-2026",
                isHighRisk = true,
                deliveryStatus = "Not Delivered",
                lmp = "01-05-2026",
                polivarys = "Not Delivered",
                age = "25"
            ),
            Mother(
                name = "Meera",
                registrationDate = "15 Feb",
                edd = "15-03-2026",
                isHighRisk = false,
                deliveryStatus = "Not Delivered",
                lmp = "15-06-2026",
                polivarys = "Delivered",
                age = "28"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mothers") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(mothersList) { mother ->
                MotherItem(
                    mother = mother,
                    onClick = {
                        navController.navigate("mother_detail/${mother.name}")
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotherDetailScreen(navController: NavController, motherName: String) {
    val mother = remember {
        Mother(
            name = motherName,
            registrationDate = "25 Jan",
            edd = "01-02-2026",
            isHighRisk = true,
            deliveryStatus = "Not Delivered",
            lmp = "01-05-2026",
            polivarys = "Not Delivered",
            age = "25"
        )
    }

    val followUpList = remember {
        listOf(
            FollowUp(
                date = "01-06-2025",
                bp = "Normal",
                remarks = "Good health"
            ),
            FollowUp(
                date = "15-06-2025",
                bp = "120/80",
                remarks = "Regular checkup"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(motherName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                MotherDetailsCard(mother = mother)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate("add_follow_up/${mother.name}")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text("ADD FOLLOW-UP")
                }
            }

            item {
                Button(
                    onClick = {
                        // Handle mark as delivered
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text("MARK AS DELIVERED")
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Follow-Up",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                FollowUpHeader()
            }

            items(followUpList) { followUp ->
                FollowUpItem(followUp = followUp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFollowUpScreen(navController: NavController, motherName: String) {
    var date by remember { mutableStateOf("") }
    var bp by remember { mutableStateOf("") }
    var remarks by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Follow-up") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = bp,
                onValueChange = { bp = it },
                label = { Text("BP") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = remarks,
                onValueChange = { remarks = it },
                label = { Text("Remarks") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Handle save logic
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Follow-up")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PHCItem(phc: PHC, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = phc.name,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HSCItem(hsc: HSC, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = hsc.name,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotherItem(mother: Mother, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = mother.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = mother.registrationDate,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "EDD: ${mother.edd}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "High Risk: ${if (mother.isHighRisk) "Yes" else "No"}",
                    fontSize = 14.sp,
                    color = if (mother.isHighRisk) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Delivery Status: ${mother.deliveryStatus}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate"
            )
        }
    }
}

@Composable
fun MotherDetailsCard(mother: Mother) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Age: ${mother.age}",
                fontSize = 16.sp
            )
            Text(
                text = "LMP: ${mother.lmp}",
                fontSize = 16.sp
            )
            Text(
                text = "EDD: ${mother.edd}",
                fontSize = 16.sp
            )
            Text(
                text = "High Risk: ${if (mother.isHighRisk) "Yes" else "No"}",
                fontSize = 16.sp,
                color = if (mother.isHighRisk) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Polivarys: ${mother.polivarys}",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun FollowUpHeader() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Date",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "BP",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Remarks",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun FollowUpItem(followUp: FollowUp) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = followUp.date,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = followUp.bp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = followUp.remarks,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// Data Classes
data class PHC(
    val name: String
)

data class HSC(
    val name: String
)

data class Mother(
    val name: String,
    val registrationDate: String,
    val edd: String,
    val isHighRisk: Boolean,
    val deliveryStatus: String,
    val lmp: String,
    val polivarys: String,
    val age: String? = null
)

data class FollowUp(
    val date: String,
    val bp: String,
    val remarks: String
)