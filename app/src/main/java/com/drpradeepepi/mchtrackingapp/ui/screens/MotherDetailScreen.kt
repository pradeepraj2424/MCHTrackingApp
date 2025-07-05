package com.drpradeepepi.mchtrackingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.BabyChangingStation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.drpradeepepi.mchtrackingapp.viewmodel.MotherViewModel
import com.drpradeepepi.mchtrackingapp.viewmodel.FollowUpViewModel
import com.drpradeepepi.mchtrackingapp.ui.components.FollowUpItem
import com.drpradeepepi.mchtrackingapp.data.extensions.toFollowUp
import com.drpradeepepi.mchtrackingapp.data.entity.FollowUpEntity
import com.drpradeepepi.mchtrackingapp.data.entity.MotherEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotherDetailScreen(
    navController: NavController,
    motherName: String,
    motherViewModel: MotherViewModel,
    followUpViewModel: FollowUpViewModel
) {
    val mother by motherViewModel.selectedMother.collectAsState()
    val followUps by followUpViewModel.followUpList.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showDeleteFollowUpDialog by remember { mutableStateOf(false) }
    var followUpToDelete by remember { mutableStateOf<FollowUpEntity?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(motherName) {
        isLoading = true
        motherViewModel.loadMotherByName(motherName)
        followUpViewModel.loadFollowUpsByMother(motherName)
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = mother?.name ?: "Mother Details",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (mother != null) {
                        IconButton(
                            onClick = {
                                navController.navigate("edit_mother/${mother!!.name}")
                            }
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Mother")
                        }
                        IconButton(
                            onClick = { showDeleteDialog = true }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Mother")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            if (mother != null) {
                FloatingActionButton(
                    onClick = { navController.navigate("add_follow_up/$motherName") },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Follow-up",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Loading mother details...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else if (mother == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Mother not found",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "The requested mother record could not be found.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController.popBackStack() }
                    ) {
                        Text("Go Back")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    // Mother Basic Information Card
                    MotherInfoCard(mother = mother!!)
                }

                item {
                    // Pregnancy Status Card
                    PregnancyStatusCard(mother = mother!!)
                }

                item {
                    // Follow-ups Section Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Follow-ups",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "${followUps.size} records",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }

                if (followUps.isEmpty()) {
                    item {
                        EmptyFollowUpCard()
                    }
                } else {
                    // Fixed: Changed visitDate to date and added explicit type annotation
                    items(followUps.sortedByDescending { it.date }) { followUpEntity ->
                        val followUp = followUpEntity.toFollowUp()
                        FollowUpItem(
                            followUp = followUp,
                            onEdit = {
                                navController.navigate("edit_follow_up/$motherName/${followUp.id}")
                            },
                            onDelete = {
                                followUpToDelete = followUpEntity
                                showDeleteFollowUpDialog = true
                            }
                        )
                    }
                }
            }
        }

        // Delete Confirmation Dialogs
        DeleteMotherDialog(
            showDialog = showDeleteDialog,
            motherName = mother?.name ?: "",
            onConfirm = {
                mother?.let { motherEntity ->
                    motherViewModel.deleteMother(motherEntity)
                    navController.popBackStack()
                }
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )

        DeleteFollowUpDialog(
            showDialog = showDeleteFollowUpDialog,
            onConfirm = {
                followUpToDelete?.let { followUpEntity ->
                    followUpViewModel.deleteFollowUp(followUpEntity)
                }
                showDeleteFollowUpDialog = false
                followUpToDelete = null
            },
            onDismiss = {
                showDeleteFollowUpDialog = false
                followUpToDelete = null
            }
        )
    }
}

@Composable
private fun MotherInfoCard(mother: MotherEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = mother.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${mother.age} years",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Information Grid
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InfoRowWithIcon(
                    icon = Icons.Default.Phone,
                    label = "Phone",
                    value = mother.phone
                )
                InfoRowWithIcon(
                    icon = Icons.Default.LocationOn,
                    label = "Address",
                    value = mother.address
                )
                InfoRowWithIcon(
                    icon = Icons.Default.Person,
                    label = "Husband",
                    value = mother.husbandName
                )
                InfoRowWithIcon(
                    icon = Icons.Default.LocationOn,
                    label = "HSC",
                    value = mother.hscName
                )
            }
        }
    }
}

@Composable
private fun PregnancyStatusCard(mother: MotherEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Pregnancy Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PregnancyInfoItem(
                    title = "Gravida",
                    value = mother.gravida.toString(),
                    icon = Icons.Default.BabyChangingStation
                )
                PregnancyInfoItem(
                    title = "Para",
                    value = mother.para.toString(),
                    icon = Icons.Default.BabyChangingStation
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoRowWithIcon(
                    icon = Icons.Default.CalendarMonth,
                    label = "LMP",
                    value = mother.lmp
                )
                InfoRowWithIcon(
                    icon = Icons.Default.CalendarMonth,
                    label = "EDD",
                    value = mother.edd
                )

                // Calculate weeks of pregnancy (if possible)
                val pregnancyWeeks = remember(mother.lmp) {
                    try {
                        val lmpDate = LocalDate.parse(mother.lmp, DateTimeFormatter.ISO_LOCAL_DATE)
                        val today = LocalDate.now()
                        val weeksPregnant = ChronoUnit.WEEKS.between(lmpDate, today)

                        if (weeksPregnant > 0 && weeksPregnant < 42) {
                            weeksPregnant
                        } else null
                    } catch (e: Exception) {
                        null
                    }
                }

                pregnancyWeeks?.let { weeks ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Week $weeks of pregnancy",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PregnancyInfoItem(
    title: String,
    value: String,
    icon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun InfoRowWithIcon(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.widthIn(min = 80.dp)
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun EmptyFollowUpCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "No follow-ups",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No follow-ups recorded yet",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tap the + button to add the first follow-up record",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun DeleteMotherDialog(
    showDialog: Boolean,
    motherName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    "Delete Mother Record",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    "Are you sure you want to delete $motherName?\n\n" +
                            "This will permanently remove:\n" +
                            "• Mother's personal information\n" +
                            "• All follow-up records\n" +
                            "• Medical history\n\n" +
                            "This action cannot be undone.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.onError)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun DeleteFollowUpDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    "Delete Follow-up Record",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    "Are you sure you want to delete this follow-up record?\n\n" +
                            "This action cannot be undone.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.onError)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}