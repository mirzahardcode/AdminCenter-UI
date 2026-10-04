# AdminCenter Reference

Dokumen ini adalah snapshot teknis dan panduan arsitektur dari sistem **AdminCenter** yang digunakan sebagai **Source of Truth** untuk penentuan schema authorization pada **UniversalAuthCenter (Batch 4 — Authorization Backend)**.

---

## Technical Summary

| Domain | Implementation Detail | Source of Truth File |
| :--- | :--- | :--- |
| **Authentication (Admin)** | Firebase Auth Email/Password + RTDB role check (`admins/{uid}`) | [FirebaseAdminAuthRepository.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/repository/FirebaseAdminAuthRepository.kt) |
| **User Identity** | Firebase Auth UID as primary key (`users/{uid}`) | [FirebaseUserRepository.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/repository/FirebaseUserRepository.kt) |
| **User Status** | Field `active` (`Boolean`), dynamically computed into `ACTIVE`, `EXPIRED`, `DISABLED` | [UserRecord.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/model/UserRecord.kt) |
| **Expiry** | Field `expiresAt` (`Long` ms, UTC end-of-day `23:59:59.999`) | [ExpiryDates.kt](../../../app/src/main/java/com/mirzadev/admincenter/util/ExpiryDates.kt) |
| **Device Binding** | Fields `deviceId` (`String`), `deviceBound` (`Boolean`), `deviceBoundAt` (`Long`) | [UserRecord.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/model/UserRecord.kt) |
| **Database Path** | Firebase Realtime Database node `/users/{uid}` and `/admins/{uid}` | [FirebaseUserRepository.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/repository/FirebaseUserRepository.kt) |
| **Privileged Operations** | HTTP POST requests to privileged backend service for `createUser` and `deleteUser` | [HttpPrivilegedUserRepository.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/repository/HttpPrivilegedUserRepository.kt) |
| **Application Mapping** | Firebase Project `ujianxthax`, Client package `com.mirzadev.onecenter`, Admin package `com.mirzadev.admincenter` | [google-services.json](../../../app/google-services.json) |

---

## Reference Documents

1. [Firebase Realtime Database Schema](firebase-schema.md) — Struktur tree RTDB aktual dan detail tipe data setiap field.
2. [Authentication Flow](authentication.md) — Mekanisme login admin dan manajemen akun user.
3. [User Data Model & Status Calculation](user-model.md) — Model data Kotlin dan aturan evaluasi status user (`ACTIVE`, `EXPIRED`, `DISABLED`).
4. [Authorization Fields](authorization-fields.md) — Rincian field `active` dan `expiresAt` serta perilakunya.
5. [User Operations](user-operations.md) — Spesifikasi API dan aksi `create`, `delete`, `setActive`, `setExpiry`, dan `clearDeviceBinding`.
6. [Device Binding Structure](device-binding.md) — Struktur field binding device dan aturan untuk integrasi UniversalAuthCenter.
7. [Source Map](source-map.md) — Indeks file source code utama di workspace.
8. [Application Mapping](application-mapping.md) — Pemetaan package name, App ID, dan environment Firebase.

---

> **Catatan Keselamatan**: Seluruh informasi di dalam direktori referensi ini dibuat tanpa mengubah behavior aplikasi, tanpa mengubah Firebase Security Rules, dan tanpa menyertakan secret/credential produksi.
