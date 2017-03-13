<?php
 
class RandomTable {
    public static $my_static = 'fo00000o';
 
                                                                       
        public $mSCSScript;
       
        function __construct() {
               
                $this->mSCSScript = array ( "#!/bin/sh",
                                        "echo Hellow!",
                                    );
      
           
            array_push($this->mSCSScript, "COL1=".mt_rand() );
            array_push($this->mSCSScript, "COL2=".strtotime("now") );
                $randA = (mt_rand()%10000) ;
                $randB = (mt_rand()%10000) ;
               
                array_push($this->mSCSScript, "COL3="."`expr ".$randA." + ".$randB."`" );
            
            array_push($this->mSCSScript, "curlCommand1=\"curl -d\"" );
            array_push($this->mSCSScript, "curlCommandURL=\"10.10.60.181/aitac2/SCS/\"" );
               
                array_push($this->mSCSScript, "curlCommandPost=\"\\\"\"" );
                array_push($this->mSCSScript, "curlCommandPost=\${curlCommandPost}\"garbage=0&\"" );
                array_push($this->mSCSScript, "curlCommandPost=\${curlCommandPost}\"COL1=\$COL1&\"" );
                array_push($this->mSCSScript, "curlCommandPost=\${curlCommandPost}\"COL2=\$COL2&\"" );
                array_push($this->mSCSScript, "curlCommandPost=\${curlCommandPost}\"COL3=\$COL3&\"" );
                array_push($this->mSCSScript, "curlCommandPost=\${curlCommandPost}\"macaddress=T1E2S3T4&\"" );
                array_push($this->mSCSScript, "curlCommandPost=\${curlCommandPost}\"\\\"\"" );
               
                array_push($this->mSCSScript, "result=\"\$curlCommand1 \${curlCommandPost} \$curlCommandURL\"" );
               
                array_push($this->mSCSScript, "excute=`\$result`" );
               
               
               
            #for($i=0;$i<4;$i++){
                #   echo $mSCSScript[$i]."\r\n<BR>";
                #}
          
           
            #var_dump($mSCSScript);
          
           
    }                                                              
 
}
?>