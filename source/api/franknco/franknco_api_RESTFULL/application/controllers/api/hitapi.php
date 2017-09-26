<?php defined('BASEPATH') OR exit('No direct script access allowed');
header('Access-Control-Allow-Origin: *');
// This can be removed if you use __autoload() in config.php OR use Modular Extensions
require APPPATH . '/libraries/REST_Controller.php';

/**
 * This is an example of a few basic user interaction methods you could use
 * all done with a hardcoded array
 *
 * @package         CodeIgniter
 * @subpackage      Rest Server
 * @category        Controller
 * @author          Phil Sturgeon, Chris Kacerguis
 * @license         MIT
 * @link            https://github.com/chriskacerguis/codeigniter-restserver
 */
class hitapi extends REST_Controller {
    function __construct()
    {
        // Construct the parent class
        parent::__construct();
        $this->load->helper('log_helper');
        date_default_timezone_set("Asia/Jakarta");

        // Ensure you have created the 'limits' table and enabled 'limits' within application/config/rest.php
        $this->methods['hitaps_post']['limit'] = 9999999999; // 100 requests per hour per user/key
        $this->methods['hitinit_post']['limit'] = 9999999999; // 100 requests per hour per user/key
    }

    // HIT API APS
    public function hitaps_post(){
      $LOG_CTR = "HitAPS_Post";
      echo createlog(date("Y-m-d H:i:s")." ".$LOG_CTR." - Post Data Incoming\n");

      // Menerima data tipe POST
      $post = $this->input->post();
	  
      // Menerima data tipe JSON
      //$data = file_get_contents('php://input');
      //$post = json_decode($data, true);

      $LOG_CTR = "HitAPS_Response";
      if($post==NULL){
        echo createlog(date("Y-m-d H:i:s")." ".$LOG_CTR." - Post Data Must be JSON\n");
        $this->response(array('code' => '0003','msg' => 'Post Data Must be JSON'), REST_Controller::HTTP_OK); // 200 being the HTTP response code
      }else{
        $url = URL_API_SERVER.'api/franknco/franknco/index.php/api/franknco_api/hitapi';
        // Start checking token
        // 400 = Untuk Change Password
        // 500 = Untuk Get User Key for Decrypt User Credit
        // 600 = Untuk Get Card Credit
        // 700 = Untuk Get User Card List
        // 800 = Untuk Get Card Transaction List
        // 900 = Untuk Register Card
        $code = array('0400','0500','0600','0700','0800','0900');
        if (in_array($post['code'], $code, TRUE)){
          if($post['token']!=''){
            // Start explode get user
            $msg = $post['msg'];
            $msg_info = explode('#',$msg);
            $user     = $msg_info[0];
            $token    = $post['token'];
            // End explode get user

            $data = array(
              'code'  => '0000',
              'msg'   => $user.'#'.$token,
            );
            $server_output = $this->cURL_POST($data,$url);
            if($server_output=='EXIST'){
              $server_output = $this->cURL_POST($post,$url);
              // $server_output = json_decode($this->cURL_POST($post,$url),true);
              echo createlog(date("Y-m-d H:i:s")." ".$LOG_CTR." - Return Data Outgoing\n");
              $this->response($server_output, REST_Controller::HTTP_OK); // 200 being the HTTP response code
            }elseif($server_output=='TIMEOUT'){
              echo createlog(date("Y-m-d H:i:s")." ".$LOG_CTR." - Token Expired, Generate New Token!\n");
              $this->response(array('code' => '0005','msg' => 'Token Expired, Generate New Token'), REST_Controller::HTTP_OK); // 200 being the HTTP response code
            }else{
              echo createlog(date("Y-m-d H:i:s")." ".$LOG_CTR." - User and Token not Match!\n");
              $this->response(array('code' => '0006','msg' => 'User and Token not Match'), REST_Controller::HTTP_OK); // 200 being the HTTP response code
            }
            // End checking token
          }else{
            echo createlog(date("Y-m-d H:i:s")." ".$LOG_CTR." - Token is Empty!\n");
            $this->response(array('code' => '0004','msg' => 'Token is Empty'), REST_Controller::HTTP_OK); // 200 being the HTTP response code
          }
        }else {
          $server_output = $this->cURL_POST($post,$url);
          // $server_output = json_decode($this->cURL_POST($post,$url),true);
          echo createlog(date("Y-m-d H:i:s")." ".$LOG_CTR." - Return Data Outgoing\n");
          $this->response($server_output, REST_Controller::HTTP_OK); // 200 being the HTTP response code
        }
      }
    }

    private function cURL_POST($post,$url){
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
      $data = json_decode($result,true);
      return $data;
    }
}
