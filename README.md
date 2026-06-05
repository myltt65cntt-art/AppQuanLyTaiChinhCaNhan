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
  Giao diện đăng ký cho phép người dùng tạo tài khoản mới để sử dụng ứng dụng SpendWise. Người dùng nhập họ tên, email, mật khẩu và xác nhận mật khẩu. Khi nhấn nút Đăng ký, hệ thống kiểm tra dữ liệu đầu vào và sử dụng Firebase Authentication để tạo tài khoản. Nếu đăng ký thành công, người dùng sẽ được chuyển đến màn hình thông báo đăng ký thành công.
  <p align="center">
<img width="250" alt="image" src="https://github.com/user-attachments/assets/426dd199-f00d-4664-8928-2369a37e2e32" />

  </p>
    <p align="center">
      <i>Giao diện chính ứng dụng SpendWise</i>
  </p>

Giao diện chính là màn hình trung tâm của ứng dụng, cho phép người dùng theo dõi tình hình tài chính cá nhân một cách trực quan.

Màn hình hiển thị các thông tin quan trọng gồm:

- Tổng thu nhập
- Tổng chi tiêu
- Số dư hiện tại
- Danh sách các giao dịch thu chi

Dữ liệu được đọc từ Firebase Realtime Database thông qua `ValueEventListener`, giúp ứng dụng tự động cập nhật khi có thay đổi dữ liệu.

Danh sách giao dịch được hiển thị bằng `RecyclerView` kết hợp với `TransactionAdapter`, hỗ trợ hiển thị số lượng lớn giao dịch một cách tối ưu.

Hệ thống tự động duyệt danh sách giao dịch để tính toán tổng thu nhập, tổng chi tiêu và số dư hiện tại theo công thức:

`Số dư = Tổng thu nhập - Tổng chi tiêu`

Ngoài ra, người dùng có thể nhấn vào giao dịch để chỉnh sửa hoặc nhấn giữ để xóa giao dịch. Mọi thay đổi đều được đồng bộ thời gian thực với Firebase Realtime Database.
  
 <p
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
   Giao diện nhập giao dịch cho phép người dùng thêm mới các khoản thu nhập hoặc chi tiêu vào hệ thống.

Người dùng có thể nhập các thông tin gồm:

- Số tiền giao dịch
- Loại giao dịch (Thu nhập hoặc Chi tiêu)
- Danh mục
- Ngày giao dịch
- Ghi chú

Sau khi nhấn nút **Lưu**, hệ thống kiểm tra tính hợp lệ của dữ liệu đầu vào. Nếu hợp lệ, ứng dụng sẽ tạo một đối tượng `TransactionModel` chứa toàn bộ thông tin giao dịch.

Dữ liệu sau đó được lưu lên Firebase Realtime Database theo cấu trúc:

`transactions → UID → TransactionID`

Trong đó `UID` là mã người dùng hiện tại được lấy từ Firebase Authentication và `TransactionID` được tạo tự động bằng phương thức `push()` của Firebase.

Sau khi lưu thành công, giao dịch sẽ được hiển thị ngay trên màn hình Trang chủ nhờ cơ chế đồng bộ dữ liệu thời gian thực của Firebase Realtime Database.
  <p align="center">
 <img width="250"alt="image" src="https://github.com/user-attachments/assets/587e9c98-3f6d-4e0d-a951-d4bd6562097f" />
<img width="250" alt="image" src="https://github.com/user-attachments/assets/6937f36c-4088-4710-b0f5-94629b20d3e1" />

</p>
 </p>
   <p align="center">
       <i>hũ tiết kiệm</i>
  </p>
  Chức năng hũ tiết kiệm giúp người dùng quản lý các mục tiêu tiết kiệm cá nhân như du lịch, mua sắm hoặc các khoản chi tiêu trong tương lai.

Người dùng có thể tạo nhiều hũ tiết kiệm khác nhau bằng cách nhập tên hũ và số tiền mục tiêu. Mỗi hũ sẽ hiển thị số tiền hiện có, số tiền mục tiêu và tiến độ hoàn thành thông qua thanh ProgressBar.

Ứng dụng hỗ trợ các chức năng:

- Tạo hũ tiết kiệm mới
- Góp tiền vào hũ
- Rút tiền từ hũ
- Xóa hũ tiết kiệm

Khi người dùng góp tiền hoặc rút tiền, hệ thống sẽ tự động cập nhật số dư của hũ và đồng thời tạo giao dịch tương ứng trong hệ thống thu chi để đảm bảo số dư tài chính luôn chính xác.

Toàn bộ dữ liệu hũ tiết kiệm được lưu trữ trên Firebase Realtime Database và tự động đồng bộ theo thời gian thực với tài khoản người dùng.
   <p align="center">
<img width="250" alt="image" src="https://github.com/user-attachments/assets/f0f237b7-f412-43c0-8c28-6dacfb73397b" />


</p>
 </p>
   <p align="center">
       <i>Quên mật khẩu</i>
  </p>
  Link Google Drive:
  https://drive.google.com/file/d/1n2ZgG_DjlPVdlFHwVbzmXd-0v1cqnh_K/view?usp=drive_link
