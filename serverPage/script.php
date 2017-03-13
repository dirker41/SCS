<?php
// create random script
#session_start();   
#echo session_id();
 
// client check.... ex: user-agent
 
 
include "RandomTable.php";
 
$script1 = new RandomTable() ;
 
 
#var_dump($script1);
 
 
for($i=0;$i<count($script1->mSCSScript);$i++){
  echo $script1->mSCSScript[$i]."\n"."";
  # if ( strpos($_SERVER ['HTTP_USER_AGENT'], "wget" ) ) echo "<BR>";
}
 
/*
$postStrBeg="\"" ;
$postStrEnd="\"" ;
$postContent="XFort=1&macaddress=SCSscriptTest&HDD=gfdgnts=100&DG=Y&AssetID=TW1000325555&";
$postContent=$postContent."Rand=".substr($script1->mSCSScript[2],5)."&";
$postContent=$postContent."SUM=".substr($script1->mSCSScript[4],5)."&";
 
$postStr=$postStrBeg.$postContent.$postStrEnd ;
 
 
echo "curl -d ".$postStr." 10.10.60.181/aitac2/SCS/"
*/
 
// echo mt_rand() ;
 
 
 
?>