<?php
$data = array();

include('connect2.php');

if (isset($_GET['date'])) {
    $date = $_GET['date'];
    #echo "Date parameter: " . $date . "\n"; // Debug statement

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
                    $data = $result->fetch_all(MYSQLI_ASSOC);
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
    // Add processing logic when there is no "date" parameter.
    $errMSG = "Please specify a date.";
}

if (isset($errMSG)) {
    $data['error'] = $errMSG;
}

header('Content-Type: application/json; charset=utf8');
$json = json_encode($data, JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
echo $json;
?>
