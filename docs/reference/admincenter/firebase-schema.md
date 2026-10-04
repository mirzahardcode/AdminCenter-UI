# Firebase Realtime Database Schema

Dokumen ini mendokumentasikan struktur Firebase Realtime Database (RTDB) aktual yang digunakan oleh AdminCenter.

---

## Database Target

* **Database URL**: `https://ujianxthax-default-rtdb.asia-southeast1.firebasedatabase.app/`
* **Firebase Project ID**: `ujianxthax`

---

## Realtime Database Tree

```text
/
├── admins/
│   └── {uid}/
│       ├── role: String       (must be "admin")
│       └── active: Boolean    (must be true)
│
└── users/
    └── {uid}/
        ├── username: String
        ├── email: String
        ├── deviceId: String
        ├── deviceBound: Boolean
        ├── deviceBoundAt: Long
        ├── active: Boolean
        ├── expiresAt: Long
        └── createdAt: Long
```

---

## Field Specifications

### Node `admins/{uid}`

| Field | Data Type | Requirement / Description |
| :--- | :--- | :--- |
| `role` | `String` | Harus bernilai `"admin"` agar diberi akses. |
| `active` | `Boolean` | Harus `true` agar admin diizinkan masuk ke sistem. |

### Node `users/{uid}`

| Field | Data Type | Nullable | Default / Format | Description |
| :--- | :--- | :--- | :--- | :--- |
| `username` | `String` | No | `""` | Nama pengguna. |
| `email` | `String` | No | `""` | Alamat email user. |
| `deviceId` | `String` | No | `""` | Identifier perangkat yang terikat. |
| `deviceBound` | `Boolean` | No | `false` | Indicator apakah perangkat sudah terikat. |
| `deviceBoundAt` | `Long` | No | `0L` | Timestamp Epoch millis saat device diikat. |
| `active` | `Boolean` | No | `false` | Flag aktif/nonaktif akun user. |
| `expiresAt` | `Long` | Yes | `null` | Timestamp Epoch millis (UTC end-of-day `23:59:59.999`) tanggal kadaluarsa. |
| `createdAt` | `Long` | No | `0L` | Timestamp Epoch millis pembuatan akun. |

---

## Notes on Structure

* **Primary User Key**: Key child di bawah `users/` adalah Firebase Auth `uid` milik pengguna. Tidak ada internal numeric ID atau uuid terpisah.
* **Absence of Application Scope Node**: Tidak ada node sub-path `apps/`, `products/`, atau `subscriptions/` di dalam RTDB. Semua user disimpan langsung under root `/users/{uid}`.
