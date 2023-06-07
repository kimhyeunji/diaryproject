<?php
    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('connect2.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['saveButton'])) || $android )
    {
        $date = $_POST['date'];
        $weather = $_POST['weather'];
        $mood = $_POST['mood'];
        $diary = $_POST['diary'];

        if(empty($date)){
            $errMSG = "날짜";
        }
        else if(empty($weather)){
            $errMSG = "날씨";
        }
        else if(empty($mood)){
            $errMSG = "기분";
        }
        else if(empty($diary)){
            $errMSG = "일기 내용";
        }

        if(!isset($errMSG)){
            try{
                $stmt = $conn->prepare('INSERT INTO happy(date, weather, mood, diary) VALUES(?, ?, ?, ?)');
                $stmt->bind_param('ssss', $date, $weather, $mood, $diary);

                if($stmt->execute())
                {
                    $successMSG = "SUCCESS";
                }
                else
                {
                    $errMSG = "FAIL";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage());
            }
        }
    }
?>

<?php
    if (isset($errMSG)) {
        echo "Error: " . $errMSG;
    }
    if (isset($successMSG)) {
        echo "Success: " . $successMSG;
    }
?>

<html>
    <body>
        <form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="POST">
            <div>
                <label for="datePicker">날짜:</label>
                <input type="date" id="datePicker" name="date" required>
            </div>

            <div>
                <label for="weatherSelect">날씨:</label>
                <select id="weatherSelect" name="weather" required>
                    <option value="">날씨 선택</option>
                    <option value="맑음">맑음</option>
                    <option value="흐림">흐림</option>
                    <option value="비">비</option>
                    <option value="눈">눈</option>
                </select>
            </div>

            <div>
                <label for="moodSelect">기분:</label>
                <select id="moodSelect" name="mood" required>
                    <option value="">기분 선택</option>
                    <option value="좋음">좋음</option>
                    <option value="평범">평범</option>
                    <option value="나쁨">나쁨</option>
                </select>
            </div>

            <div>
                <label for="diaryTextarea">일기 내용:</label>
                <textarea id="diaryTextarea" name="diary" rows="4" cols="50" required></textarea>
            </div>

            <button type="submit" name="saveButton">저장하기</button>
        </form>
    </body>
</html>