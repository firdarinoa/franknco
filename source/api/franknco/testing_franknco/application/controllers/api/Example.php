<?php defined('BASEPATH') OR exit('No direct script access allowed');

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
class Example extends REST_Controller {
    function __construct()
    {
        // Construct the parent class
        parent::__construct();
        $this->load->helper('cURL_helper');
        $this->load->model('api_model');
    }
    //UNTUK LOGIN
    public function login_post(){
  		$uid = $this->input->post('uid');
  		$password = $this->input->post('password');
  		$data=$this->api_model->login_post($uid,$password);
  		if($data)
          {
              $this->response($data); // 200 being the HTTP response code
          }
          else
          {
              $this->response(array('suc' => '0','user' => 'Login Failed'), 404);
          }
  	}
    //UNTUK LOGIN CUSTOMER
    public function logincustomer_post(){
  		$cid = $this->input->post('cid');
  		$pin = $this->input->post('pin');
  		$data=$this->api_model->logincustomer_post($cid,$pin);
  		if($data)
          {
              $this->response($data); // 200 being the HTTP response code
          }
          else
          {
              $this->response(array('err' => 'failed','msg' => 'Login Failed'), 404);
          }
  	}
    //UNTUK GET CUSTOMER WALLET
    public function customerwallet_post()
    {
      $cid = $this->input->post('cid');
      $data = $this->api_model->walletcustomer_post($cid);
      if($data)
          {
              $this->response($data); // 200 being the HTTP response code
          }
          else
          {
              $this->response(array('err' => 'failed','msg' => 'Not have a wallet'), 404);
          }
    }
    //UNTUK GET TRANSACTION HISTORY
    public function transactionhistory_post()
    {
      $cid = $this->input->post('cid');
      $data = $this->api_model->transactionhistory_post($cid);
      if($data)
          {
              $this->response($data); // 200 being the HTTP response code
          }
          else
          {
              $this->response(array('err' => 'failed','msg' => 'Not have a transaction'), 404);
          }
    }
    //UNTUK UPDATE PIN CUSTOMER
    public function customerupdate_post()
    {
      $cid = $this->input->post('cid');
      $pin = $this->input->post('pin');
      $new_pin = $this->input->post('new_pin');
      $data = $this->api_model->customerupdate_post($cid,$pin,$new_pin);
      if($data)
          {
              $this->response($data); // 200 being the HTTP response code
          }
          else
          {
              $this->response(array('err' => 'failed','msg' => 'ID and Pin not Match'), 404);
          }
    }
    //UNTUK GET ALL merchant and store
    public function get_ALL_get()
    {
      $data['merchant'] = $this->api_model->get_merchant();
      $data['store'] = $this->api_model->get_store();
      $this->response($data);
    }
}
