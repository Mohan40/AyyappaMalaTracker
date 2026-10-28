package com.example.ayyappamala.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun GuideScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🙏 Swamiye Saranam Ayyappa 🙏",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Ayyappa Deeksha & Vratham Guidelines",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        item {
            GuideSectionCard(
                title = "1. Purity & Dress Code",
                content = "• Wear the sacred Mala (Rudraksha or Tulasi) blessed by a Guru Swami.\n• Wear Black, Blue, or Saffron clothes throughout the 41 days.\n• Maintain physical cleanliness with baths twice a day (cold water preferred before dawn and evening prayer)."
            )
        }

        item {
            GuideSectionCard(
                title = "2. Daily Sadhana & Prayers",
                content = "• Perform morning and evening pooja with 108 Ayyappa Saranam chanting.\n• Recite Vishnu Sahasranamam or devotional bhajans daily.\n• Recite Harivarasanam before closing the eyes or temple doors at night."
            )
        }

        item {
            GuideSectionCard(
                title = "3. Dietary Discipline (Sattvic)",
                content = "• Consume only pure vegetarian (Sattvic) food.\n• Avoid onions, garlic, non-vegetarian food, alcohol, and tobacco completely.\n• Many devotees observe single meals or strict fasting rules."
            )
        }

        item {
            GuideSectionCard(
                title = "4. Brahmacharya & Mental Discipline",
                content = "• Strictly observe celibacy (Brahmacharya) in thought, word, and deed.\n• Speak truth, practice patience, and eliminate anger.\n• Address every male as 'Swami' and every female as 'Malikapuram' (Mother)."
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Harivarasanam (Ending Lullaby)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Harivarasanam Viswamohanam\nHarinadhipathyam Eeswaram\nArumanaputhu Vanchitham\nSabarigireesam Bhajeham...\n\nSwamiye Ayyappa! Ayyappa Swamiye!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
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
fun GuideSectionCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
