# Employee Management Android Application

A comprehensive Android app for managing employee information with form submission, image upload, and data validation capabilities.

## Features

- **Employee Form Submission**  
  Capture employee details including:
  - Personal information (first/last name)
  - Employment details (position, start dates)
  - Contact information
  - Contract details

- **Image Management**  
  - Capture photos using device camera
  - Select images from gallery
  - Image cropping and preview

- **Date Selection**  
  Built-in date pickers for:
  - Date of Birth
  - Employment dates
  - Contract dates

- **Form Validation**  
  Real-time validation for:
  - Required fields
  - Date formats
  - Gender selection
  - Image upload

- **Data Management**  
  - Temporary data storage
  - Form reset functionality
  - Error handling

## Technical Specifications

- **Language**: Kotlin
- **Minimum SDK**: Android 5.0 (API 22)
- **Architecture**: MVC Pattern
- **Components**:
  - Activities
  - Custom Utility Classes
  - AndroidX Components

- **Key Dependencies**:
  - Glide (Image loading)
  - Material Design Components
  - AndroidX ConstraintLayout

## Installation

1. **Requirements**  
   - Android Studio Flamingo or newer
   - Android SDK 33+
   - Java Development Kit 11

2. *
   git clone [https://github.com/elonmasai7/Elegant_app.git](https://github.com/elonmasai7/Elegant_app/)
   ```
   - Open project in Android Studio
   - Build project (Ctrl+F9/Cmd+F9)

3. **Configuration**  
   - Update `applicationId` in `build.gradle`
   - Add Google Maps API key if implementing location features
   - Configure FileProvider in `AndroidManifest.xml`

## Usage

1. **Form Submission**  
   - Launch app from device
   - Fill all required fields (marked with *)
   - Upload employee photo
   - Tap Submit to save data

2. **Image Upload**  
   - Tap profile image
   - Choose source (Camera/Gallery)
   - Capture/Select image
   - Image auto-updates in preview

3. **Date Selection**  
   - Tap any date field
   - Choose date from picker
   - Dates auto-format (YYYY-MM-DD)

## Permission Handling

The app requests these permissions at runtime:
- Camera access (for photo capture)
- Storage access (for image selection)

## Development Setup

1. **Dependency Management**  
   Add to `build.gradle`:
   ```gradle
   implementation 'com.github.bumptech.glide:glide:4.16.0'
   kapt 'com.github.bumptech.glide:compiler:4.16.0'
   ```

2. **File Provider Configuration**  
   Add to `AndroidManifest.xml`:
   ```xml
   <provider
       android:name="androidx.core.content.FileProvider"
       android:authorities="${applicationId}.provider"
       android:exported="false"
       android:grantUriPermissions="true">
       <meta-data
           android:name="android.support.FILE_PROVIDER_PATHS"
           android:resource="@xml/file_paths"/>
   </provider>
   ```

## Contributing

1. Fork repository
2. Create feature branch (`git checkout -b feature/improvement`)
3. Commit changes (`git commit -m 'Add some feature'`)
4. Push to branch (`git push origin feature/improvement`)
5. Open Pull Request

## License

MIT License - See [LICENSE](LICENSE) for details

## Acknowledgements

- Android Developer Documentation
- Glide Image Loading Library
- Material Design Components

