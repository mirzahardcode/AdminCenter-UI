# Device Binding Structure & UniversalAuthCenter Alignment

Dokumen ini menjelaskan struktur data binding perangkat dan rekomendasi batas cakupan untuk Batch 4 UniversalAuthCenter.

---

## 1. Existing RTDB Fields

Pada node `users/{uid}`, terdapat 3 field yang mengelola perangkat:

```text
users/{uid}/
├── deviceId: String       (Default: "" atau ID perangkat)
├── deviceBound: Boolean   (Default: false, true jika terikat)
└── deviceBoundAt: Long    (Default: 0L, Unix ms timestamp saat terikat)
```

---

## 2. Operasi Admin Center pada Device Binding

AdminCenter menyediakan aksi **Clear Device Binding** untuk mengizinkan reset pendaftaran perangkat user jika terjadi pergantian HP / reset app.

Method di [FirebaseUserRepository.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/repository/FirebaseUserRepository.kt):

```kotlin
override suspend fun clearDeviceBinding(uid: String): Result<Unit> =
    runCatchingCancellable {
        val updates = mapOf<String, Any>(
            "deviceId" to "",
            "deviceBound" to false,
            "deviceBoundAt" to 0L
        )
        usersRef.child(uid).updateChildren(updates).await()
        invalidate()
        Unit
    }
```

---

## 3. Catatan Penting untuk Batch 4 UniversalAuthCenter

* **Batch 4 Scope**: UniversalAuthCenter **tidak akan mengimplementasikan penegakan (enforcement) device binding pada Batch 4**.
* **Batch 6 Alignment**: Penegakan aturan device binding akan ditangani pada Batch 6.
* **Integrasi Schema**: Field `deviceId`, `deviceBound`, dan `deviceBoundAt` **harus dipertahankan sesuai struktur aktual saat ini** agar tidak terjadi bentrok skema saat pengembangan Batch 6 dimulai.
