## **Laporan Praktikum Pemrograman Platform**

**Nama:** [Nama Anda]  
**Kelas:** [Kelas Anda]  
**Mata Kuliah:** Pemrograman Platform  
**Tanggal:** [Tanggal Praktikum]

---

### **Judul Kegiatan**
Membuat Aplikasi Android Pertama dengan Modifikasi Template Menggunakan Kotlin

---

### **Tujuan Praktikum**
1. Memahami cara menyiapkan lingkungan pengembangan untuk pengembangan aplikasi Android.
2. Membuat aplikasi Android sederhana menggunakan Android Studio.
3. Memodifikasi tampilan dan fungsionalitas aplikasi menggunakan bahasa pemrograman Kotlin.

---

### **Alat dan Bahan**
1. **Komputer atau Laptop**
    - Spesifikasi minimum sesuai kebutuhan Android Studio.
2. **Android Studio** (dapat diunduh di [https://developer.android.com/studio](https://developer.android.com/studio))
3. **Koneksi Internet**
    - Untuk mengunduh Android Studio dan dependensi proyek.

---

### **Langkah-Langkah Praktikum**

#### **Langkah 1: Persiapan Lingkungan Pengembangan**
1. Unduh Android Studio dari [https://developer.android.com/studio](https://developer.android.com/studio).
2. Pastikan komputer memenuhi persyaratan sistem minimum, seperti RAM 8 GB dan ruang penyimpanan yang cukup.
3. Instal Android Studio dengan mengikuti langkah-langkah pada wizard instalasi.
4. Verifikasi instalasi dengan membuka Android Studio dan memastikan tampilannya normal.

---

#### **Langkah 2: Membuat Proyek Aplikasi Android**
1. Buka Android Studio, klik **"New Project"**.
2. Pilih template **"Empty Activity"** di bawah kategori **"Phone and Tablet"**.
3. Isi informasi proyek:
    - **Nama Proyek:** Perpustakaan
    - **Package Name:** com.polstat.perpustakaan
    - **Lokasi Penyimpanan:** [Pilih folder sesuai keinginan].
4. Klik tombol **Finish**, tunggu hingga proses pengunduhan dan sinkronisasi selesai.
5. Setelah selesai, tampilan editor Android Studio akan menampilkan file **MainActivity.kt** dan struktur proyek.

---

#### **Langkah 3: Menjalankan Aplikasi Android**
1. Klik tombol **Run** (ikon segitiga hijau) di toolbar Android Studio.
2. Pilih perangkat (Emulator atau Perangkat Fisik) untuk menjalankan aplikasi.
3. Tunggu proses build dan deployment selesai.
4. Aplikasi akan berjalan, menampilkan layar dengan teks default "Hello World!" atau yang telah ditentukan oleh template.

---

#### **Langkah 4: Memodifikasi Tampilan Teks**
1. Buka file **MainActivity.kt**.
2. Perhatikan fungsi `Greeting()` dengan anotasi `@Composable`. Fungsi ini digunakan untuk menampilkan teks di layar.
3. Modifikasi teks pada fungsi `Greeting()` menjadi:
   ```kotlin
   fun Greeting(name: String, modifier: Modifier = Modifier) {
       Text(
           text = "Selamat Datang di Perpustakaan $name!", 
           modifier = modifier
       )
   }
   ```  
4. Ubah parameter `Greeting("Android")` di fungsi `onCreate()` menjadi:
   ```kotlin
   Greeting("Politeknik Statistik - STIS")
   ```  
5. Klik tombol **Run** kembali untuk melihat perubahan.
6. Teks pada layar akan berubah sesuai modifikasi.

---

### **Kode Program Selengkapnya**

```kotlin
package com.polstat.perpustakaan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.polstat.perpustakaan.ui.theme.PerpustakaanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PerpustakaanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Politeknik Statistik - STIS")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Selamat Datang di Perpustakaan $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PerpustakaanTheme {
        Greeting("Android")
    }
}
```

---

### **Hasil Praktikum**
1. Aplikasi berhasil dibuat dan dijalankan dengan tampilan teks:  
   *"Selamat Datang di Perpustakaan Politeknik Statistik - STIS!"*
2. Modifikasi pada fungsi `Greeting()` berhasil dilakukan dan tampil sesuai dengan perubahan yang diinginkan.

---

### **Kesimpulan**
1. Android Studio merupakan alat yang efektif untuk mengembangkan aplikasi Android dengan antarmuka yang user-friendly.
2. Kotlin sebagai bahasa pemrograman memungkinkan modifikasi yang mudah dan efisien pada aplikasi Android.
3. Praktikum ini memberikan pemahaman dasar tentang pengembangan aplikasi Android, mulai dari membuat proyek, menjalankan aplikasi, hingga memodifikasi tampilannya.

---

### **Saran**
1. Pastikan perangkat keras komputer memenuhi spesifikasi minimum untuk menghindari lag atau crash selama pengembangan.
2. Pelajari lebih lanjut tentang elemen-elemen lain dalam Compose untuk meningkatkan kemampuan pengembangan aplikasi Android.

--- 

**Tanda Tangan dan Nama Praktikan:**  
[_________________________]  