package com.bafmay.challengeweek2.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun RedButton(
    imageVector: ImageVector,
    onPressed : () -> Unit
) {
    IconButton(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color = Color(0xFFEC4B2B)),
        onClick = onPressed
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = Color.White
        )
    }
}