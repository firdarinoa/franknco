<?php
//CURL POST function
function cURL_POST($post,$url){
  $ch = curl_init();

  //Set the POST Register Customer, number of POST vars, POST data
  curl_setopt($ch, CURLOPT_URL, $url);
  curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 2);//The number of seconds to wait while trying to connect. Use 0 to wait indefinitely.
  curl_setopt($ch, CURLOPT_POST, true);
  curl_setopt($ch, CURLOPT_POSTFIELDS, $post);
  curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

  //execute post
  $result = curl_exec($ch);
  $data = $result;
  return $data;
}

//CURL GET function
function cURL_GET($url = NULL){
  $ch = curl_init();

  //set the POST login, number of POST vars, POST data
  curl_setopt($ch, CURLOPT_URL, $url);
  curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 0);//The number of seconds to wait while trying to connect. Use 0 to wait indefinitely.
  curl_setopt($ch, CURLOPT_TIMEOUT, 5); //timeout in seconds
  curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

  //execute post
  $result = curl_exec($ch);
  $data = $result;
  return $data;
}
?>
