<?php
$data = array();

include('connect3.php');

if (isset($_GET['date'])) {
  $date = $_GET['date'];

  if (empty($date)) {
      $errMSG = "날짜를 입력해주세요.";
  }

  if (!isset($errMSG)) {
      try {
          $stmt = $conn->prepare('SELECT * FROM happy WHERE date = ?');
          $stmt->bind_param('s', $date);

          if ($stmt->execute()) {
              $result = $stmt->get_result();
              $row = $result->fetch_assoc();

              $data = array(
                  'weather' => $row['weather'],
                  'mood' => $row['mood'],
                  'diary' => $row['diary']
              );
          } else {
              $errMSG = "데이터를 불러오는데 실패했습니다.";
          }
      } catch (PDOException $e) {
          die("Database error: " . $e->getMessage());
      }
  }
} else {
  // "date" 매개변수가 없을 때의 처리 로직을 추가합니다.
  $errMSG = "날짜를 지정해주세요.";
}

?>

<html>
    <body>
        <form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="GET">
            <div>
                <label for="datePicker">날짜:</label>
                <input type="date" id="datePicker" name="date" required>
            </div>

            <button type="submit" name="retrieveButton">불러오기</button>
        </form>

        <div>
            <label for="weatherTextView">날씨:</label>
            <p id="weatherTextView"><?php echo isset($data['weather']) ? $data['weather'] : ''; ?></p>
        </div>

        <div>
            <label for="moodTextView">기분:</label>
            <p id="moodTextView"><?php echo isset($data['mood']) ? $data['mood'] : ''; ?></p>
        </div>

        <div>
            <label for="diaryTextView">일기:</label>
            <p id="diaryTextView"><?php echo isset($data['diary']) ? $data['diary'] : ''; ?></p>
        </div>

        <?php
        if (isset($errMSG)) {
          echo "<p>Error: " . $errMSG . "</p>";
        }
        if (isset($successMSG)) {
          echo "<p>Success: " . $successMSG . "</p>";
        }
        ?>
    </body>
</html>
