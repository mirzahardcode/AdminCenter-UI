# Application Mapping

Dokumen ini mencatat identitas aplikasi dan konfigurasi environment Firebase yang terdaftar pada ekosistem AdminCenter.

---

## Registered Applications

Data diambil dari [google-services.json](../../../app/google-services.json):

### Application 1: AdminCenter (Management Interface)
* **Application Name**: AdminCenter
* **Package Name**: `com.mirzadev.admincenter`
* **Mobile SDK App ID**: `1:63356146637:android:85180b3a6aff3f06e22a84`
* **OAuth Client ID**: `63356146637-k2ca1m4s8tmf5brkiq9f1i371fdfjkeh.apps.googleusercontent.com`
* **Role**: Interface manajemen admin untuk mengelola user, status aktif, expiry, dan reset device.

### Application 2: OneCenter (Client Application)
* **Application Name**: OneCenter
* **Package Name**: `com.mirzadev.onecenter`
* **Mobile SDK App ID**: `1:63356146637:android:b552423c1502bf18e22a84`
* **OAuth Client ID**: `63356146637-k2ca1m4s8tmf5brkiq9f1i371fdfjkeh.apps.googleusercontent.com`
* **Role**: Aplikasi klien pengguna utama yang akan melakukan otentikasi dan otorisasi.

---

## Firebase Infrastructure

* **Firebase Project ID**: `ujianxthax`
* **Firebase Project Number**: `63356146637`
* **Realtime Database URL**: `https://ujianxthax-default-rtdb.asia-southeast1.firebasedatabase.app/`
* **Storage Bucket**: `ujianxthax.firebasestorage.app`
* **Privileged Backend Base URL**: `https://backend-admin-theta.vercel.app/`
