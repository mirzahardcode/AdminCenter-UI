# User Data Model & Status Calculation

Dokumen ini menjelaskan struktur data model pengguna dan logika penentuan status akun.

---

## 1. Data Model Class `UserRecord`

File Source: [UserRecord.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/model/UserRecord.kt)

```kotlin
package com.mirzadev.admincenter.data.model

enum class UserStatus { ACTIVE, EXPIRED, DISABLED }

data class UserRecord(
    val uid: String,
    val username: String,
    val email: String,
    val deviceId: String,
    val deviceBound: Boolean,
    val deviceBoundAt: Long,
    val active: Boolean,
    val expiresAt: Long?,
    val createdAt: Long
) {
    fun statusAt(nowMillis: Long): UserStatus = when {
        !active -> UserStatus.DISABLED
        expiresAt == null || expiresAt <= 0L -> UserStatus.EXPIRED
        expiresAt <= nowMillis -> UserStatus.EXPIRED
        else -> UserStatus.ACTIVE
    }
}
```

---

## 2. Aturan Evaluasi Status (`UserStatus`)

Status user dihitung secara dinamis pada runtime menggunakan fungsi `statusAt(nowMillis)` dengan aturan berikut (berurutan berdasarkan prioritas):

1. **DISABLED**:
   Jika `active == false`, maka status langsung menjadi `DISABLED`, tidak peduli berapa nilai `expiresAt`. Flag `disabled` mengambil prioritas tertinggi.
2. **EXPIRED**:
   Jika `active == true`, namun `expiresAt == null`, `expiresAt <= 0L`, atau `expiresAt <= nowMillis`, maka status menjadi `EXPIRED`.
   *Catatan*: User tanpa tanggal expiry (`null` / `<= 0`) secara otomatis dianggap kadaluarsa (`EXPIRED`).
3. **ACTIVE**:
   Jika `active == true` DAN `expiresAt > nowMillis`, maka status adalah `ACTIVE`.

---

## 3. Data Transfer Objects (DTOs)

File Source: [UserManagementModels.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/model/UserManagementModels.kt)

### `CreateUserRequest`
```kotlin
class CreateUserRequest(
    val username: String,
    val email: String,
    val password: String,
    val deviceId: String,
    val expiresAtMillis: Long
)
```

### `CreatedUser`
```kotlin
data class CreatedUser(val uid: String)
```
