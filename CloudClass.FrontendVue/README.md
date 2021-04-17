# cloud_class_frontend

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

