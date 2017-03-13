<html>
<head>
  <title>SCS DeBugger</title>
</head>
<body>
<?php
// check scs status
 
include "RandomTable.php";
 
echo RandomTable::$my_static ;
 
 
 
$checkItems=array("date_time","hostname","site","securitystatus","antivirusstatus",
 
                  "Antivirusversion","definition","USB","hotfix","CPUModel",
                       
                              "logondomain","logonuser","IP","macaddress","OSversion",
                       
                              "servicepack","computertype","manufacturer","model","PCuser",   
                         
                              "pcdomain","HDD","HDDcounts","RAM","AssetID",
                       
                              "DG","admin","networkadaptercounts","XFort",
                  "COL1","COL2","COL3"                                
                        ) ;
 
                       
foreach ($checkItems as $value) {
    echo $value."=" .$_POST[$value]."<BR>\r\n";
}
 
/*                    
for ($i = 0; $i <= 29; $i++) {
    echo checkItems[$i]."="$_POST[$checkItems[$i]]."\r\n";
}
*/
 
/*
str += "id="                      +"456789"                     + "&" ;
str += "date_time="               +get_date_time()              + "&" ;
str += "hostname="                +get_hostname()               + "&" ;
str += "site="                    +get_site()                   + "&" ;
str += "security status="         +get_security$status()        + "&" ;
str += "antivirus status="        +get_antivirus$status()       + "&" ;
str += "Antivirus version="       +get_antivirus$status()       + "&" ;
str += "definition="              +get_definition()             + "&" ;
str += "USB="                     +get_USB()                    + "&" ;
str += "hotfix="                  +get_hotfix()                 + "&" ;
str += "CPU Model="               +get_CPU$Model()              + "&" ;
//=====================================================================            
str += "logondomain="             +get_logondomain()            + "&" ;
str += "logonuser="               +get_logonuser()              + "&" ;
str += "IP="                      +get_IP()                     + "&" ;
str += "mac address="             +get_mac$address()            + "&" ;
str += "OS version="              +get_OS$Version               + "&" ;
str += "service pack="            +get_service$pack()           + "&" ;
str += "computer type="           +get_computer$type()          + "&" ;
str += "manufacturer="            +get_manufacturer()           + "&" ;
str += "model="                   +get_model()                  + "&" ;
str += "PC user="                 +get_PC$user()                + "&" ;
//=====================================================================
str += "pc domain="               +get_pc$domain()              + "&" ;
str += "HDD="                     +get_HDD()                    + "&" ;
str += "HDD counts="              +get_HDD$counts()             + "&" ;
str += "RAM="                     +get_RAM()                    + "&" ;
str += "Asset ID="                +get_ASSetID()                + "&" ;
str += "DG="                      +get_DG()                     + "&" ;
str += "admin="                   +get_admin()                  + "&" ;
str += "network adapter counts="  +get_networkAdapter$counts()  + "&" ;
str += "XFort="                   +get_XFort()                  + "&" ;
*/
 
// mkdir("/var/www/html/aitac2/SCS/SCSreport",0755,true);
 
$Y=date(Y);
$m=date(m);
$d=date(d);
$H=date(H);
$currentTime=date(Y).date(m).date(d).date(H) ;
 
// echo $currentTime ;
 
$tempServerAgent = str_replace( "SCS" , "" , $_SERVER['HTTP_USER_AGENT'] );
$tempServerAgent = str_replace( "/" , " " , $tempServerAgent );
 
 
$fileName = $_POST["macaddress"].",".$_POST["hostname"].",".$_POST["securitystatus"].",".$tempServerAgent;
 
$fileContent = "";
 
foreach ($checkItems as $value) {
    $fileContent = $fileContent.$_POST[$value].",";
}
 
 
 
$file = fopen("SCSreport/".$fileName,"w"); //¶}±ÒÀÉ®×
 
// echo "<BR>"."SCSreport/".$fileName."<BR>" ;
 
fwrite($file,$fileContent);
fclose($file);
 
echo "had Write" ;
echo mt_rand() ;
 
 
 
?>
 
 </body>
</html>