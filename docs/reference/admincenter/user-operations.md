# User Operations & Data Layer Behavior

Dokumen ini menjelaskan seluruh operasi manajemen user yang diimplementasikan pada AdminCenter.

---

## Operations Overview

| Operation | Implementation Class | Target Path / Service | Authentication / Protocol |
| :--- | :--- | :--- | :--- |
| **Get Users List** | `FirebaseUserRepository.getUsers()` | RTDB `users` | Firebase RTDB `.get().await()` |
| **Get Single User** | `FirebaseUserRepository.getUser(uid)` | RTDB `users/{uid}` | Firebase RTDB `.get().await()` |
| **Create User** | `HttpPrivilegedUserRepository.createUser(...)` | HTTP POST `/createUser` | Bearer Token Admin ID Token |
| **Delete User** | `HttpPrivilegedUserRepository.deleteUser(uid)` | HTTP POST `/deleteUser` | Bearer Token Admin ID Token |
| **Set Active / Disable** | `FirebaseUserRepository.setActive(uid, active)` | RTDB `users/{uid}/active` | Firebase RTDB `.updateChildren()` |
| **Set Expiry** | `FirebaseUserRepository.setExpiry(uid, millis)` | RTDB `users/{uid}/expiresAt` | Firebase RTDB `.updateChildren()` |
| **Clear Device Binding** | `FirebaseUserRepository.clearDeviceBinding(uid)` | RTDB `users/{uid}` | Firebase RTDB `.updateChildren()` |

---

## Detailed Operation Flow

### 1. Create User
* **Function**: `HttpPrivilegedUserRepository.createUser(request: CreateUserRequest)`
* **HTTP Endpoint**: `POST https://backend-admin-theta.vercel.app/createUser`
* **Headers**:
  * `Content-Type: application/json`
  * `Authorization: Bearer <ADMIN_ID_TOKEN>`
* **Request JSON Body**:
  ```json
  {
    "username": "john_doe",
    "email": "john@example.com",
    "password": "secretPassword123",
    "deviceId": "device_identifier",
    "expiresAt": 1735689599999,
    "expiresAtMillis": 1735689599999
  }
  ```
* **Response Body**:
  ```json
  {
    "uid": "GeneratedFirebaseUid123"
  }
  ```
* **Backend Side Effects**: Backend membuat user di Firebase Authentication dan menulis node awal di RTDB `users/{uid}`.

---

### 2. Delete User
* **Function**: `HttpPrivilegedUserRepository.deleteUser(uid: String)`
* **HTTP Endpoint**: `POST https://backend-admin-theta.vercel.app/deleteUser`
* **Headers**:
  * `Content-Type: application/json`
  * `Authorization: Bearer <ADMIN_ID_TOKEN>`
* **Request JSON Body**:
  ```json
  {
    "uid": "TargetFirebaseUid123"
  }
  ```
* **Backend Side Effects**: Backend menghapus user dari Firebase Authentication dan menghapus node `users/{uid}` dari RTDB.

---

### 3. Set Active Status (Enable / Disable)
* **Function**: `FirebaseUserRepository.setActive(uid: String, active: Boolean)`
* **RTDB Direct Write**: `users/{uid}`
* **Updates Map**:
  ```kotlin
  mapOf<String, Any>("active" to active)
  ```

---

### 4. Set Expiry Date
* **Function**: `FirebaseUserRepository.setExpiry(uid: String, expiresAtMillis: Long)`
* **RTDB Direct Write**: `users/{uid}`
* **Updates Map**:
  ```kotlin
  mapOf<String, Any>("expiresAt" to expiresAtMillis)
  ```

---

### 5. Clear Device Binding
* **Function**: `FirebaseUserRepository.clearDeviceBinding(uid: String)`
* **RTDB Direct Write**: `users/{uid}`
* **Updates Map**:
  ```kotlin
  mapOf<String, Any>(
      "deviceId" to "",
      "deviceBound" to false,
      "deviceBoundAt" to 0L
  )
  ```
