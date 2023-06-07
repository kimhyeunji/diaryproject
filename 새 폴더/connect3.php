<?php
$db_name = "diary";
$mysql_username = "root";
$mysql_password = "";
$server_name = "10.0.2.2";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

if ($conn) {
    // 문자 인코딩 설정
    mysqli_set_charset($conn, "utf8");
    echo "Connection success";
} else {
    echo "Connection failed: " . mysqli_connect_error();
}
?>
