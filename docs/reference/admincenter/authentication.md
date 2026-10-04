# Authentication Flow & Architecture

Dokumen ini menjelaskan aliran otentikasi (Authentication) dan otorisasi (Authorization) untuk AdminCenter.

---

## 1. Admin Authentication

* **Class Implementasi**: [FirebaseAdminAuthRepository.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/repository/FirebaseAdminAuthRepository.kt)
* **Auth Service**: Firebase Authentication via Email & Password (`auth.signInWithEmailAndPassword(email, password)`).
* **Role Verification (Otorisasi Admin)**:
  1. Setelah Firebase Auth berhasil mengembalikan `FirebaseUser`, sistem membaca node Realtime Database `admins/{uid}`.
  2. Memeriksa dua properti:
     * `role == "admin"`
     * `active == true`
  3. Jika kedua kondisi terpenuhi, Session diset menjadi `SessionState.Authorized(AdminProfile(uid, email))`.
  4. Jika kondisi gagal, sistem secara otomatis melakukan `auth.signOut()`, mengeset `SessionState.SignedOut`, dan mengembalikan error `AdminNotAuthorizedException`.

```kotlin
// Snippet dari FirebaseAdminAuthRepository.kt
private suspend fun verifyAdmin(user: FirebaseUser, forceTokenRefresh: Boolean): AdminProfile? {
    if (forceTokenRefresh) user.getIdToken(true).await()
    val snapshot = database.getReference("admins").child(user.uid).get().await()
    val role = snapshot.child("role").value as? String
    val active = snapshot.child("active").value as? Boolean
    return if (role == "admin" && active == true) {
        AdminProfile(uid = user.uid, email = user.email.orEmpty())
    } else {
        null
    }
}
```

---

## 2. User Authentication & Account Management

* **Client User Sign-In**: Aplikasi `AdminCenter-UI` **TIDAK** memproses sign-in pengguna biasa.
* **User Creation & Deletion**:
  * Menggunakan Privileged Backend API yang berlokasi di: `https://backend-admin-theta.vercel.app/`
  * Diimplementasikan di [HttpPrivilegedUserRepository.kt](../../../app/src/main/java/com/mirzadev/admincenter/data/repository/HttpPrivilegedUserRepository.kt).
  * Request menyertakan Firebase Auth ID Token dari Admin di header `Authorization: Bearer <token>`.
  * Backend Service menggunakan Firebase Admin SDK untuk:
    1. Membuat/menghapus user di Firebase Auth.
    2. Menulis/menghapus node `users/{uid}` di Realtime Database.

---

## 3. Identitas & Mapping Identifier

* **Firebase Auth UID**: Menjadi identifier tunggal dan utama.
* **Mapping UID → User Record**: Realtime Database menyimpan record user langsung dengan key `{uid}` di under node `/users`.
* **Email**: Disimpan di properti `email` pada node `users/{uid}` di RTDB serta di Firebase Auth.
