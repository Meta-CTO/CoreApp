#!/bin/bash

# Paths
current_path="$(pwd)"
app_folder_path="${current_path}/samples/sample-app-shared/src/commonMain/kotlin/com/metacto/sampleapp"
package_name="com.metacto.sampleapp"

# Check if both feature and screen arguments are provided
if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <feature> <screen>"
  exit 1
fi

# Inputs
feature="$1"
screen="$2"

# Convert "screen" to lowercase
screen_lower=$(echo "$screen" | tr '[:upper:]' '[:lower:]')

#####################################################################
# Create Contract file
#####################################################################
# Prepare file values
contract_file_name="${screen}Contract.kt"
contract_file_path="${app_folder_path}/presentation/${feature}/${screen_lower}"
contract_file_content="package ${package_name}.presentation.${feature}.${screen_lower}

import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState

class ${screen}Contract {

    data class State(
        val isInitialized: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
    }

    sealed class Effect : ViewSideEffect
}"

# Create the directory and the create the file
mkdir -p "$contract_file_path"
echo "$contract_file_content" >"${contract_file_path}/${contract_file_name}"

# Display message to the user
echo "File generated at: ${contract_file_path}/${contract_file_name}"

#####################################################################
# Create ViewModel file
#####################################################################
# Prepare file values
viewmodel_file_name="${screen}ViewModel.kt"
viewmodel_file_path="${app_folder_path}/presentation/${feature}/${screen_lower}"
viewmodel_file_content="package ${package_name}.presentation.${feature}.${screen_lower}

import ${package_name}.presentation.base.BaseViewModel
import ${package_name}.presentation.${feature}.${screen_lower}.${screen}Contract.Effect
import ${package_name}.presentation.${feature}.${screen_lower}.${screen}Contract.Event
import ${package_name}.presentation.${feature}.${screen_lower}.${screen}Contract.State

class ${screen}ViewModel : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init

        // Update the flag
        setState { copy(isInitialized = true) }
    }
}"

# Create the directory and the create the file
mkdir -p "$viewmodel_file_path"
echo "$viewmodel_file_content" >"${viewmodel_file_path}/${viewmodel_file_name}"

# Display message to the user
echo "File generated at: ${viewmodel_file_path}/${viewmodel_file_name}"

#####################################################################
# Create Screen file
#####################################################################
# Prepare file values
screen_file_name="${screen}Screen.kt"
screen_file_path="${app_folder_path}/presentation/${feature}/${screen_lower}"
screen_file_content="package ${package_name}.presentation.${feature}.${screen_lower}

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ${package_name}.presentation.${feature}.${screen_lower}.${screen}Contract.Event
import ${package_name}.presentation.${feature}.${screen_lower}.components.${screen}Content
import com.metacto.core.ui.base.BaseScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

internal object ${screen}Screen : BaseScreen<${screen}ViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<${screen}ViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        ${screen}Content(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}"

# Create the directory and the create the file
mkdir -p "$screen_file_path"
echo "$screen_file_content" >"${screen_file_path}/${screen_file_name}"

# Display message to the user
echo "File generated at: ${screen_file_path}/${screen_file_name}"

#####################################################################
# Create Content file
#####################################################################
# Prepare file values
content_file_name="${screen}Content.kt"
content_file_path="${app_folder_path}/presentation/${feature}/${screen_lower}/components"
content_file_content="package ${package_name}.presentation.${feature}.${screen_lower}.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.metacto.core.ui.components.containers.ScreenColumn
import ${package_name}.presentation.theme.colors
import ${package_name}.presentation.theme.typography
import ${package_name}.presentation.theme.shapes
import ${package_name}.presentation.theme.spacings
import ${package_name}.presentation.${feature}.${screen_lower}.${screen}Contract.State
import ${package_name}.presentation.${feature}.${screen_lower}.${screen}Contract.Event

@Composable
internal fun ${screen}Content(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Container column
    ScreenColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = \"${screen} Screen\"
        )
    }
}"

# Create the directory and the create the file
mkdir -p "$content_file_path"
echo "$content_file_content" >"${content_file_path}/${content_file_name}"

# Display message to the user
echo "File generated at: ${content_file_path}/${content_file_name}"

#####################################################################
# Add the view model DI into DI module
#####################################################################
# Prepare file values
viewmodels_di_module_file_path="${app_folder_path}/di"
viewmodels_di_module_file_name="ViewModelsModule.kt"
viewmodels_di_module_full_file_name="${viewmodels_di_module_file_path}/${viewmodels_di_module_file_name}"

# Then append the screen di module to the main feature module
viewmodel_import_code="import ${package_name}.presentation.${feature}.${screen_lower}.${screen}ViewModel"
awk -v text="$viewmodel_import_code" '/\/\/ MARK: Add imports/ { print; print text; next } 1' "$viewmodels_di_module_full_file_name" >tmpfile && mv tmpfile "$viewmodels_di_module_full_file_name"
echo "Screen ViewModel DI appended to ${viewmodels_di_module_full_file_name}"

# Then append the screen di module to the main feature module
di_include_code="    commonViewModel { ${screen}ViewModel() }"
awk -v text="$di_include_code" '/\/\/ MARK: Add view model definitions/ { print; print text; next } 1' "$viewmodels_di_module_full_file_name" >tmpfile && mv tmpfile "$viewmodels_di_module_full_file_name"
echo "Screen ViewModel DI appended to ${viewmodels_di_module_full_file_name}"
