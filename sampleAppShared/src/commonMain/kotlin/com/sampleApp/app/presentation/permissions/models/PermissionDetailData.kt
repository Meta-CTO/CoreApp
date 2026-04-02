package com.sampleApp.app.presentation.permissions.models

import com.metacto.core.permissions.enums.Permission


data class PermissionDetailData(
    val permission: Permission,
    val displayName: String,
    val description: String,
    val androidPermissions: List<String>,
    val iosEquivalent: String,
    val useCases: List<String>,
    val requiredForFeatures: List<String>
)

object PermissionDetailsProvider {
    fun getPermissionDetails(permission: Permission): PermissionDetailData {
        return when (permission) {
            Permission.CAMERA -> PermissionDetailData(
                permission = Permission.CAMERA,
                displayName = "📷 Camera",
                description = "Access device camera to take photos and record videos",
                androidPermissions = listOf("android.permission.CAMERA"),
                iosEquivalent = "NSCameraUsageDescription",
                useCases = listOf("Take profile photos", "Record videos", "Scan QR codes", "Document scanning"),
                requiredForFeatures = listOf("Camera Screen", "Profile Photo", "Video Recording")
            )

            Permission.GALLERY -> PermissionDetailData(
                permission = Permission.GALLERY,
                displayName = "🖼️ Photo Gallery",
                description = "Access photos and videos from device gallery",
                androidPermissions = listOf("READ_MEDIA_IMAGES (API 33+)", "READ_MEDIA_VIDEO (API 33+)", "READ_EXTERNAL_STORAGE (< API 33)"),
                iosEquivalent = "NSPhotoLibraryUsageDescription",
                useCases = listOf("Select profile photos", "Share media content", "Upload images", "Media picker"),
                requiredForFeatures = listOf("Image Picker", "Media Upload", "Profile Photos")
            )

            Permission.STORAGE -> PermissionDetailData(
                permission = Permission.STORAGE,
                displayName = "💾 Storage Access",
                description = "Read files from device storage",
                androidPermissions = listOf("READ_MEDIA_AUDIO (API 33+)", "READ_MEDIA_IMAGES (API 33+)", "READ_MEDIA_VIDEO (API 33+)", "READ_EXTERNAL_STORAGE (< API 33)"),
                iosEquivalent = "File access via DocumentPicker",
                useCases = listOf("Access downloaded files", "Read media files", "File management", "Document access"),
                requiredForFeatures = listOf("File Manager", "Media Access", "Document Viewer")
            )

            Permission.WRITE_STORAGE -> PermissionDetailData(
                permission = Permission.WRITE_STORAGE,
                displayName = "✏️ Write Storage",
                description = "Write files to device storage",
                androidPermissions = listOf("WRITE_EXTERNAL_STORAGE"),
                iosEquivalent = "Files app integration",
                useCases = listOf("Save downloaded files", "Export data", "Cache management", "File creation"),
                requiredForFeatures = listOf("Download Manager", "Export Features", "File Creation")
            )

            Permission.LOCATION -> PermissionDetailData(
                permission = Permission.LOCATION,
                displayName = "📍 Precise Location",
                description = "Access precise device location using GPS",
                androidPermissions = listOf("ACCESS_FINE_LOCATION", "ACCESS_COARSE_LOCATION (API 31+)"),
                iosEquivalent = "NSLocationWhenInUseUsageDescription",
                useCases = listOf("Navigation", "Location-based services", "Weather by location", "Nearby search"),
                requiredForFeatures = listOf("Maps", "Location Services", "Weather", "Check-in")
            )

            Permission.COARSE_LOCATION -> PermissionDetailData(
                permission = Permission.COARSE_LOCATION,
                displayName = "📍 Approximate Location",
                description = "Access approximate device location",
                androidPermissions = listOf("ACCESS_COARSE_LOCATION"),
                iosEquivalent = "NSLocationWhenInUseUsageDescription (reduced accuracy)",
                useCases = listOf("City-level location", "Timezone detection", "Regional content", "Approximate weather"),
                requiredForFeatures = listOf("Regional Content", "Basic Location Services")
            )

            Permission.BACKGROUND_LOCATION -> PermissionDetailData(
                permission = Permission.BACKGROUND_LOCATION,
                displayName = "📍 Background Location",
                description = "Access location when app is in background",
                androidPermissions = listOf("ACCESS_FINE_LOCATION", "ACCESS_COARSE_LOCATION (API 31+)", "ACCESS_BACKGROUND_LOCATION"),
                iosEquivalent = "NSLocationAlwaysAndWhenInUseUsageDescription",
                useCases = listOf("Fitness tracking", "Location history", "Geofencing", "Navigation apps"),
                requiredForFeatures = listOf("Fitness Tracking", "Geofencing", "Location History")
            )

            Permission.BLUETOOTH_LE -> PermissionDetailData(
                permission = Permission.BLUETOOTH_LE,
                displayName = "📶 Bluetooth (All)",
                description = "Full Bluetooth access including scan, connect, and advertise",
                androidPermissions = listOf("BLUETOOTH_CONNECT (API 31+)", "BLUETOOTH_SCAN (API 31+)", "BLUETOOTH_ADVERTISE (API 31+)", "ACCESS_COARSE_LOCATION", "BLUETOOTH (< API 31)"),
                iosEquivalent = "NSBluetoothAlwaysUsageDescription",
                useCases = listOf("Connect to devices", "Smart device control", "Fitness trackers", "Audio devices"),
                requiredForFeatures = listOf("Device Pairing", "Smart Home", "Fitness Devices")
            )

            Permission.REMOTE_NOTIFICATION -> PermissionDetailData(
                permission = Permission.REMOTE_NOTIFICATION,
                displayName = "🔔 Push Notifications",
                description = "Show push notifications from remote servers",
                androidPermissions = listOf("POST_NOTIFICATIONS (API 33+)"),
                iosEquivalent = "Push notification entitlement",
                useCases = listOf("App updates", "Messages", "Alerts", "Marketing notifications"),
                requiredForFeatures = listOf("Push Messaging", "App Notifications", "Alerts")
            )

            Permission.RECORD_AUDIO -> PermissionDetailData(
                permission = Permission.RECORD_AUDIO,
                displayName = "🎤 Microphone",
                description = "Record audio using device microphone",
                androidPermissions = listOf("RECORD_AUDIO"),
                iosEquivalent = "NSMicrophoneUsageDescription",
                useCases = listOf("Voice messages", "Audio recording", "Voice calls", "Speech recognition"),
                requiredForFeatures = listOf("Voice Recording", "Video with Audio", "Voice Calls")
            )

            Permission.BLUETOOTH_SCAN -> PermissionDetailData(
                permission = Permission.BLUETOOTH_SCAN,
                displayName = "📶 Bluetooth Scan",
                description = "Scan for nearby Bluetooth devices",
                androidPermissions = listOf("BLUETOOTH_SCAN (API 31+)", "BLUETOOTH (< API 31)"),
                iosEquivalent = "Core Bluetooth scanning",
                useCases = listOf("Device discovery", "Nearby device detection", "Device pairing setup"),
                requiredForFeatures = listOf("Device Discovery", "Bluetooth Setup")
            )

            Permission.BLUETOOTH_ADVERTISE -> PermissionDetailData(
                permission = Permission.BLUETOOTH_ADVERTISE,
                displayName = "📶 Bluetooth Advertise",
                description = "Make device discoverable via Bluetooth",
                androidPermissions = listOf("BLUETOOTH_ADVERTISE (API 31+)", "BLUETOOTH (< API 31)"),
                iosEquivalent = "Core Bluetooth peripheral mode",
                useCases = listOf("Device sharing", "Peer-to-peer connection", "Act as Bluetooth peripheral"),
                requiredForFeatures = listOf("Device Sharing", "P2P Features")
            )

            Permission.BLUETOOTH_CONNECT -> PermissionDetailData(
                permission = Permission.BLUETOOTH_CONNECT,
                displayName = "📶 Bluetooth Connect",
                description = "Connect to paired Bluetooth devices",
                androidPermissions = listOf("BLUETOOTH_CONNECT (API 31+)", "BLUETOOTH (< API 31)"),
                iosEquivalent = "Core Bluetooth central mode",
                useCases = listOf("Connect to headphones", "Smart device control", "Data transfer"),
                requiredForFeatures = listOf("Audio Devices", "Smart Device Control")
            )

            Permission.CONTACTS -> PermissionDetailData(
                permission = Permission.CONTACTS,
                displayName = "👥 Contacts",
                description = "Access device contacts for reading and writing",
                androidPermissions = listOf("READ_CONTACTS", "WRITE_CONTACTS"),
                iosEquivalent = "NSContactsUsageDescription",
                useCases = listOf("Friend invitations", "Contact sharing", "Auto-fill phone numbers", "Social features"),
                requiredForFeatures = listOf("Contact Picker", "Friend Invites", "Social Features")
            )

            Permission.MOTION -> PermissionDetailData(
                permission = Permission.MOTION,
                displayName = "🏃 Motion & Fitness",
                description = "Access motion and fitness sensors",
                androidPermissions = listOf("ACTIVITY_RECOGNITION (API 31+)", "BODY_SENSORS"),
                iosEquivalent = "NSMotionUsageDescription",
                useCases = listOf("Step counting", "Activity tracking", "Fitness monitoring", "Health data"),
                requiredForFeatures = listOf("Fitness Tracking", "Health Monitoring", "Step Counter")
            )

            Permission.CALENDER -> PermissionDetailData(
                permission = Permission.CALENDER,
                displayName = "📅 Calendar",
                description = "Access device calendar for reading and writing events",
                androidPermissions = listOf("READ_CALENDAR", "WRITE_CALENDAR"),
                iosEquivalent = "NSCalendarsUsageDescription",
                useCases = listOf("Event scheduling", "Meeting integration", "Reminder creation", "Calendar sync"),
                requiredForFeatures = listOf("Event Scheduling", "Meeting Features", "Reminders")
            )
        }
    }

    fun getAllPermissionDetails(): List<PermissionDetailData> {
        return Permission.entries.map { getPermissionDetails(it) }
    }
}