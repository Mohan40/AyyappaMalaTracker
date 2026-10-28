package com.example.ayyappamala.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ayyappamala.R
import com.example.ayyappamala.data.DailyLog
import com.example.ayyappamala.ui.MalaViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(viewModel: MalaViewModel) {
    val profile by viewModel.profile.collectAsState()
    val todayLog by viewModel.todayLog.collectAsState()
    val currentDay by viewModel.currentDayNumber.collectAsState()
    val streak by viewModel.streakCount.collectAsState()

    var notesText by remember(todayLog.notes) { mutableStateOf(todayLog.notes) }

    val completedCount = todayLog.completedTasksCount()
    val totalCount = DailyLog.totalTaskCount
    val progress = completedCount.toFloat() / totalCount.toFloat()

    val formattedDate = remember {
        SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault()).format(Date())
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Devotional Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Devotional Image Avatar
                    Surface(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ayyappa_logo),
                            contentDescription = "Swami Ayyappa Logo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "🙏 Swamiye Saranam Ayyappa 🙏",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${profile.swamyName} • ${profile.malaNumber}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    // Progress row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Mala Day $currentDay of ${profile.totalDays}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "🔥 Streak: $streak days",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Text(
                            text = "${(progress * 100).toInt()}%",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
        }

        // Date header (Centered on next line)
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Today's Sadhana Checklist",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Checklist Items (Chronological Timing Order: Morning -> Day -> Evening -> Night)
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 1. Morning / Continuous
                ChecklistItemCard(
                    title = "Wearing Mala & Rudraksha",
                    subtitle = "Consecrated beads worn continuously",
                    icon = Icons.Default.CheckCircle,
                    checked = todayLog.malaWorn,
                    onCheckedChange = { checked ->
                        viewModel.updateTodayLog { it.copy(malaWorn = checked) }
                    }
                )

                // 2. Morning Bath
                ChecklistItemCard(
                    title = "Morning Cold Bath",
                    subtitle = "Purification before dawn prayer",
                    icon = Icons.Default.Refresh,
                    checked = todayLog.morningBath,
                    onCheckedChange = { checked ->
                        viewModel.updateTodayLog { it.copy(morningBath = checked) }
                    }
                )

                // 3. Morning Prayer
                ChecklistItemCard(
                    title = "Morning Puja & Namajpam",
                    subtitle = "108 Ayyappa Saranam / Vishnu Sahasranamam",
                    icon = Icons.Default.WbSunny,
                    checked = todayLog.morningPrayer,
                    onCheckedChange = { checked ->
                        viewModel.updateTodayLog { it.copy(morningPrayer = checked) }
                    }
                )

                // 4. Daily Diet
                ChecklistItemCard(
                    title = "Strict Sattvic Diet",
                    subtitle = "Pure vegetarian (no onion/garlic, single meals if observed)",
                    icon = Icons.Default.Home,
                    checked = todayLog.sattvicDiet,
                    onCheckedChange = { checked ->
                        viewModel.updateTodayLog { it.copy(sattvicDiet = checked) }
                    }
                )

                // 5. Daily Purity
                ChecklistItemCard(
                    title = "Brahmacharya (Celibacy)",
                    subtitle = "Mental and physical purity & detachment",
                    icon = Icons.Default.Lock,
                    checked = todayLog.celibacy,
                    onCheckedChange = { checked ->
                        viewModel.updateTodayLog { it.copy(celibacy = checked) }
                    }
                )

                // 6. Daily Mindset
                ChecklistItemCard(
                    title = "Truth, Patience & 'Swami' Address",
                    subtitle = "Speaking truth, practicing patience, seeing Ayyappa in all",
                    icon = Icons.Default.Favorite,
                    checked = todayLog.truthAndPatience,
                    onCheckedChange = { checked ->
                        viewModel.updateTodayLog { it.copy(truthAndPatience = checked) }
                    }
                )

                // 7. Evening Bath
                ChecklistItemCard(
                    title = "Evening Bath",
                    subtitle = "Bathing before evening pooja",
                    icon = Icons.Default.Refresh,
                    checked = todayLog.eveningBath,
                    onCheckedChange = { checked ->
                        viewModel.updateTodayLog { it.copy(eveningBath = checked) }
                    }
                )

                // 8. Evening Prayer
                ChecklistItemCard(
                    title = "Evening Puja & Harivarasanam",
                    subtitle = "Deeparadhana & Harivarasanam recitation",
                    icon = Icons.Default.NightsStay,
                    checked = todayLog.eveningPrayer,
                    onCheckedChange = { checked ->
                        viewModel.updateTodayLog { it.copy(eveningPrayer = checked) }
                    }
                )

                // 9. Temple / Bhajans
                ChecklistItemCard(
                    title = "Temple / Bhajans Attendance",
                    subtitle = "Visiting temple or participating in evening Bhajans",
                    icon = Icons.Default.Star,
                    checked = todayLog.templeVisit,
                    onCheckedChange = { checked ->
                        viewModel.updateTodayLog { it.copy(templeVisit = checked) }
                    }
                )
            }
        }

        // Daily Reflection Notes
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Daily Spiritual Notes / Reflection",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = notesText,
                        onValueChange = {
                            notesText = it
                            viewModel.updateTodayLog { log -> log.copy(notes = it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Write your thoughts, temple visits, or mantra count...") },
                        maxLines = 4,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ChecklistItemCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (checked) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (checked) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (checked) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
