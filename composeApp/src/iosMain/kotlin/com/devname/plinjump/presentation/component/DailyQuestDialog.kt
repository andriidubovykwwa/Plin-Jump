package com.devname.plinjump.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.devname.plinjump.utils.GameConfig
import com.devname.plinjump.utils.dialogGradientColor1
import com.devname.plinjump.utils.dialogGradientColor2

@Composable
fun DailyQuestDialog(
    modifier: Modifier = Modifier,
    playedDailyGames: Int,
    dailyRecord: Int,
    onClaim: (Int) -> Unit,
    claimed: List<Int>,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        val dialogShape = RoundedCornerShape(15.dp)
        Box(
            modifier.background(
                Brush.verticalGradient(
                    colors = listOf(
                        dialogGradientColor1,
                        dialogGradientColor2,
                    )
                ), dialogShape
            )
                .widthIn(min = 300.dp)
                .heightIn(min = 200.dp)
                .border(2.dp, Color.White, dialogShape)
        ) {
            LazyColumn(
                Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                item {
                    DailyQuestComponent(
                        Modifier.widthIn(min = 200.dp),
                        description = "Play ${GameConfig.QUEST_1_GAMES} game",
                        reward = GameConfig.QUEST_1_REWARD,
                        currentProgress = playedDailyGames,
                        targetProgress = GameConfig.QUEST_1_GAMES,
                        onClaim = { onClaim(0) },
                        claimed = claimed.contains(0)
                    )
                }
                item {
                    DailyQuestComponent(
                        Modifier.widthIn(min = 200.dp),
                        description = "Play ${GameConfig.QUEST_2_GAMES} games",
                        reward = GameConfig.QUEST_2_REWARD,
                        currentProgress = playedDailyGames,
                        targetProgress = GameConfig.QUEST_2_GAMES,
                        onClaim = { onClaim(1) },
                        claimed = claimed.contains(1)
                    )
                }
                item {
                    DailyQuestComponent(
                        Modifier.widthIn(min = 200.dp),
                        description = "Reach ${GameConfig.QUEST_3_SCORE} score",
                        reward = GameConfig.QUEST_3_REWARD,
                        currentProgress = dailyRecord,
                        targetProgress = GameConfig.QUEST_3_SCORE,
                        onClaim = { onClaim(2) },
                        claimed = claimed.contains(2)
                    )
                }
            }
        }
    }
}