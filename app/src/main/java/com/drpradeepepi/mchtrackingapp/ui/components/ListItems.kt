// File: app/src/main/java/com/drpradeepepi/mchtrackingapp/ui/components/ListItems.kt
package com.drpradeepepi.mchtrackingapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drpradeepepi.mchtrackingapp.domain.model.PHC
import com.drpradeepepi.mchtrackingapp.domain.model.HSC
import com.drpradeepepi.mchtrackingapp.domain.model.Mother
import com.drpradeepepi.mchtrackingapp.domain.model.FollowUp

@Composable
fun PHCItem(
    phc: PHC,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                    text = phc.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = phc.location,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                if (phc.doctorName.isNotEmpty()) {
                    Text(
                        text = "Dr. ${phc.doctorName}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

@Composable
fun HSCItem(
    hsc: HSC,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                    text = hsc.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = hsc.location,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                if (hsc.anmName.isNotEmpty()) {
                    Text(
                        text = "ANM: ${hsc.anmName}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

@Composable
fun MotherItem(
    mother: Mother,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                    text = "Age: ${mother.age}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Phone: ${mother.phone}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                if (mother.edd.isNotEmpty()) {
                    Text(
                        text = "EDD: ${mother.edd}",
                        fontSize = 14.sp,
                        color = Color.Blue
                    )
                }
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

@Composable
fun FollowUpItem(
    followUp: FollowUp,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Date: ${followUp.date}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                if (followUp.gestationalAge > 0) {
                    Text(
                        text = "GA: ${followUp.gestationalAge} weeks",
                        fontSize = 14.sp
                    )
                }
                if (followUp.weight.isNotEmpty()) {
                    Text(
                        text = "Weight: ${followUp.weight} kg",
                        fontSize = 14.sp
                    )
                }
                if (followUp.bloodPressure.isNotEmpty()) {
                    Text(
                        text = "BP: ${followUp.bloodPressure}",
                        fontSize = 14.sp
                    )
                }
                if (followUp.hemoglobin.isNotEmpty()) {
                    Text(
                        text = "Hb: ${followUp.hemoglobin} g/dl",
                        fontSize = 14.sp
                    )
                }
                if (followUp.complaints.isNotEmpty()) {
                    Text(
                        text = "Complaints: ${followUp.complaints}",
                        fontSize = 14.sp
                    )
                }
                if (followUp.nextVisit.isNotEmpty()) {
                    Text(
                        text = "Next Visit: ${followUp.nextVisit}",
                        fontSize = 14.sp,
                        color = Color.Blue
                    )
                }
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}