package at.crowdware.shift.plugin

import android.content.Context
import at.crowdware.shift.ShiftPlugin

class Plugin : ShiftPlugin {
    override fun initialize(context: Context) {
        // Initialize plugin-specific resources or components
    }

    override fun performAction() {
        println("performAction")
    }
}