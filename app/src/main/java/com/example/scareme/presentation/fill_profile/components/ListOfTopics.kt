package com.example.scareme.presentation.fill_profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scareme.R
import com.example.scareme.domain.Entities.RequestBodies.TopicsRequest
import com.example.scareme.presentation.ui.theme.balooFontFamily

@Composable
fun Topics(
    fetchedTopics: List<TopicsRequest>,
    topics: MutableList<String>
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.party_topics),
            fontFamily = balooFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            items(fetchedTopics.chunked(3)) { rowItems ->
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    items(rowItems) { topic ->
                        TopicItem(topic, topics)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun TopicItem(
    topic: TopicsRequest,
    topics: MutableList<String>
) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor = if (isSelected) Color(0xFFF6921D) else Color(0xFF180c14)
    val textColor = if (isSelected) Color(0xFF180c14) else Color(0xFFF6921D)

    Box(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                isSelected = !isSelected
                if (isSelected) {
                    topics.add(topic.id)
                } else {
                    topics.remove(topic.id)
                }
            }
            .border(BorderStroke(2.dp, Color(0xFFF6921D)), shape = RoundedCornerShape(16.dp))
    ) {
        Text(
            text = topic.title,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = balooFontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}