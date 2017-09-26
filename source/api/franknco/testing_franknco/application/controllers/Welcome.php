<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Welcome extends CI_Controller {

	function __construct(){
 		parent::__construct();
 		$this->load->helper('cURL_helper');
 	}

	public function index()
	{
		$this->load->helper('url');

		$this->load->view('welcome_message');
	}

	public function logincurl()
	{
		$post = $this->input->post();
		$url = 'http://staging.exceedlink.com/index.php/api/example/login/format/json';
		$data = cURL_POST($post,$url);

		print_r($data);exit;

	}

	public function pay_salecurl()
	{
		$post = $this->input->post();
		$url = 'http://staging.exceedlink.com/index.php/api/example/pay_sale/format/json';
		$data = cURL_POST($post,$url);

		print_r($data);exit;
	}

	public function pay_sale_edccurl()
	{
		$post = $this->input->post();
		$url = 'http://staging.exceedlink.com/index.php/api/example/pay_edc_mode_insert/format/json';
		$data = cURL_POST($post,$url);

		print_r($data);exit;
	}

	public function voidcurl()
	{
		$post = $this->input->post();
		$url = 'http://staging.exceedlink.com/index.php/api/example/set_void_tx/format/json';
		$data = cURL_POST($post,$url);

		print_r($data);exit;
	}

	public function cek_historicurl()
	{
		$post = $this->input->post();
		$url = 'http://staging.exceedlink.com/index.php/api/example/get_transaksi/format/json';
		$data = cURL_POST($post,$url);

		print_r($data);exit;
	}
}
