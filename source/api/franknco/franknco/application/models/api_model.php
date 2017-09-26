<?php
  class api_model extends CI_Model
  {
    public function __construct()
    {
      $this->load->database();
      $this->load->helper('log_helper');
      date_default_timezone_set("Asia/Jakarta");
      define('KEY3DES','12345678uaop');
    }

    //FUNCTION FOR LOGIN
    public function login($user,$password){
      // Start Data Decrpyt
      $user_decrypt = $this->decrypt_3des($user);
      $password_decrypt = $this->decrypt_3des($password);
      $hit_data = 'User:'.serialize($user_decrypt).'Password:'.serialize($password_decrypt);
      // End Data Decrypt

      $action = "LOGIN";
      $string_log = 'Init Hit Data '.$hit_data;
      $insert_log = $this->logging($user_decrypt,$action,$string_log);
      echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
      $cheking_user = $this->checking_user($user,$password);
      if($cheking_user=='1'){
        //cek login attemp
        $login_attemp = $this->get_login_attemp($user);
        $login_attemp = $this->decrypt_3des($login_attemp);
        if($login_attemp > 7){//attemp limit 7
          $blocked_time = $this->get_blocked_time($user);
          $duration = round(abs(time() - strtotime($blocked_time))/60,0);
          if($duration < 10){//blocked time still running 10 minutes
            $string_log = 'Login Failed! Code=0130 - Please Try Again in '.(10-$duration).' minutes!';
            $insert_log = $this->logging($user_decrypt,$action,$string_log);
            echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
            return array('code' => '0130', 'msg' => 'Please Try Again in '.(10-$duration).' minutes');
          }else{
            $login_attemp_decrypt = 0;
            $login_attemp_encrypt = $this->encrypt_3des($login_attemp_decrypt);
            $this->set_login_attemp($user,$login_attemp_encrypt);
            $this->delete_blocked_time($user);
          }
        }
        $token = $this->generate_token($user);
        $string_log = 'Login Success! Code=0110 - User and Password Match!';
        $insert_log = $this->logging($user_decrypt,$action,$string_log);
        echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
        return array('code' => '0110', 'msg' => $token);
      }else{
        $checking_signup = $this->checking_signup($user);
        if($checking_signup=='1'){
          //update login_attemp
          $login_attemp = $this->get_login_attemp($user);
          $login_attemp_decrypt = $this->decrypt_3des($login_attemp);
          if($login_attemp_decrypt <= 7){//attemp limit 7
            $login_attemp_decrypt += 1;
            $login_attemp_encrypt = $this->encrypt_3des($login_attemp_decrypt);
            $this->set_login_attemp($user,$login_attemp_encrypt);
          }
          if($login_attemp_decrypt > 7){//over attemp limit 7
            $this->set_blocked_time($user);
            $string_log = 'Login Failed! Code=0130 - Please Try Again in 10 minutes!';
            $insert_log = $this->logging($user_decrypt,$action,$string_log);
            echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
            return array('code' => '0130', 'msg' => 'Please Try Again in 10 minutes');
          }
        }
        $string_log = 'Login Failed! Code=0120 - User and Password not Match!';
        $insert_log = $this->logging($user_decrypt,$action,$string_log);
        echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
        return array('code' => '0120', 'msg' => 'User and Password not Match');
      }
    }

    //FUNCTION FOR SIGN UP
    public function signup($card_number,$fullname,$dob,$address,$email,$hp,$password){
      // Start Data Decrpyt
      $card_number_decrypt = $this->decrypt_3des($card_number);
      $fullname_decrypt = $this->decrypt_3des($fullname);
      $address_decrypt = $this->decrypt_3des($address);
      $email_decrypt = $this->decrypt_3des($email);
      $user_decrypt = $this->decrypt_3des($hp);
      $password_decrypt = $this->decrypt_3des($password);
      $hit_data = 'User:'.serialize($user_decrypt).'Password:'.serialize($password_decrypt).'Card Number:'.serialize($card_number_decrypt).
                  'Fullname:'.serialize($fullname_decrypt).'DOB:'.serialize($dob).
                  'Address:'.serialize($address_decrypt).'Email:'.serialize($email_decrypt);
      // End Data Decrypt

      $action = "SIGN UP";
      $string_log = 'Init Hit Data '.$hit_data;
      $insert_log = $this->logging($user_decrypt,$action,$string_log);
      echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
      $checking_signup = $this->checking_signup($hp);
      if($checking_signup=='0'){
        $checking_card = $this->checking_card($card_number);
        if($checking_card=='1'){//Means card already registered to database and not linked to any user yet
          $signup = $this->set_signup($fullname,$dob,$address,$email,$hp,$password);
          if($signup=='1'){
            $register_card = $this->set_register_card($hp,$card_number);
            $string_log = 'Sign Up Success! Code=0210 - Sign Up Success!';
            $insert_log = $this->logging($user_decrypt,$action,$string_log);
            echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
            return array('code' => '0210', 'msg' => 'Sign Up Success');
          }else{
            $string_log = 'Sign Up Failed! Code=0220 - Error When Inserting to Database!';
            $insert_log = $this->logging($user_decrypt,$action,$string_log);
            echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
            return array('code' => '0220', 'msg' => 'Error When Inserting to Database');
          }
        }else{//Card not registered to database or already linked to other user
          $string_log = 'Sign Up Failed! Code=0240 - Card not Registered to Database!';
          $insert_log = $this->logging($user_decrypt,$action,$string_log);
          echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
          return array('code' => '0240', 'msg' => 'Card not Registered to Database');
        }
      }else{
        $string_log = 'Sign Up Failed! Code=0230 - User Already Exist!';
        $insert_log = $this->logging($user_decrypt,$action,$string_log);
        echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
        return array('code' => '0230', 'msg' => 'User Already Exist');
      }
    }

    //FUNCTION FOR FORGOT PASSWORD
    public function forgot_password($user){
      // Start Data Decrpyt
      $user_decrypt = $this->decrypt_3des($user);
      $hit_data = 'User:'.serialize($user_decrypt);
      // End Data Decrypt

      $action = "FORGOT PASSWORD";
      $string_log = 'Init Hit Data '.$hit_data;
      $insert_log = $this->logging($user_decrypt,$action,$string_log);
      echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
      $checking_signup = $this->checking_signup($user);
      if($checking_signup=='1'){
        //Forgot Password belum selesai
        $forgot_pass = $this->set_forgot_password($user);
        $string_log = 'Forgot Password Success! Code=0310 - Change Password Success!';
        $insert_log = $this->logging($user_decrypt,$action,$string_log);
        echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
        return array('code' => '0310', 'msg' => $forgot_pass);
      }else{
        $string_log = 'Forgot Password Failed! Code=0320 - User not Exist!';
        $insert_log = $this->logging($user_decrypt,$action,$string_log);
        echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
        return array('code' => '0320', 'msg' => 'User not Exist');
      }
    }

    //FUNCTION FOR GET CHANGE PASSWORD
    public function change_password($user,$password,$new_password){
      // Start Data Decrpyt
      $user_decrypt = $this->decrypt_3des($user);
      $password_decrypt = $this->decrypt_3des($password);
      $new_password_decrypt = $this->decrypt_3des($new_password);
      $hit_data = 'User:'.serialize($user_decrypt).'Password:'.serialize($password_decrypt).'New Password:'.serialize($new_password_decrypt);
      // End Data Decrypt

      $action = "CHANGE PASSWORD";
      $string_log = 'Init Hit Data '.$hit_data;
      $insert_log = $this->logging($user_decrypt,$action,$string_log);
      echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
      $checking_signup = $this->checking_signup($user);
      if($checking_signup=='1'){
        $checking_user = $this->checking_user($user,$password);
        if($checking_user=='1'){
          //Change Password
          $change_pass = $this->set_change_password($user,$password,$new_password);
          $string_log = 'Change Password Success! Code=0410 - Change Password Success!';
          $insert_log = $this->logging($user_decrypt,$action,$string_log);
          echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
          return array('code' => '0410', 'msg' => 'Change Password Success');
        }else{
          $string_log = 'Change Password Failed! Code=0420 - User and Password not Match!';
          $insert_log = $this->logging($user_decrypt,$action,$string_log);
          echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
          return array('code' => '0420', 'msg' => 'User and Password not Match');
        }
      }else{
        $string_log = 'Change Password Failed! Code=0430 - User not Exist!';
        $insert_log = $this->logging($user_decrypt,$action,$string_log);
        echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
        return array('code' => '0430', 'msg' => 'User not Exist');
      }
    }

    //FUNCTION FOR GET USER KEY
    public function card_key($user,$card_number){
      // Start Data Decrpyt
      $user_decrypt = $this->decrypt_3des($user);
      $card_number_decrypt = $this->decrypt_3des($card_number);
      $hit_data = 'User:'.serialize($user_decrypt).'Card Number:'.serialize($card_number_decrypt);
      // End Data Decrypt

      $action = "CARD KEY";
      $string_log = 'Init Hit Data '.$hit_data;
      $insert_log = $this->logging($user_decrypt,$action,$string_log);
      echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
      $checking_signup = $this->checking_signup($user);
      if($checking_signup=='1'){
        $card_key = $this->get_card_key($user,$card_number);
        if(!empty($card_key)){
          $string_log = 'Card Key Success! Code=0510 - Get Card Key Success!';
          $insert_log = $this->logging($user_decrypt,$action,$string_log);
          echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
          return array('code' => '0510', 'msg' => $card_key);
        }else{
          $string_log = 'Card Key Failed! Code=0520 - Card not Registered to User!';
          $insert_log = $this->logging($user_decrypt,$action,$string_log);
          echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
          return array('code' => '0520', 'msg' => 'Card not Registered to User');
        }
      }else{
        $string_log = 'Card Key Failed! Code=0530 - User not Exist!';
        $insert_log = $this->logging($user_decrypt,$action,$string_log);
        echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
        return array('code' => '0530', 'msg' => 'User not Exist');
      }
    }

    //FUNCTION FOR GET CARD CREDIT
    public function card_credit($user,$card_number){
      // Start Data Decrpyt
      $user_decrypt = $this->decrypt_3des($user);
      $card_number_decrypt = $this->decrypt_3des($card_number);
      $hit_data = 'User:'.serialize($user_decrypt).'Card Number:'.serialize($card_number_decrypt);
      // End Data Decrypt

      $action = "CARD CREDIT";
      $string_log = 'Init Hit Data '.$hit_data;
      $insert_log = $this->logging($user_decrypt,$action,$string_log);
      echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
      $checking_signup = $this->checking_signup($user);
      if($checking_signup=='1'){
        $card_credit = $this->get_card_credit($user,$card_number);
        if(!empty($card_credit)){
          $string_log = 'Card Credit Success! Code=0610 - Get Card Credit Success!';
          $insert_log = $this->logging($user_decrypt,$action,$string_log);
          echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
          return array('code' => '0610', 'msg' => $card_credit);
        }else{
          $string_log = 'Card Credit Failed! Code=0620 - Card not Registered to User!';
          $insert_log = $this->logging($user_decrypt,$action,$string_log);
          echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
          return array('code' => '0620', 'msg' => 'Card not Registered to User');
        }
      }else{
        $string_log = 'User Credit Failed! Code=0630 - User not Exist!';
        $insert_log = $this->logging($user_decrypt,$action,$string_log);
        echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
        return array('code' => '0630', 'msg' => 'User not Exist');
      }
    }

    //FUNCTION FOR GET USER CARD LIST
    public function card_list($user){
      // Start Data Decrpyt
      $user_decrypt = $this->decrypt_3des($user);
      $hit_data = 'User:'.serialize($user_decrypt);
      // End Data Decrypt

      $action = "CARD LIST";
      $string_log = 'Init Hit Data '.$hit_data;
      $insert_log = $this->logging($user_decrypt,$action,$string_log);
      echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
      $checking_signup = $this->checking_signup($user);
      if($checking_signup=='1'){
        $card = $this->get_card_list($user);
        if(!empty($card)){
          $string_log = 'Card List Success! Code=0710 - Get Card List Success!';
          $insert_log = $this->logging($user_decrypt,$action,$string_log);
          echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
          return array('code' => '0710', 'msg' => $card);
        }else{
          $string_log = 'Card List Failed! Code=0720 - No Card Found!';
          $insert_log = $this->logging($user_decrypt,$action,$string_log);
          echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
          return array('code' => '0720', 'msg' => 'No Card Found');
        }
      }else{
        $string_log = 'Card List Failed! Code=0730 - User not Exist!';
        $insert_log = $this->logging($user_decrypt,$action,$string_log);
        echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
        return array('code' => '0730', 'msg' => 'User not Exist');
      }
    }

    //FUNCTION FOR GET CARD TRANSACTION LIST
    public function card_transaction($user,$card_number,$limit){
      // Start Data Decrpyt
      $user_decrypt = $this->decrypt_3des($user);
      $card_number_decrypt = $this->decrypt_3des($card_number);
      $limit_decrypt = $this->decrypt_3des($limit);
      $hit_data = 'User:'.serialize($user_decrypt).'Card Number:'.serialize($card_number_decrypt).
                  'Limit:'.serialize($limit_decrypt);
      // End Data Decrypt

      $action = "CARD TRANSACTION";
      $string_log = 'Init Hit Data '.$hit_data;
      $insert_log = $this->logging($user_decrypt,$action,$string_log);
      echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
      $checking_signup = $this->checking_signup($user);
      if($checking_signup=='1'){
        $card_transaction = $this->get_card_transaction($user,$card_number,$limit_decrypt);
        if(!empty($card_transaction)){
          $string_log = 'Card Transaction Success! Code=0810 - Get Card Transaction Success!';
          $insert_log = $this->logging($user_decrypt,$action,$string_log);
          echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
          return array('code' => '0810', 'msg' => $card_transaction);
        }else{
          $string_log = 'Card Transaction Failed! Code=0820 - No Transaction Found!';
          $insert_log = $this->logging($user_decrypt,$action,$string_log);
          echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
          return array('code' => '0820', 'msg' => 'No Transaction Found');
        }
      }else{
        $string_log = 'Card Transaction! Code=0830 - User not Exist!';
        $insert_log = $this->logging($user_decrypt,$action,$string_log);
        echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
        return array('code' => '0830', 'msg' => 'User not Exist');
      }
    }

    //FUNCTION FOR REGISTER CARD
    public function register_card($user,$card_number){
      // Start Data Decrpyt
      $user_decrypt = $this->decrypt_3des($user);
      $card_number_decrypt = $this->decrypt_3des($card_number);

      $hit_data = 'User:'.serialize($user_decrypt).'Card Number:'.serialize($card_number_decrypt);
      // End Data Decrypt

      $action = "CARD REGISTER";
      $string_log = 'Init Hit Data '.$hit_data;
      $insert_log = $this->logging($user_decrypt,$action,$string_log);
      echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
      $checking_signup = $this->checking_signup($user);
      if($checking_signup=='1'){
        $checking_card = $this->checking_card($card_number);
        if($checking_card=='1'){//Means card already registered to database and not linked to any user yet
          $register_card = $this->set_register_card($user,$card_number);
          if($register_card=='1'){
            $string_log = 'Card Register Success! Code=0910 - Card Register Success!';
            $insert_log = $this->logging($user_decrypt,$action,$string_log);
            echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
            return array('code' => '0910', 'msg' => 'Card Register Success');
          }else{
            $string_log = 'Card Register Failed! Code=0920 - Error When Inseting to Database!';
            $insert_log = $this->logging($user_decrypt,$action,$string_log);
            echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
            return array('code' => '0920', 'msg' => 'Error When Inseting to Database');
          }
        }else{
          $string_log = 'Card Register! Code=0930 - User not Exist!';
          $insert_log = $this->logging($user_decrypt,$action,$string_log);
          echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
          return array('code' => '0930', 'msg' => 'User not Exist');
        }
      }else{//Card not registered to database or already linked to other user
        $string_log = 'Card Register! Code=0940 - Card not Registered to Database!';
        $insert_log = $this->logging($user_decrypt,$action,$string_log);
        echo createlog(date("Y-m-d H:i:s")." - ".$user_decrypt." - ".$action." - ".$string_log."\n");
        return array('code' => '0940', 'msg' => 'Card not Registered to Database');
      }
    }

    // //FUNCTION FOR SEND EMAIL
    // public function send_email($trace_number,$email,$hit_data){
    //   $action = "SEND EMAIL";
    //   $string_log = 'Init Hit Data '.$hit_data;
    //   $insert_log = $this->logging($email,$action,$string_log);
    //   echo createlog(date("Y-m-d H:i:s")." - ".$email." - ".$action." - ".$string_log."\n");
    //   $checking_user = $this->checking_user($email);
    //   if($checking_user=='1'){
    //     $userid = $this->get_userid($email);
    //     $get_trans = $this->get_trans($trace_number,$userid);
    //     if(empty($get_trans)){
    //       $string_log = 'Try Sending Email! Code=0540 - Send Email Failed, Transaction not Found!';
    //       $insert_log = $this->api_model->logging($email,$action,$string_log);
    //       echo createlog(date("Y-m-d H:i:s")." - ".$email." - ".$action." - ".$string_log."\n");
    //       return array('code' => '0540', 'msg' => 'Send Email Failed, Transaction not Found');
    //     }else{
    //       $template_email = $this->email_template($get_trans['trace_number'],$get_trans['user_id'],
    //                                               $get_trans['transaction_type'],$get_trans['amount'],$get_trans['status'],
    //                                               $get_trans['transaction_date'],$get_trans['note']);
    //       // echo $template_email;
    //
    //       //SET SEND EMAIL
    //       include ('function/function.sendemail.php');
    //       $subject = 'MaGhaButt';
    //       $addAddress_email = $email;
    //       $send_email = send_email($subject,$addAddress_email,$template_email);
    //       // $this->email->attach($data_print['path_struct'],"inline");
    //       if($send_email!='1')
    //       {
    //         $string_log = 'Try Sending Email! Code=0520 - Send Email Failed!';
    //         $insert_log = $this->api_model->logging($email,$action,$string_log);
    //         echo createlog(date("Y-m-d H:i:s")." - ".$email." - ".$action." - ".$string_log."\n");
    //         // echo $this->email->print_debugger();
    //         return array('code' => '0520', 'msg' => 'Send Email Failed');
    //       }else{
    //         $string_log = 'Try Sending Email! Code=0510 - Send Email Success!';
    //         $insert_log = $this->api_model->logging($email,$action,$string_log);
    //         echo createlog(date("Y-m-d H:i:s")." - ".$email." - ".$action." - ".$string_log."\n");
    //         return array('code' => '0510', 'msg' => 'Send Email Success');
    //       }
    //     }
    //   }else{
    //     $string_log = 'Try Sending Email! Code=0530 - User not Exist!';
    //     $insert_log = $this->logging($email,$action,$string_log);
    //     echo createlog(date("Y-m-d H:i:s")." - ".$email." - ".$action." - ".$string_log."\n");
    //     return array('code' => '0530', 'msg' => 'User not Exist');
    //   }
    // }

    public function checking_token($user,$token){
      $query_check_token = "select token,passwd from user_member where hp='".$user."' and token='".$token."'";
      $exec_check_token = $this->db->query($query_check_token);
      $result_check_token = $exec_check_token->result_array();
      $token = $result_check_token[0]['token'];
      if($token!=Array()){
        $data = 'EXIST';
        $token_decrypt = $this->decrypt_3des($token);
        $msg = explode('#_-#', $token_decrypt);
        $seconds = time() - strtotime($msg[2]);
        // $days = floor($seconds / 86400);
        $seconds %= 86400;
        // $hours = floor($seconds / 3600);
        $seconds %= 3600;
        $minutes = floor($seconds / 60);
        // $seconds %= 60;
        if($minutes >= 15){
           $data = 'TIMEOUT';
        }
      }else{
        $data = 'NOTEXIST';
      }
      return $data;
    }

    private function checking_user($user,$password){
      $query_user_login = "select count(*) as jumlah from user_member where hp='".$user."' and passwd='".$password."'";
      $exec_user_login = $this->db->query($query_user_login);
      $result_user_login = $exec_user_login->result_array();
      return $result_user_login[0]['jumlah'];
    }

    private function checking_card($card_number){
      $query_check_card = "select count(*) as jumlah from card where card_number='".$card_number."' and (umid is null or umid='')";
      $exec_check_card = $this->db->query($query_check_card);
      $result_check_card = $exec_check_card->result_array();
      return $result_check_card[0]['jumlah'];
    }

    private function set_change_password($user,$password,$new_password){
      $query_set_password = "update user_member set passwd='".$new_password."' where hp='".$user."' and password='".$password."'";
      $exec_set_password = $this->db->query($query_set_password);
      return $exec_set_password;
    }

    private function set_forgot_password($user){
      $query_set_password = "select passwd from user_member where hp='".$user."'";
      $exec_set_password = $this->db->query($query_set_password);
      $result_set_password = $exec_set_password->result_array();
      return $result_set_password[0]['passwd'];
    }

    private function set_login_attemp($user,$attemp){
      $query_set_attemp = "update user_member set login_attemp='".$attemp."' where hp='".$user."'";
      $exec_set_attemp = $this->db->query($query_set_attemp);
      return $exec_set_attemp;
    }

    private function get_login_attemp($user){
      $query_get_attemp = "select login_attemp from user_member where hp='".$user."'";
      $exec_get_attemp = $this->db->query($query_get_attemp);
      $result_get_attemp = $exec_get_attemp->result_array();
      return $result_get_attemp[0]['login_attemp'];
    }

    private function set_blocked_time($user){
      $query_set_blocked = "update user_member set blocked_time=now() where hp='".$user."'";
      $exec_set_blocked = $this->db->query($query_set_blocked);
      return $exec_set_blocked;
    }

    private function delete_blocked_time($user){
      $query_set_blocked = "update user_member set blocked_time=null where hp='".$user."'";
      $exec_set_blocked = $this->db->query($query_set_blocked);
      return $exec_set_blocked;
    }

    private function get_blocked_time($user){
      $query_get_blocked = "select blocked_time from user_member where hp='".$user."'";
      $exec_get_blocked = $this->db->query($query_get_blocked);
      $result_get_blocked = $exec_get_blocked->result_array();
      return $result_get_blocked[0]['blocked_time'];
    }

    private function checking_signup($user){
      $query_check_signup = "select count(*) as jumlah from user_member where hp='".$user."'";
      $exec_check_signup = $this->db->query($query_check_signup);
      $result_check_signup = $exec_check_signup->result_array();
      return $result_check_signup[0]['jumlah'];
    }

    private function set_signup($fullname,$dob,$address,$email,$hp,$password){
      $login_attemp = $this->encrypt_3des(0);
      $query_set_signup = "insert into user_member(fullname,dob,address,email,hp,passwd,login_attemp)
                           values('".$fullname."','".$dob."','".$address."','".$email."','".$hp."','".$password."','".$login_attemp."')";
      $exec_set_signup = $this->db->query($query_set_signup);
      return $exec_set_signup;
    }

    private function set_register_card($user,$card_number){
      $query_set_register = "update card set umid=(select umid from user_member where hp='".$user."'), activation_date=now() where card_number='".$card_number."'";
      $exec_set_register = $this->db->query($query_set_register);
      return $exec_set_register;
    }

    private function set_token($user,$token){
      $query_user_token = "update user_member set token='".$token."' where hp='".$user."'";
      $exec_user_token = $this->db->query($query_user_token);
      return $exec_user_token;
    }

    private function generate_token($user){
       $query_get_password = "select passwd from user_member where hp='".$user."'";
       $exec_get_password = $this->db->query($query_get_password);
       $result_get_password = $exec_get_password->result_array();
       $user_decrypt = $this->decrypt_3des($user);
       $password_decrypt = $this->decrypt_3des($result_get_password[0]['passwd']);
       $token = $user_decrypt.'#_-#'.$password_decrypt.'#_-#'.date("Y-m-d h:i:s");
       $data = array(
         'user'    => $user,
         'token'   => $this->encrypt_3des($token),
       );
       $this->set_token($data['user'],$data['token']);
       return $data;
    }

    private function get_card_key($user,$card_number){
      $query_get_card_key = "select card_key from card where umid=(select umid from user_member where hp='".$user."') and card_number='".$card_number."'";
      $exec_get_card_key = $this->db->query($query_get_card_key);
      $result_get_card_key = $exec_get_card_key->result_array();
      if(!empty($result_get_card_key)){
        $data = array(
          'user'        => $user,
          'card_key'    => $result_get_card_key[0]['card_key'],
        );
      }else {
        $data = '';
      }
      return $data;
    }

    private function get_card_credit($user,$card_number){
      $query_get_card_credit = "select x175 from card where umid=(select umid from user_member where hp='".$user."') and card_number='".$card_number."'";
      $exec_get_card_credit = $this->db->query($query_get_card_credit);
      $result_get_card_credit = $exec_get_card_credit->result_array();
      if(!empty($result_get_card_credit)){
        $data = array(
          'user'        => $user,
          'x175'        => $result_get_card_credit[0]['x175'],
        );
      }else{
        $data = '';
      }
      return $data;
    }

    private function get_card_list($user){
      $query_get_card_list = "select card_id,card_number,x175,card_key,valid_until from card where umid=(select umid from user_member where hp='".$user."') order by cdid";
      $exec_get_card_list = $this->db->query($query_get_card_list);
      $result_get_card_list = $exec_get_card_list->result_array();
      if(!empty($result_get_card_list)){
        for($i=0; $i<count($result_get_card_list); $i++){
          $data[] = array(
            'user'        => $user,
            'card_id'     => $result_get_card_list[$i]['card_id'],
            'card_number' => $result_get_card_list[$i]['card_number'],
            'x175'        => $result_get_card_list[$i]['x175'],
            'card_key'    => $result_get_card_list[$i]['card_key'],
            'valid_until' => $result_get_card_list[$i]['valid_until'],
          );
  			}
      }else{
        $data = '';
      }
      return $data;
    }

    private function get_card_transaction($user,$card_number,$limit){
      $query_get_card_transaction = "select b.name,a.x761,a.trans_date from transaction a
      inner join transaction_type b on a.ttid=b.ttid
      where card_id=(select card_id from card where card_number='".$card_number."')
      order by a.trans_date desc
      limit $limit";
      $exec_get_card_transaction = $this->db->query($query_get_card_transaction);
      $result_get_card_transaction = $exec_get_card_transaction->result_array();
      if(!empty($result_get_card_transaction)){
        for($i=0; $i<count($result_get_card_transaction); $i++){
          $data[] = array(
            'card_number'        => $card_number,
            'transaction_type'   => $result_get_card_transaction[$i]['name'],
            'x761'               => $result_get_card_transaction[$i]['x761'],
            'trans_date'         => $result_get_card_transaction[$i]['trans_date'],
          );
  			}
      }else{
        $data = '';
      }
      return $data;
    }

    // //FUNCTION FOR GET TEMPLATE FOR SEND EMAIL
    // private function email_template($trace_number,$userid,$transaction_type,$amount,$status,$transaction_date,$note){
    //   $html="
    //   <html>
    //   <head>
    //     <style>
    //     hr {
    //         display: block;
    //         margin-top: 0.5em;
    //         margin-bottom: 0.5em;
    //         margin-left: auto;
    //         margin-right: auto;
    //         border-style: dashed;
    //         border-width: 1px;
    //     }
    //     </style>
    //   </head>
    //     <body>
    //     <div style='width:30%;'>
    //       <table width='100%' border=0>
    //           <tr>
    //             <td>
    //               <hr>
    //             </td>
    //           </tr>
    //           <tr>
    //             <td style='text-align: center;'>
    //                 <b>".strtoupper('IN MAGHABUTT WE TRUST')."</b>
    //             </td>
    //           </tr>
    //           <tr>
    //             <td>
    //               <hr>
    //             </td>
    //           </tr>
    //           <tr>
    //             <td>
    //               Trace Number: $trace_number
    //             </td>
    //           </tr>
    //           <tr>
    //             <td>
    //               User ID: $userid
    //             </td>
    //           </tr>
    //           <tr>
    //             <td>
    //               Transaction Type: $transaction_type
    //             </td>
    //           </tr>
    //           <tr>
    //             <td>
    //               Amount: $amount
    //             </td>
    //           </tr>
    //           <tr>
    //             <td>
    //               Status: $status
    //             </td>
    //           </tr>
    //           <tr>
    //             <td>
    //               Transaction Date: $transaction_date
    //             </td>
    //           </tr>
    //       </table>
    //     </div>
    //   </body>
    //   </html>";
    //   return $html;
    // }

    //FUNCTION FOR ENRYPT 3DES MASTER
    public function encrypt_3des($text) {
			$iv 	= '00000000';

			$cipher = mcrypt_module_open(MCRYPT_TRIPLEDES,'',MCRYPT_MODE_CBC,'');
			mcrypt_generic_init($cipher, KEY3DES, $iv);
			$decrypted = mcrypt_generic($cipher,$text);
			mcrypt_generic_deinit($cipher);
			return strtoupper(bin2hex(base64_encode($decrypted)));
		}

    //FUNCTION FOR DECRYPT 3DES MASTER
		public function decrypt_3des($encrypted_text){
			$iv 	= '00000000';

			$td = mcrypt_module_open (MCRYPT_TRIPLEDES, '', MCRYPT_MODE_CBC, '');
	    mcrypt_generic_init ($td, KEY3DES, $iv);
	    $decryptedString = mdecrypt_generic ($td, base64_decode(hex2bin($encrypted_text)));
	    return trim($decryptedString);
		}

    //FUNCTION FOR ENRYPT 3DES USER CREDIT
    public function encrypt_3des_credit($text,$key) {
      $iv 	= '00000000';

      $cipher = mcrypt_module_open(MCRYPT_TRIPLEDES,'',MCRYPT_MODE_CBC,'');
      mcrypt_generic_init($cipher, $key, $iv);
      $decrypted = mcrypt_generic($cipher,$text);
      mcrypt_generic_deinit($cipher);
      return strtoupper(bin2hex(base64_encode($decrypted)));
    }

    //FUNCTION FOR DECRYPT 3DES USER CREDIT
    public function decrypt_3des_credit($encrypted_text,$key){
      $iv 	= '00000000';

      $td = mcrypt_module_open (MCRYPT_TRIPLEDES, '', MCRYPT_MODE_CBC, '');
      mcrypt_generic_init ($td, $key, $iv);
      $decryptedString = mdecrypt_generic ($td, base64_decode(hex2bin($encrypted_text)));
      return trim($decryptedString);
    }

    //FUNCTION FOR LOGGING
    public function logging($user,$action,$data){
      $query_insert_logging = "insert into logging_api(user,action,data,last_log_timestamp) values ('".$user."','".$action."','".$data."',now())";
      $exec_insert_logging = $this->db->query($query_insert_logging);
    }

    public function oracle_escape_string($str)
    {
      $data = str_replace(array('<','>','"',"'",'(',')',';'),array('','','','','','',''), $str);
      return htmlspecialchars($data);
    }
}
