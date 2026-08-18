package com.felipeserri.financeoffline.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.felipeserri.financeoffline.ui.theme.Spacing

@Composable
fun CategoryChip(name: String, colorHex: String, modifier: Modifier = Modifier) {
    val chipColor = remember(colorHex) {
        runCatching { Color(android.graphics.Color.parseColor(colorHex)) }.getOrDefault(Color.Gray)
    }

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = chipColor.copy(alpha = 0.15f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Spacing.sm, vertical = Spacing.xs),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(chipColor, shape = CircleShape)
            )
            androidx.compose.foundation.layout.Spacer(Modifier.width(Spacing.xs))
            Text(text = name, style = MaterialTheme.typography.labelMedium, color = chipColor)
        }
    }
}