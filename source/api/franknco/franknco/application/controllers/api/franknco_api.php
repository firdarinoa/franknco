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
class franknco_api extends REST_Controller {
    function __construct()
    {
        // Construct the parent class
        parent::__construct();
        $this->load->helper('log_helper');
        $this->load->model('api_model');
        date_default_timezone_set("Asia/Jakarta");

        // Ensure you have created the 'limits' table and enabled 'limits' within application/config/rest.php
        $this->methods['hitapi_post']['limit'] = 9999999999; // 100 requests per hour per user/key
    }

    //HIT API Controller
    public function hitapi_post(){
      $data = file_get_contents('php://input');
      $post = json_decode($data, true);
  	  if(empty($post)){
  		 $post = $this->input->post();
  	  }

      $LOG_CTR = "HitAPI_Post";
      if($post['code']!='' && $post['msg']!='')
      {
          $code = $post['code'];
          switch ($code){
            case '0000': //Untuk Checking Token
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $user     = $msg_info[0];
              $token    = $msg_info[1];
              $data = $this->api_model->checking_token($user,$token);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            case '0100': //Untuk Login
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $user     = $msg_info[0];
              $password = strtoupper($msg_info[1]);
              $data = $this->api_model->login($user,$password);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            case '0200': //Untuk Sign Up
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $card_number = $msg_info[0];
              $fullname = $msg_info[1];
              $dob      = $msg_info[2]; //format Y-m-d (2017-08-13)
              $address  = $msg_info[3];
              $email    = $msg_info[4];
              $hp       = $msg_info[5]; //user
              $password = strtoupper($msg_info[6]);
              $data = $this->api_model->signup($card_number,$fullname,$dob,$address,$email,$hp,$password);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            case '0300': //Untuk Forgot Password
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $user = $msg_info[0];
              $data = $this->api_model->forgot_password($user);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            case '0400': //Untuk Change Password
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $user = $msg_info[0];
              $password = $msg_info[1];
              $new_password = $msg_info[2];
              $data = $this->api_model->change_password($user,$password,$new_password);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            case '0500': //Untuk Get Card Key for Decrypt Card Credit
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $user = $msg_info[0];
              $card_number = $msg_info[1];
              $data = $this->api_model->card_key($user,$card_number);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            case '0600': //Untuk Get Card Credit
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $user = $msg_info[0];
              $card_number = $msg_info[1];
              $data = $this->api_model->card_credit($user,$card_number);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            case '0700': //Untuk Get User Card List
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $user = $msg_info[0];
              $data = $this->api_model->card_list($user);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            case '0800': //Untuk Get Card Transaction List
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $user = $msg_info[0];
              $card_number = $msg_info[1];
              $limit = $msg_info[2];
              $data = $this->api_model->card_transaction($user,$card_number,$limit);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            case '0900': //Untuk Register Card
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $user = $msg_info[0];
              $card_number = $msg_info[1];
              $data = $this->api_model->register_card($user,$card_number);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            // case '0500': //Untuk Send Email
            //   $msg = $this->api_model->oracle_escape_string($post['msg']);
            //   $msg_info = explode('#',$msg);
            //   $trace_number = $msg_info[0];
            //   $email = $msg_info[1];
            //   $hit_data = 'Trace Number:'.serialize($trace_number).'Email:'.serialize($email);
            //   $data = $this->api_model->send_email($trace_number,$email,$hit_data);
            //   $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
            //   break;
            case '1600': //Untuk Encrypt 3 Des
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $text = $msg_info[0];
              $data = $this->api_model->encrypt_3des($text);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            case '1700': //Untuk Decrypt 3 Des
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $text = $msg_info[0];
              $data = $this->api_model->decrypt_3des($text);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            case '1800': //Untuk Encrypt 3 Des Credit
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $text = $msg_info[0];
              $key = $msg_info[1];
              $data = $this->api_model->encrypt_3des_credit($text,$key);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            case '1900': //Untuk Decrypt 3 Des Credit
              $msg = $this->api_model->oracle_escape_string($post['msg']);
              $msg_info = explode('#',$msg);
              $text = $msg_info[0];
              $key = $msg_info[1];
              $data = $this->api_model->decrypt_3des_credit($text,$key);
              $this->response($data, REST_Controller::HTTP_OK); // 200 being the HTTP response code
              break;
            default:
              echo createlog(date("Y-m-d H:i:s")." - ".$LOG_CTR." - Code=0002 - Post Code not Match!"."\n");
              $this->response(array('code' => '0002','msg' => 'Post Code not Match'), REST_Controller::HTTP_NOT_FOUND);
              break;
          }
        }else {
          echo createlog(date("Y-m-d H:i:s")." - ".$LOG_CTR." - Code=0001 - Empty Post Code or Post Msg!"."\n");
          $this->response(array('code' => '0001','msg' => 'Empty Post Code or Post Msg'), REST_Controller::HTTP_NOT_FOUND);
        }
    }
}
