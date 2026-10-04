# Source Map

Dokumen ini berisi peta tautan langsung ke file-file source code utama di dalam repository yang mendefinisikan arsitektur data dan otorisasi.

---

## Configuration & Container

* **App Configuration**: [AppConfig.kt](../../../app/src/main/java/com/mirzadev/admincenter/AppConfig.kt)
  * Menyiapkan `DATABASE_URL` (`https://ujianxthax-default-rtdb.asia-southeast1.firebasedatabase.app/`), `PRIVILEGED_BACKEND_BASE_URL` (`https://backend-admin-theta.vercel.app/`), dan `ADMIN_ROLE` (`admin`).
* **Dependency Container**: [AppContainer.kt](../../../app/src/main/java/com/mirzadev/admincenter/AppContainer.kt)
  * Menginisialisasi instance Firebase Auth dan Firebase Realtime Database serta menghubungkannya ke repository.

---

## Data Models

* **Core User Model & Status Logic**: [UserRecord.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/model/UserRecord.kt)
  * Definisi `UserRecord`, `UserStatus`, dan fungsi `statusAt(nowMillis)`.
* **User Management Request/Response**: [UserManagementModels.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/model/UserManagementModels.kt)
  * Definisi `CreateUserRequest` dan `CreatedUser`.
* **Admin Profile & Session State**: [AdminModels.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/model/AdminModels.kt)
  * Definisi `AdminProfile` dan `SessionState`.

---

## Repositories & Data Layers

* **User Repository Interface**: [UserRepository.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/repository/UserRepository.kt)
* **Firebase User Repository**: [FirebaseUserRepository.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/repository/FirebaseUserRepository.kt)
  * Operasi RTDB direct read/write (`getUsers`, `getUser`, `setActive`, `setExpiry`, `clearDeviceBinding`).
* **Privileged User Repository Interface**: [PrivilegedUserRepository.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/repository/PrivilegedUserRepository.kt)
* **HTTP Privileged User Repository**: [HttpPrivilegedUserRepository.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/repository/HttpPrivilegedUserRepository.kt)
  * Client HTTP ke privileged backend endpoint (`createUser` & `deleteUser`).
* **Admin Auth Repository Interface**: [AdminAuthRepository.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/repository/AdminAuthRepository.kt)
* **Firebase Admin Auth Repository**: [FirebaseAdminAuthRepository.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/repository/FirebaseAdminAuthRepository.kt)
  * Otentikasi Admin via Firebase Auth & verifikasi otorisasi via RTDB node `admins/{uid}`.

---

## Utilities

* **Expiry Date Calculations**: [ExpiryDates.kt](../../../app/src/main/java/com/mirzadev/admincenter/util/ExpiryDates.kt)
  * UTC Date formatting (`dd/MM/uuuu`), end-of-day millis conversion, dan kalkulasi perpanjangan durasi.
