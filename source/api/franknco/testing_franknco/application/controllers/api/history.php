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
class history extends REST_Controller {
    function __construct()
    {
        // Construct the parent class
        parent::__construct();
        $this->load->helper('cURL_helper');
        $this->load->helper('log_helper');
        $this->load->model('api_model');
    }
    public function catch_post()
    {
      $post = $this->input->post();
      $log_ctr = 'Get Transaction history -';
      if(!empty($post))
      {
        echo createlog(date("Y-m-d H:i:s")." init history data ".serialize($post)." "."\n");
        $version = $this->input->post('oauth_version');
        if($version==VER)
        {
          echo createlog(date("Y-m-d H:i:s")." ".$log_ctr." Version compare success "."\n");
          $dongle = $this->input->post('oauth_dongle_id');
          $uid = $this->input->post('oauth_user_id');
          $data = $this->api_model->get_transaction($uid,$dongle,$version);
          if($data)
          {
            echo createlog(date("Y-m-d H:i:s")." ".$log_ctr." success!! Connection to database "."\n");
            $this->response($data); // 200 being the HTTP response code
          }
          else{
              echo createlog(date("Y-m-d H:i:s")." ".$log_ctr." 63 status error null data "."\n");
              $this->response(array('oauth_response' => '63','oauth_data' => 'Error!! Something wrong!','oauth_version' => $version), 404);
          }
        }
        else {
          echo createlog(date("Y-m-d H:i:s")." ".$log_ctr." 70 error version compare "."\n");
          $this->response(array('oauth_response' => '70','oauth_data' => 'Error!! Please Contact Us','oauth_version' => $version), 404);
        }
      }
      else {
        echo createlog(date("Y-m-d H:i:s")." ".$log_ctr." 71 error empty post and connection "."\n");
        $this->response(array('oauth_response' => '71','oauth_data' => 'Error Connection','oauth_version' => $version), 404);
      }
    }
}
