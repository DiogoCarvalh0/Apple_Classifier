package pt.carvalho.apples.classifier.ui.modal

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val RADIUS = 24

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun Bottomsheet(
    isExpanded: Boolean = false,
    whenCollapsed: () -> Unit,
    sheetContent: @Composable () -> Unit,
    behindSheetContent: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    if (isExpanded) {
        expand(
            scope = scope,
            state = bottomSheetScaffoldState.bottomSheetState
        )
    }

    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) { whenCollapsed() }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = RADIUS.dp, topEnd = RADIUS.dp),
        sheetContent = { sheetContent() },
        sheetPeekHeight = 0.dp
    ) { behindSheetContent() }
}

@OptIn(ExperimentalMaterialApi::class)
private fun expand(
    scope: CoroutineScope,
    state: BottomSheetState
) {
    scope.launch {
        state.expand()
    }
}
