import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.Random;
 
 
 
class ConnectSetting {
        public static final String SERVERIP = "10.10.60.181" ;
        //public static final String serverIP = "127.0.0.1" ;
        public static final String SERVERPATH = "/aitac2/SCS/index.php" ;
        public static final int    SERVERPORT = 80 ;
        public static final int    SOURCEPORT = 5432 ;
        public static final String USERAGENT = "SCSver.20170103" ;
        public static final int    SCSITEMCOUNT = 29 ;
       
       
} //ConnectSetting
 
 
 
 
public class ClientThreadCode extends Thread
{
    private Socket m_socket;//
        private String mPostContent = "" ;
 
    public ClientThreadCode(String ip, int port){
        try
        {
            m_socket = new Socket(ip, port);
            //destination IP and port
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
   
    public ClientThreadCode(String ip, int port,String sip, String postContent) {
        try
        {
                        mPostContent = postContent ;
                       
                       
                       
                       
            m_socket = new Socket(InetAddress.getByName(ip), port,InetAddress.getByName(sip),ConnectSetting.SOURCEPORT);
            //destination IP and port, Source IP and port
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
   
    
    
 
    @Override
    public void run(){
        try
        {
            if (m_socket != null)//if connect succeed, go ahead
            {
 
               
                PrintStream writer = new PrintStream(m_socket.getOutputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
 
                writer.println(PostForm());
                writer.flush();
                               
                                for ( String str = reader.readLine() ; str != null ; ) {
                  System.out.println("Server:" + str );
                                  str = reader.readLine() ;
                                } // for
                               
                                reader.close();
                               
                m_socket.close();
 
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    } // run
 
////////////////////////////////////////////////////////////////////////////////
       
    public static void main(String[] argv) {   
       
            System.out.println("Start SCS");         
               
               
                // SCS scs = new SCS() ;
               
       
            while ( true ) {
             
                        SCS scs = new SCS() ;
                        for ( int i = 0 ; i < scs.getNetworkadaptercounts() ; i ++ ) {
                               
                               
                new ClientThreadCode(ConnectSetting.SERVERIP,ConnectSetting.SERVERPORT,scs.getInterface(i),scs.toPostFormat(i) ).start();               
                                               
                //creat Object
                       
                        }
                        try {
                Thread.sleep(50000); // sleep 5S
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // catch
                       
                       
        } // while
               
                //System.out.println("END SCS");      
    }
       
////////////////////////////////////////////////////////////////////////////////////
       
       
 
       
       
        private String PostForm() {
               
                // SCS scs = new SCS();
               
                String str ="";
               
                str +=
                // "POST /aitac2/SCS/index.php HTTP/1.1\r\n" +
                        "POST " + ConnectSetting.SERVERPATH + " HTTP/1.1\r\n" +
                "Host: "+ SCS.get_hostname() +"\r\n" +
                                //"Host: "+ "www.yayya.c8c8.com.tw" +"\r\n" +
                "User-Agent: "+ConnectSetting.USERAGENT+"\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
                "Accept-Language: zh-tw,en-us;q=0.7,en;q=0.3\r\n"+
                "Accept-Encoding: gzip,deflate\r\n" +
                "Accept-Charset: UTF-8,*\r\n" +
                "Keep-Alive: 115\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: " + mPostContent.length() + "\r\n\r\n" +
        
                mPostContent
               
                ;
               
               
               
                return str;
        }
       
       
       
}
 
 
class InterfaceList{
       
       
        private Vector<String> mInterfaceList ;
       
        InterfaceList() {
               
                mInterfaceList = new Vector<String>( ) ;
                getInterface() ;
               
                // System.out.println("JAVA Ver.:" + System.getProperty("java.version"));
        }
       
        public int getLength() {
                return mInterfaceList.size()/2;
        } // get
       
        public String get( int index ) {
                return mInterfaceList.get(index);
        } // get
       
       
        private void getInterface()  {
                // int i = 0 ;
               
        try {
                       
                        /*
                        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces(); 
            InetAddress ip = null; 
            while (allNetInterfaces.hasMoreElements()) { 
                NetworkInterface netInterface =  allNetInterfaces.nextElement();   
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses(); 
                while (addresses.hasMoreElements()) { 
                   ip = addresses.nextElement(); 
                   if (ip != null && ip instanceof InetAddress) { 
                      System.out.println("My IP = " + ip.getHostAddress()); 
                    } 
                } 
            } 
                       
                        */
                       
                       
                       
                       
            Enumeration<NetworkInterface> enumeration= NetworkInterface.getNetworkInterfaces();
            // i = 0 ;
            while(enumeration.hasMoreElements()){
          
              NetworkInterface networkInterface=enumeration.nextElement();
              if ( networkInterface.getHardwareAddress() != null )
              {
                // System.out.println( networkInterface);
            
                Enumeration <InetAddress> enu1 = networkInterface.getInetAddresses();
                               
           
                while ( enu1.hasMoreElements() ) {
                  InetAddress inetAddr = enu1.nextElement();
                                  byte[] mac = networkInterface.getHardwareAddress();
                                  StringBuilder sb = new StringBuilder();
                          for (int i = 0; i < mac.length; i++) {
                                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "" : ""));
                          }
                                 
                                  if ( (inetAddr+"").length() <= 17) {
                                          mInterfaceList.add( sb.toString() ) ;
                                          mInterfaceList.add( (""+inetAddr).substring(1) ) ;
                                      //System.out.println(sb.toString());
                      //System.out.println( (""+inetAddr).substring(1) );
                                  } // if
                } // while
            
            
              // System.out.println( "" );
              } // if
         
          
              //i ++ ;
            } // while
                       
            } catch (Exception e){
                       
                }
               
               
        }
       
}// class
 
 
 
 
class SCS{
       
        // private int mNetworkAdapterCounts = 0 ;
        private InterfaceList mInterfaceList ; 
        // 0 mac, 1 IP,
        // 2 mac, 3 IP,
        // ...
        private Vector<String> mPostVector ;
       
        SCS() {
               
               
               
               
                mInterfaceList = new InterfaceList();
                // mNetworkAdapterCounts = mInterfaceList.size() ;
                // System.out.println( mInterfaceList.get(0));
                // System.out.println( mInterfaceList.get(1));
               
               
               
                mPostVector = new Vector<String>() ;
               
               
                toPostFormat() ;
               
               
               
        }
       
        public String getInterface( int index ){
                return mInterfaceList.get( index*2+1  ) ;
        }
       
        public int getNetworkadaptercounts () {
            return  mInterfaceList.getLength() ;
        }
       
        private void toPostFormat() {
                String str = "";
               
                Date date1 = new Date();
               
                System.out.println( date1 ) ;
               
                // mPostVector.add ( "id="                      +"456789"                     + "&" );
                mPostVector.add ( "date_time="               +get_date_time()              + "&" );
                mPostVector.add ( "hostname="                +get_hostname()               + "&" );
                mPostVector.add ( "site="                    +get_site(0)                  + "&" ); // multiple networkCard had multiple site
                mPostVector.add ( "securitystatus="          +get_security$status()        + "&" );
                mPostVector.add ( "antivirusstatus="         +get_antivirus$status()       + "&" );
                mPostVector.add ( "Antivirusversion="        +get_Antivirus$version()      + "&" );
                mPostVector.add ( "definition="              +get_definition()             + "&" );
                mPostVector.add ( "USB="                     +get_USB()                    + "&" );
                mPostVector.add ( "hotfix="                  +get_hotfix()                 + "&" );
                mPostVector.add ( "CPUModel="                +get_CPU$Model()              + "&" );
                //=================================================================================            
                mPostVector.add ( "logondomain="             +get_logondomain()            + "&" );
                mPostVector.add ( "logonuser="               +get_logonuser()              + "&" );
                mPostVector.add ( "IP="                      +get_IP( 0 )                  + "&" );  // multiple networkCard had multiple IP
                mPostVector.add ( "macaddress="              +get_mac$address( 0 )         + "&" );  // multiple networkCard had multiple MAC
                mPostVector.add ( "OSversion="               +get_OS$Version()             + "&" );
                mPostVector.add ( "servicepack="             +get_service$pack()           + "&" );
                mPostVector.add ( "computertype="            +get_computer$type()          + "&" );
                mPostVector.add ( "manufacturer="            +get_manufacturer()           + "&" );
                mPostVector.add ( "model="                   +get_model()                  + "&" );
                mPostVector.add ( "PCuser="                  +get_PC$user()                + "&" );
                //======t==========================================================================
                mPostVector.add ( "pcdomain="                +get_PC$domain()              + "&" );
                mPostVector.add ( "HDD="                     +get_HDD()                    + "&" );
                mPostVector.add ( "HDDcounts="               +get_HDD$counts()             + "&" );
                mPostVector.add ( "RAM="                     +get_RAM()                    + "&" );
                mPostVector.add ( "AssetID="                 +get_ASSetID()                + "&" );
                mPostVector.add ( "DG="                      +get_DG()                     + "&" );
                mPostVector.add ( "admin="                   +get_admin()                  + "&" );
                mPostVector.add ( "networkadaptercounts="    +get_networkAdapter$counts()  + "&" ); 
                mPostVector.add ( "XFort="                   +get_XFort()                  + "&" );
                                                                      
                Date date2 = new Date();
               
                System.out.println( date2 ) ;
               
                System.out.println( "so many time?" + (date2.getTime() - date1.getTime()) );   
               
        }
       
        public String toPostFormat( int index ) {
                String str = "";
               
               
                for ( int i = 0 ; i < ConnectSetting.SCSITEMCOUNT ; i ++ ) {
                        if      ( i == 2 ) str += "site=" +get_site(index)+ "&" ;
                        else if ( i == 12) str += "IP="+get_IP( index )+ "&" ;
                        else if ( i == 13) str += "macaddress="+get_mac$address( index )+ "&" ;
                        else str += mPostVector.get(i) ;
                }
               
                return str ;
                                                                     
        }
       
       
        /*  old Version lieave a record
        public String toPostFormat( int index ) {
                String str = "";
               
                str += "id="                      +"456789"                     + "&" ;
                str += "date_time="               +get_date_time()              + "&" ;
                str += "hostname="                +get_hostname()               + "&" ;
                str += "site="                    +get_site()                   + "&" ; // multiple networkCard had multiple site
                str += "securitystatus="          +get_security$status()        + "&" ;
                str += "antivirusstatus="         +get_antivirus$status()       + "&" ;
                str += "Antivirusversion="        +get_Antivirus$version()      + "&" ;
                str += "definition="              +get_definition()             + "&" ;
                str += "USB="                     +get_USB()                    + "&" ;
                str += "hotfix="                  +get_hotfix()                 + "&" ;
                str += "CPUModel="                +get_CPU$Model()              + "&" ;
                //=====================================================================            
                str += "logondomain="             +get_logondomain()            + "&" ;
                str += "logonuser="               +get_logonuser()              + "&" ;
                str += "IP="                      +get_IP( index )                     + "&" ;  // multiple networkCard had multiple IP
                str += "macaddress="              +get_mac$address( index )            + "&" ;  // multiple networkCard had multiple MAC
                str += "OSversion="               +get_OS$Version()             + "&" ;
                str += "servicepack="             +get_service$pack()           + "&" ;
                str += "computertype="            +get_computer$type()          + "&" ;
                str += "manufacturer="            +get_manufacturer()           + "&" ;
                str += "model="                   +get_model()                  + "&" ;
                str += "PCuser="                  +get_PC$user()                + "&" ;
                //=====================================================================
                str += "pcdomain="                +get_PC$domain()              + "&" ;
                str += "HDD="                     +get_HDD()                    + "&" ;
                str += "HDDcounts="               +get_HDD$counts()             + "&" ;
                str += "RAM="                     +get_RAM()                    + "&" ;
                str += "AssetID="                 +get_ASSetID()                + "&" ;
                str += "DG="                      +get_DG()                     + "&" ;
                str += "admin="                   +get_admin()                  + "&" ;
                str += "networkadaptercounts="    +get_networkAdapter$counts()  + "&" ; 
                str += "XFort="                   +get_XFort()                  + "&" ;
                                                                      
                return str.replace( ",","" );
               
        }
        */
       
        @Override
        public String toString() {
                String str = "";
               
                str += "id="                      +"456789"                     + "&" ;
                str += "date_time="               +get_date_time()              + "&" ;
                str += "hostname="                +get_hostname()               + "&" ;
                str += "site="                    +get_site( 0 )                + "&" ; // multiple networkCard had multiple site
                str += "securitystatus="          +get_security$status()        + "&" ;
                str += "antivirusstatus="         +get_antivirus$status()       + "&" ;
                str += "Antivirusversion="        +get_Antivirus$version()      + "&" ;
                str += "definition="              +get_definition()             + "&" ;
                str += "USB="                     +get_USB()                    + "&" ;
                str += "hotfix="                  +get_hotfix()                 + "&" ;
                str += "CPUModel="                +get_CPU$Model()              + "&" ;
                //=====================================================================            
                str += "logondomain="             +get_logondomain()            + "&" ;
                str += "logonuser="               +get_logonuser()              + "&" ;
                str += "IP="                      +get_IP(0)                    + "&" ;  // multiple networkCard had multiple IP
                str += "macaddress="              +get_mac$address(0)           + "&" ;  // multiple networkCard had multiple MAC
                str += "OSversion="               +get_OS$Version()             + "&" ;
                str += "servicepack="             +get_service$pack()           + "&" ;
                str += "computertype="            +get_computer$type()          + "&" ;
                str += "manufacturer="            +get_manufacturer()           + "&" ;
                str += "model="                   +get_model()                  + "&" ;
                str += "PCuser="                  +get_PC$user()                + "&" ;
                //=====================================================================
                str += "pcdomain="                +get_PC$domain()              + "&" ;
                str += "HDD="                     +get_HDD()                    + "&" ;
                str += "HDDcounts="               +get_HDD$counts()             + "&" ;
                str += "RAM="                     +get_RAM()                    + "&" ;
                str += "AssetID="                 +get_ASSetID()                + "&" ;
                str += "DG="                      +get_DG()                     + "&" ;
                str += "admin="                   +get_admin()                  + "&" ;
                str += "networkadaptercounts="    +get_networkAdapter$counts()  + "&" ; 
                str += "XFort="                   +get_XFort()                  + "&" ;
                                                                      
                return str.replace( ",","" );
        }
       
       
//=============================================start Get SCS===========================================
        public String get_date_time() {
               
                String str = "1" ;
                Date date = new Date();
               
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH");
               
                str = formatter.format( date ) ;
               
                return str;
        } // get_Date()
       
       
       
       
       
        public static String get_hostname() {
               
                String str = "2" ;
          try{
             InetAddress addr;
             addr = InetAddress.getLocalHost();
             str = addr.getHostName();
          }catch (Exception ex){
             str= "Hostname can not be resolved //SCS->get_HostName" ;
          } // catch
               
                return str;
        } // get_HostName()
       
        //column3
        public String get_site( int index ) {
               
                String str = "3" ;
               
                return str ;
        } // get_site()
       
        //column4
        public String get_security$status() {
               
                String str = "4" ;
               
                return str ;
        } // get_securityStatus()
       
        //column5
        public String get_antivirus$status() {
               
                String str = "5" ;
               
                return str ;
        } // get_antivirusStatus()
       
//=====================================================================================================
        // column6
        public String get_Antivirus$version() {
               
                String str = "6" ;
               
                return str ;
        } // get_AntivirusVersion()
       
        //column7
        public String get_definition() {
               
                String str = "7" ;
               
                return str ;
        } // get_definition()
       
        //column8
        public String get_USB() {
               
                String str = "8" ;
               
                return str ;
        } // get_USB()
       
        //column9
        public String get_hotfix() {
               
                String str = "9" ;
               
                return str ;
        } // get_hotfix()
       
        //column10
        public String get_CPU$Model() {
               
                String str = "10" ;
               
                return str ;
        } // get_CPU$Model()
       
//==========================================================================================================================
        //column11
        public String get_logondomain() {
               
                String str = "11" ;
               
                str = System.getenv("USERDOMAIN");
               
                return str ;
        }
       
        //column12
        public String get_logonuser() {
               
                String str = "12" ;
               
                str =  System.getProperty("user.name");
               
                return str ;
        }
       
        //column13
        public String get_IP( int index ) {
               
                String str = "13" ;
                str = mInterfaceList.get(index*2+1) ;
               
               
                return str ;
        }
       
        //column14
        public String get_mac$address( int index ) {
               
                String str = "14" ;
                str = mInterfaceList.get(index*2) ;
               
                return str ;
        }
       
        //column15
        public String get_OS$Version() {
               
                String str = "15" ;
               
                str = System.getProperty("os.version");
               
                return str ;
        }
       
//==============================================================================================================
        //colun16
        public String get_service$pack() {
               
                String str = "16" ;
               
                return str ;
        }
       
        //column17
        public String get_computer$type() {
               
                String str = "17" ;
               
                return str ;
        }
       
        //column18
        public String get_manufacturer() {
               
                String str = "18" ;
               
                return str ;
        }
       
        //column19
        public String get_model() {
               
                String str = "19" ;
               
                return str ;
        }
       
        //column20
        public String get_PC$user() {
               
                String str = "20" ;
               
                return str ;
        }
       
//=============================================================================================
        //column21
        public String get_PC$domain() {
               
                String str = "21" ;
               
                return str ;
        }
       
        //column22
        public String get_HDD() {
               
                String str = "22" ;
               
                return str ;
        }
               
        //column23
        public String get_HDD$counts() {
               
                String str = "23" ;
               
                return str ;
        }
               
        //column24
        public String get_RAM() {
               
                String str = "24" ;
               
                return str ;
        }
               
        //column25
        public String get_ASSetID() {
               
                String str = "25" ;
               
                return str ;
        }
 
//=================================================================================
        //column26
        public String get_DG() {
               
                String str = "26" ;
               
                return str ;
        }
               
        //column27
        public String get_admin() {
               
                String str = "27" ;
               
                return str ;
        }
               
        //column28
        public String get_networkAdapter$counts() {
               
                String str = "28" ;
               
                str = "" + getNetworkadaptercounts () ;
               
                return str ;
        }
               
        //column29
        public String get_XFort() {
               
                String str = "" ;
               
                return str ;
        }
//==================================end Get SCS=====================================================================
       
       
}