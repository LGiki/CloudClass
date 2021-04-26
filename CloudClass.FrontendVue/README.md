# Cloud Class Frontend

## Project setup
```
yarn install
```

### Compiles and hot-reloads for development
```
yarn serve
```

### Compiles and minifies for production
```
yarn build
```

### Lints and fixes files
```
yarn lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

# Compile for Android devices

```bash
# If cordova is not already installed
yarn global add cordova

# Install cordova
vue add cordova

# Change directory to src-cordova
cd src-cordova

# Add Android platform
cordova platform add android

# Add QR Code scanner plugin
cordova plugin add cordova-plugin-qr-barcode-scanner

# Change directory to root
cd ..

# Development or Build
yarn cordova-serve-android # Development Android
yarn cordova-build-android # Build Android
```

# Allow HTTP connection after Android P

Add following code to `src-cordova/config.xml`:

```xml
<platform name="android">
    <!-- ... -->
    <!-- Code start here -->
    <edit-config xmlns:android="http://schemas.android.com/apk/res/android" file="app/src/main/AndroidManifest.xml" mode="merge" target="/manifest/application">
        <application android:networkSecurityConfig="@xml/network_security_config" />
    </edit-config>
    <resource-file src="network_security_config.xml" target="app/src/main/res/xml/network_security_config.xml" />
    <!-- Code end here -->
</platform>
```

Then create a file named `network_security_config.xml` under `src-cordova` folder:

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>
```

