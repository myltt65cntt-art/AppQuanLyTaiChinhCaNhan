# SpendWise

Ứng dụng quản lý tài chính cá nhân trên Android giúp người dùng quản lý thu nhập, chi tiêu và theo dõi tình hình tài chính một cách hiệu quả.

## 📱 Màn hình đăng ký

<p align="center">
  <img src="https://github.com/user-attachments/assets/2f379f05-fe00-40a0-92cc-178478749606" width="250"/>
  <img width="250" alt="image" src="https://github.com/user-attachments/assets/3cdfd598-8436-4ca9-8150-42b8496237bf" />
</p>
Chức năng Đăng nhập

Ứng dụng SpendWise sử dụng Firebase Authentication để xác thực người dùng thông qua Email và Mật khẩu.

Khi người dùng nhập thông tin đăng nhập và nhấn nút Đăng nhập, ứng dụng sẽ gửi Email và Mật khẩu đến Firebase Authentication để kiểm tra tính hợp lệ của tài khoản.

Nếu thông tin chính xác, Firebase sẽ xác thực thành công và trả về một UID (User ID) duy nhất cho người dùng.

Sau khi nhận được UID, ứng dụng sử dụng UID này để truy xuất dữ liệu cá nhân từ Firebase Realtime Database, bao gồm:

👤 Thông tin hồ sơ cá nhân
💵 Danh sách giao dịch thu nhập
🛒 Danh sách giao dịch chi tiêu
🖼️ Ảnh đại diện (Avatar) và ảnh bìa (Cover)
Dữ liệu được tải về và hiển thị trên màn hình Trang chủ, cho phép người dùng theo dõi:
Tổng thu nhập
Tổng chi tiêu
Số dư hiện tại
Lịch sử giao dịch
Việc sử dụng Firebase Authentication giúp tăng tính bảo mật, hỗ trợ quản lý nhiều người dùng và đảm bảo mỗi tài khoản chỉ có thể truy cập dữ liệu thuộc về chính mình.

<p align="center">
<img width="250" alt="image" src="https://github.com/user-attachments/assets/846fe0bc-95b1-4a98-bd69-e62188b9c427" />
  <img width="250"  alt="image" src="https://github.com/user-attachments/assets/2e036df2-d1eb-43e3-8e99-b68127fb48d8" />
  </p>
  <p align="center">
      <i>Giao diện đăng kí ứng dụng SpendWise</i>
  </p>
  <p align="center">
<img width="250" alt="image" src="https://github.com/user-attachments/assets/426dd199-f00d-4664-8928-2369a37e2e32" />

  </p>
    <p align="center">
      <i>Giao diện chính ứng dụng SpendWise</i>
  </p>
  <p align="center">
    <img width="250"  alt="image" src="https://github.com/user-attachments/assets/a6043b4d-9667-4c19-8ae2-ef3a826c79d7" />

  </p>
   <p align="center">
      <i>Giao diện nhập chi tiêu ứng dụng SpendWise</i>
  </p>
    <p align="center">
    <img width="250"  alt="image" src="https://github.com/user-attachments/assets/d38db452-bd95-4889-a416-7bcdb9b0ad1b" />
    <img width="250" alt="image" src="https://github.com/user-attachments/assets/0fed6c91-207c-41ff-b0ea-82b98f886f7d" />
  </p>
   <p align="center">
      <i>Giao diện profile ứng dụng SpendWise</i>
  </p>
    <p align="center">
   <img width="250" alt="image" src="https://github.com/user-attachments/assets/51c72dbe-d046-4364-a298-7eabd3eff3a4" />
   <img width="250" alt="image" src="https://github.com/user-attachments/assets/a047c794-052f-418d-8c0e-a0b94aa48085" />



  </p>
   <p align="center">
       <i>Giao diện profile ứng dụng SpendWise</i>
  </p>
  </p>
    <p align="center">
<img width="250" alt="image" src="https://github.com/user-attachments/assets/572fd239-17c0-4a5a-9a7b-20de094d1282" />
      <img width="250" alt="image" src="https://github.com/user-attachments/assets/6f3bb62e-e307-4866-9219-228dc4d4c3e4" />

 </p>
   <p align="center">
       <i>Giao diện nhập liệu</i>
  </p>
