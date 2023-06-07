<?php
$data = array();

include('connect2.php');

if (isset($_GET['date'])) {
    $date = $_GET['date'];
    #echo "Requested Date: " . $date . "\n"; // 디버깅 문장

    if (empty($date)) {
        $errMSG = "Please enter a date.";
    }

    if (!isset($errMSG)) {
        try {
            $stmt = $conn->prepare('SELECT * FROM happy WHERE date = ?');
            $stmt->bind_param('s', $date);

            if ($stmt->execute()) {
                $result = $stmt->get_result();
                if ($result->num_rows > 0) {
                    while ($row = $result->fetch_assoc()) {
                        $data[] = $row;
                    }
                    #echo "Fetched Data: \n";
                    print_r($data); // 디버깅 문장
                } else {
                    $errMSG = "No data available for the specified date.";
                }
            } else {
                $errMSG = "Failed to load data.";
            }
        } catch (Exception $e) {
            $errMSG = "Database error: " . $e->getMessage();
        }
    }
} else {
    $errMSG = "Please specify a date.";
}

if (isset($errMSG)) {
    $data = array('error' => $errMSG);
}

header('Content-Type: application/json; charset=utf8');
$json = json_encode($data, JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
echo $json;
?>
