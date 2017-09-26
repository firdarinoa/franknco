<?php
    require ('PHPMailer/PHPMailerAutoload.php');

    function send_email($subject,$addAddress_email,$message_email){
		$username 		= 'wisely.interindo@gmail.com';
		$pass			    = '56968177z';
    //https://www.google.com/settings/security/lesssecureapps untuk setting ijin mengirim email dengan gmail

        $mail = new PHPMailer;
        $mail->isSMTP();                                      // Set mailer to use SMTP
        $mail->Host 	    = 'smtp.gmail.com';                 // Specify main and backup SMTP servers
        $mail->SMTPAuth   = true;                             // Enable SMTP authentication
        $mail->Username   = $username;        				        // SMTP username
        $mail->Password   = $pass;           				          // SMTP password
        $mail->SMTPSecure = 'tls';                            // Enable TLS encryption, `ssl` also accepted
        $mail->Port       = 587;                              // TCP port to connect to

	      $mail->SMTPOptions = array(
    			'ssl'                => array(
    			'verify_peer'        => false,
    			'verify_peer_name'   => false,
    			'allow_self_signed'  => true
    			)
    		);

		    $mail->setFrom($username, 'MaGhaButt Team');
        $mail->addAddress($addAddress_email);                 // Name is optional
        $mail->isHTML(true);                                  // Set email format to HTML

        $mail->Subject 			  = $subject;
        $mail->Body    			  = $message_email;
		    $mail->Timeout       	= 8; // set the timeout (seconds)
		    $mail->SMTPKeepAlive 	= true; // don't close the connection between messages

        if(!$mail->send()) {
          //echo 'Message could not be sent.';
          //echo 'Mailer Error: ' . $mail->ErrorInfo;
    			$mail->SmtpClose(); // close the connection since it was left open.
    			return true;
        } else {
    			//echo 'Message has been sent';
    			$mail->SmtpClose(); // close the connection since it was left open.
    			return true;
        }
    }
?>
