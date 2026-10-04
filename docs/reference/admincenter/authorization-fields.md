# Authorization Fields & Behavior

Dokumen ini menjelaskan spesifikasi rinci mengenai dua field otorisasi utama dalam AdminCenter: `active` dan `expiresAt`.

---

## 1. Field `active`

* **RTDB Path**: `users/{uid}/active`
* **Data Type**: `Boolean`
* **Definisi**:
  * `true`: Akun diizinkan aktif / di-enable oleh admin.
  * `false`: Akun di-disable / diblokir oleh admin.
* **Operasi Update**:
  * Diubah melalui fungsi `FirebaseUserRepository.setActive(uid, active)` dengan metode `updateChildren(mapOf("active" to active))`.
* **Aturan di UniversalAuthCenter**:
  * Field ini adalah flag utama ketersediaan akun. Jika `active == false`, otorisasi pengguna harus ditolak (Disabled).

---

## 2. Field `expiresAt`

* **RTDB Path**: `users/{uid}/expiresAt`
* **Data Type**: `Long` (milliseconds since Unix epoch)
* **Timezone Standard**: `UTC` (`ZoneOffset.UTC`).
* **Format Timestamp**:
  * Diisi dengan timestamp millisecond pada akhir hari UTC: `23:59:59.999`.
  * Dihitung menggunakan helper `ExpiryDates.endOfDayMillis(date)`:
    ```kotlin
    fun endOfDayMillis(date: LocalDate): Long =
        date.plusDays(1).atStartOfDay(ZONE).toInstant().toEpochMilli() - 1L
    ```
* **Interpretasi Nilai**:
  * `null` atau `<= 0L`: Dianggap `EXPIRED`.
  * `expiresAt <= nowMillis`: Dianggap `EXPIRED`.
  * `expiresAt > nowMillis`: Dianggap belum kadaluarsa.
* **Operasi Update**:
  * Diubah melalui fungsi `FirebaseUserRepository.setExpiry(uid, expiresAtMillis)` dengan metode `updateChildren(mapOf("expiresAt" to expiresAtMillis))`.

---

## 3. Presets & Perpanjangan Expiry

Helper `ExpiryDates.extend(currentExpiresAt, days, nowMillis)` memperhitungkan tanggal awal perpanjangan:
* Jika `currentExpiresAt` sudah di masa lalu atau `null`, perpanjangan dihitung dari `nowMillis` + `days`.
* Jika `currentExpiresAt` masih berlaku di masa depan, perpanjangan dihitung dari `currentExpiresAt` + `days`.
* Hasilnya dikonversi kembali ke milisekon akhir hari UTC (`23:59:59.999 UTC`).
