```markdown
# Manajemen Ujian Online & Bank Soal

## Deskripsi Proyek
Proyek ini adalah sistem Manajemen Ujian Online dan Bank Soal yang dikembangkan untuk Polstat-STIS. Sistem ini memungkinkan pembuatan, pengelolaan, dan pengiriman ujian secara online, serta penyimpanan dan pengelolaan soal ujian.

## Fitur Utama
- **Autentikasi dan Otorisasi Pengguna**: Registrasi, login, dan manajemen peran pengguna (Admin, Dosen, Mahasiswa).
- **Manajemen Pengguna**: Melihat dan memperbarui profil pengguna, mengubah peran, mengganti kata sandi, dan menghapus akun.
- **Manajemen Ujian**: Membuat, mengedit, menghapus, dan melihat ujian.
- **Pengiriman Ujian**: Mahasiswa dapat mengirim jawaban ujian dan mendapatkan hasil.

## Teknologi yang Digunakan
- **Backend**: Java, Spring Boot, JPA (Hibernate)
- **Keamanan**: JWT (JSON Web Token), Spring Security
- **Database**: MySQL
- **Dokumentasi API**: Swagger

## Struktur Direktori
src/main/java/com/polstatstis/digiexam 
├── config/
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   ├── ExamController.java
│   ├── ExamResultController.java
│   ├── QuestionController.java
│   └── UserController.java
├── dto/
│   ├── AnswerDTO.java
│   ├── ChangePasswordDTO.java
│   ├── ExamDTO.java
│   ├── ExamResultDTO.java
│   ├── ExamSubmissionDTO.java
│   ├── QuestionDTO.java
│   ├── UserDTO.java
│   ├── UserLogin.java
│   └── UserRegistrationDTO.java
├── entity/
│   ├── Exam.java
│   ├── ExamResult.java
│   ├── Question.java
│   └── Userr.java
├── exception/
│   ├── ExamNotFoundException.java
│   ├── ExamResultNotFoundException.java
│   ├── JwtAuthenticationException.java
│   ├── QuestionNotFoundException.java
│   └── UserNotFoundExceptionHandler.java
├── mapper/
│   ├── ExamMapper.java
│   ├── ExamResultMapper.java
│   ├── QuestionMapper.java
│   ├── UserLoginMapper.java
│   └── UserMapper.java
├── repository/
│   ├── ExamRepository.java
│   ├── ExamResultRepository.java
│   ├── QuestionRepository.java
│   └── UserRepository.java
├── security/
│   ├── CustomUserDetails.java
│   ├── JwtAuthenticationEntryPoint.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtService.java
├── service/
│   ├── AuthService.java
│   ├── ExamResultService.java
│   ├── ExamService.java
│   ├── QuestionService.java
│   ├── UsersDetailsServiceImpl.java
│   └── UserService.java
└── DIgiexamApplication.java