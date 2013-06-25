package virtickx;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.IOException;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.InputStream;
import java.io.OutputStream;
import java.awt.Robot;
import java.awt.event.KeyEvent;  
import javax.bluetooth.*;
import javax.microedition.io.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Dell
 */
public class Virtickx {

    /**
     * @param args the command line arguments
     */
    //start server
    Robot r;
    KeyEvent ke;
    UUID uuid = new UUID("1101", true);
    StreamConnectionNotifier streamConnNotifier;
    StreamConnection connection;
    InputStream inStream;
    OutputStream outStream;
    RemoteDevice dev ;
    String selectedSetting="";
    int keypress1[]={-1,-1,-1,-1};
    int keypress2[]={-1,-1,-1,-1};
    int keypress4[]={-1,-1,-1,-1};
    int curstate1[]={0,0,0,0};
    int curstate2[]={0,0,0,0};
    int curstate4[]={0,0,0,0,0,0};
    boolean MouseRequire,Closing=false;
    PointerInfo info;
    Point point;
    int MouseSensetivityX=0,SensorStatus=0,MouseSensetivityY=0,HorKeyPWD=0,VerKeyPWD=0,angleHor=0,angleVer=0,window=0;
    protected final int NONDRX[]={-1,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,120,121,48,49,50,51,52,53,54,55,56,57,40,37,39,38,96,97,98,99,100,101,102,103,104,105,18,151,192,92,8,93,44,17,127,35,61,27,36,155,45,91,33,34,46,521,222,16,59,47};
    String LocalAddress="",LocalName="",RemoteAddress="",RemoteName="";
    private void startServer() throws IOException{

        LocalDevice localDevice = LocalDevice.getLocalDevice();
        LocalAddress="Address: "+localDevice.getBluetoothAddress();
        LocalName="Name: "+localDevice.getFriendlyName();
        System.out.println(LocalAddress);
        System.out.println(LocalName);
        //Create a UUID for SPP
        
        //Create the servicve url
        String connectionString = "btspp://localhost:" + uuid +";name=Sample SPP Server";
        //open server url
        streamConnNotifier = (StreamConnectionNotifier)Connector.open( connectionString );
        
        //Wait for client connection
        System.out.println("\nServer Started. Waiting for clients to connect...");
        connection=streamConnNotifier.acceptAndOpen();
        
        //Making connection with the remote device and sending sample message
        RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
        RemoteAddress="Remote device address: "+dev.getBluetoothAddress();
        RemoteName="Remote device name: "+dev.getFriendlyName(true);
        System.out.println(RemoteAddress);
        System.out.println(RemoteName);
        //Opening Input Stream
        inStream=connection.openInputStream();
        outStream=connection.openOutputStream();
  
    }
    public void setSettings(Profile prof)
    {
        for(int i=0;i<4;i++)
        {
            keypress1[i]=prof.keypress1[i];
            keypress2[i]=prof.keypress2[i];
            keypress4[i]=prof.keypress4[i];
        }
           
        MouseRequire=prof.MouseRequired;
        System.out.println(MouseRequire);
        MouseSensetivityX=prof.MouseSensetivityX;
        MouseSensetivityY=prof.MouseSensetivityY;
    }
    public void setSettings(Settings prof)
    {
        for(int i=0;i<4;i++)
        {
            keypress1[i]=prof.keypress1[i];
            keypress2[i]=prof.keypress2[i];
            keypress4[i]=prof.keypress4[i];
        }
           
        MouseRequire=prof.MouseRequired;
        System.out.println(MouseRequire);
        MouseSensetivityX=prof.MouseSensetivityX;
        MouseSensetivityY=prof.MouseSensetivityY;
    }
    public void reverseSetSettings(Settings prof)
    {
        for(int i=0;i<4;i++)
        {
            prof.keypress1[i]=keypress1[i];
            prof.keypress2[i]=keypress2[i];
            prof.keypress4[i]=keypress4[i];
        }
           
        prof.MouseRequired=MouseRequire;
        prof.MouseSensetivityX=MouseSensetivityX;
        prof.MouseSensetivityY=MouseSensetivityY;
    }
    public void reverseSetSettings(Profile prof)
    {
        for(int i=0;i<4;i++)
        {
            prof.keypress1[i]=keypress1[i];
            prof.keypress2[i]=keypress2[i];
            prof.keypress4[i]=keypress4[i];
        }
           
        prof.MouseRequired=MouseRequire;
        prof.MouseSensetivityX=MouseSensetivityX;
        prof.MouseSensetivityY=MouseSensetivityY;
    }
    public void keyStateChange()
    {
        int keycode=0;
        while(window!=3)
        {
            System.out.print("");
            if(window==0)
            {
        try{
            
            keycode=inStream.read();
            if(keycode==255)
                window=3;
            else if(keycode==1)
            {
                r.keyPress(KeyEvent.VK_ESCAPE);
                Thread.sleep(200);
                r.keyPress(KeyEvent.VK_ESCAPE);
            }
            else if(keycode<64)
            {   
                int multi=4;
                for(int i=0;i<4;i++)
                {
                    if((keycode&multi)!=0&&curstate1[i]==0)
                    {
                        if(NONDRX[keypress1[i]]!=120&&NONDRX[keypress1[i]]!=121)
                            r.keyPress(NONDRX[keypress1[i]]);
                        else
                        {
                            if(NONDRX[keypress1[i]]==120)
                                r.mousePress(InputEvent.BUTTON1_MASK);
                            else
                                r.mousePress(InputEvent.BUTTON3_MASK);
                        }
                        curstate1[i]=1;
                    }
                    else if((keycode&multi)==0&&curstate1[i]==1)
                    {
                        if(NONDRX[keypress1[i]]!=120&&NONDRX[keypress1[i]]!=121)
                            r.keyRelease(NONDRX[keypress1[i]]);
                        else
                        {
                            if(NONDRX[keypress1[i]]==120)
                                r.mouseRelease(InputEvent.BUTTON1_MASK);
                            else
                                r.mouseRelease(InputEvent.BUTTON3_MASK);
                        }
                        curstate1[i]=0;
                    }
                    multi=multi*2;
                }
            }
            else if(keycode>=64&&keycode<128)
            {
                int multi=4;
                for(int i=0;i<4;i++)
                {
                    if((keycode&multi)!=0&&curstate2[i]==0)
                    {
                        if(NONDRX[keypress2[i]]!=120&&NONDRX[keypress2[i]]!=121)
                            r.keyPress(NONDRX[keypress2[i]]);
                        else
                        {
                            if(NONDRX[keypress2[i]]==120)
                                r.mousePress(InputEvent.BUTTON1_MASK);
                            else
                                r.mousePress(InputEvent.BUTTON3_MASK);
                        }
                        curstate2[i]=1;
                    }
                    else if((keycode&multi)==0&&curstate2[i]==1)
                    {
                        if(NONDRX[keypress2[i]]!=120&&NONDRX[keypress2[i]]!=121)
                            r.keyRelease(NONDRX[keypress2[i]]);
                        else
                        {
                            if(NONDRX[keypress2[i]]==120)
                                r.mouseRelease(InputEvent.BUTTON1_MASK);
                            else
                                r.mouseRelease(InputEvent.BUTTON3_MASK);
                        }
                        curstate2[i]=0;
                    }
                    multi=multi*2;
                }
            }
            else if(keycode>=128)
            {
                if(MouseRequire)
                {
                        int angley=0,anglex=0;
                        info=MouseInfo.getPointerInfo();
                        point=info.getLocation();
                        angley=inStream.read();
                        anglex=inStream.read();
                        if(anglex==255||angley==255)
                            window=3;
                        anglex=anglex>90?90:anglex;
                        angley=angley>90?90:angley;
                        //System.out.println(anglex);
                        //System.out.println(angley);
                        if((keycode&36)!=0)
                            r.mouseMove(-anglex*MouseSensetivityX+point.x,-angley*MouseSensetivityY+point.y);
                        else if((keycode&40)!=0)
                            r.mouseMove(anglex*MouseSensetivityX+point.x,-angley*MouseSensetivityY+point.y);
                        else if((keycode&20)!=0)
                            r.mouseMove(-anglex*MouseSensetivityX+point.x,angley*MouseSensetivityY+point.y);
                        else if((keycode&24)!=0)
                            r.mouseMove(anglex*MouseSensetivityX+point.x,angley*MouseSensetivityY+point.y);
                }
                else
                {
                    int anglex=inStream.read();
                    //System.out.println(anglex);
                    int angley=inStream.read();
                    //System.out.println(angley);
                    if(anglex>45)
                        anglex=45;
                    if(angley>45)
                        angley=45;
                    angleVer=anglex;
                    angleHor=angley;
                    int multi=4;
                for(int i=0;i<4;i++)
                {
                    if((keycode&multi)!=0&&curstate4[i]==0)
                    {
                        if(NONDRX[keypress4[i]]!=120&&NONDRX[keypress4[i]]!=121)
                            r.keyPress(NONDRX[keypress4[i]]);
                        else
                        {
                            if(NONDRX[keypress4[i]]==120)
                                r.mousePress(InputEvent.BUTTON1_MASK);
                            else
                                r.mousePress(InputEvent.BUTTON3_MASK);
                        }
                        curstate4[i]=1;
                    }
                    else if((keycode&multi)==0&&curstate4[i]==1)
                    {
                        if(NONDRX[keypress4[i]]!=120&&NONDRX[keypress4[i]]!=121)
                            r.keyRelease(NONDRX[keypress4[i]]);
                        else
                        {
                            if(NONDRX[keypress4[i]]==120)
                                r.mouseRelease(InputEvent.BUTTON1_MASK);
                            else
                                r.mouseRelease(InputEvent.BUTTON3_MASK);
                        }
                        curstate4[i]=0;
                    }
                    multi=multi*2;
                }
                        //System.out.println(HorKeyPWD+"  "+angleHor+"  "+anglex);
                    //System.out.println(VerKeyPWD+"  "+angleVer+"  "+angley);
                }
            }
        }
        catch(Exception e){}
        }
        else
        {
                try {
                    keycode=inStream.read();
                } catch (IOException ex) {
                    Logger.getLogger(Virtickx.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(keycode);
        }
        }
    }
    public void close()
    {
        try {
            streamConnNotifier.close();
            inStream.close();
            outStream.close();
        } catch (IOException ex) {
            Logger.getLogger(Virtickx.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    public void virtickStart()throws IOException
    {
        try{r=new Robot();}
        catch(AWTException e){};
        Starting startScreen=new Starting();
        startScreen.setVisible(true);
        //try{Thread.sleep(3000);}
        //catch(InterruptedException e){}
        try{startServer();}
        catch(Exception e)
        {
            BluetoothError error=new BluetoothError();
            error.setVisible(true);
            try{Thread.sleep(5000);}
            catch(InterruptedException ex){}
            error.close();
            close();
        }
        int sensorData=inStream.read();
        Profile pro=new Profile();
        pro.SensorStatus=sensorData;
        pro.createFolderAndFiles();
        if(pro.ErrorShutdown==true)
            close();
        pro.setNames();
        if(pro.ErrorShutdown==true)
            close();
        pro.setVisible(true);
        while(pro.ScreenOn)
            System.out.print("");
        if(pro.ImportError)
        {    
            Error error=new Error();
            error.setVisible(true);
            try{Thread.sleep(5000);}
            catch(InterruptedException ex){}
            error.close();
            close();
        }
        else if(pro.NamesUpdateNotDone)
        {
            UpdationError error=new UpdationError();
            error.setVisible(true);
            try{Thread.sleep(5000);}
            catch(InterruptedException ex){}
            error.close();
        }
        pro.ScreenOn=true;
        startScreen.close();
        setSettings(pro);
        if(MouseRequire)
        {
            angleHor=0;
            angleVer=0;
            VerKeyPWD=0;
            HorKeyPWD=0;
        }
        System.out.println(sensorData);
        Settings set=new Settings();
        /*for(int i=0;i<4;i++)
        {
            //keypress4[i]=SetScreen.keypress4[i];
            System.out.print(keypress4[i]);
            //keypress1[i]=SetScreen.keypress1[i];
            //keypress2[i]=SetScreen.keypress2[i];
            System.out.print(keypress1[i]);
            System.out.print(keypress2[i]);
            System.out.println();
        }
        //MouseRequire=SetScreen.MouseRequired;
        System.out.println(MouseRequire);
        //MouseSensetivityX=SetScreen.MouseSensetivityX;
        System.out.println(MouseSensetivityX);
        //MouseSensetivityY=SetScreen.MouseSensetivityY;
        System.out.println(MouseSensetivityY);
        /*int mr=0;
        if(MouseRequire)
            mr=1;*/
        outStream.write(MouseRequire?1:0);
        Server server=new Server();
        server.setLabels(LocalAddress,LocalName,RemoteAddress,RemoteName);
        server.setVisible(true);
        server.setProfile(pro.ProfileNames[pro.selected]);
        //pwmForKeyHor.start();
        //pwmForKeyVer.start();
        Thread PressKey=new Thread()
            {
                public void run()
                {
                    keyStateChange();
                }
            };PressKey.start();
        while(server.window!=3&&window!=3)
        {
            System.out.print("");
            if(server.window==1)
            {
                window=1;
                reverseSetSettings(set);
                set.SensorStatus=sensorData;
                set.setDefaultState();
                set.setVisible(true);
                while(set.ScreenOn)
                    System.out.print("");
                set.ScreenOn=true;
                setSettings(set);
                if(MouseRequire)
                {
                    angleHor=0;
                    angleVer=0;
                    VerKeyPWD=0;
                    HorKeyPWD=0;
                }
                server.window=0;
                window=0;
            }
            else if(server.window==2)
            {
                window=2;
                reverseSetSettings(pro);
                pro.updateFiles();
                /*pro.setNames();
                if(pro.ErrorShutdown==true)
                    close();*/
                pro.createFolderAndFiles();
                pro.setVisible(true);
                while(pro.ScreenOn)
                    System.out.print("");
                server.setProfile(pro.ProfileNames[pro.selected]);
                if(pro.ImportError)
                {    
                    Error error=new Error();
                    error.setVisible(true);
                    try{Thread.sleep(5000);}
                    catch(InterruptedException ex){}
                    error.close();
                    close();
                }
                else if(pro.NamesUpdateNotDone)
                {
                    UpdationError error=new UpdationError();
                    error.setVisible(true);
                    try{Thread.sleep(5000);}
                    catch(InterruptedException ex){}
                    error.close();
                }
                pro.ScreenOn=true;
                setSettings(pro);
                server.window=0;
                window=0;
            }
        }
        reverseSetSettings(pro);
        pro.updateFiles();
        outStream.write(255);
        Closing=true;
        close();
    }
    /*Thread pwmForKeyHor=new Thread()
    {
        public void run()
        {
            int key=0,angle=0;
            while(!Closing)
            {
                System.out.print("");
                key=HorKeyPWD;
                System.out.print("");
                angle=angleHor;
                System.out.print("");
                System.out.println(key+"  "+angle);
                if(key!=0&&key!=-1)
                {
                    if(key!=120&&key!=121)
                    {
                        r.keyPress(key);
                        long prevtime=System.currentTimeMillis();
                        while((System.currentTimeMillis()-prevtime)>angle*4)
                                System.out.print("");
                        r.keyRelease(key);
                        prevtime=System.currentTimeMillis();
                        while((System.currentTimeMillis()-prevtime)>(180-angle*4))
                                System.out.print("");
                     }
                     else
                        {
                            if(key==120)
                            {
                                r.mousePress(InputEvent.BUTTON1_MASK);
                                long prevtime=System.currentTimeMillis();
                                while((System.currentTimeMillis()-prevtime)>angle*4)
                                    System.out.print("");
                                r.mouseRelease(InputEvent.BUTTON1_MASK);
                                prevtime=System.currentTimeMillis();
                                while((System.currentTimeMillis()-prevtime)>(180-angle*4))
                                    System.out.print("");
                            }
                            else
                            {
                                r.mousePress(InputEvent.BUTTON3_MASK);
                                long prevtime=System.currentTimeMillis();
                                while((System.currentTimeMillis()-prevtime)>angle*4)
                                    System.out.print("");
                                r.mouseRelease(InputEvent.BUTTON3_MASK);
                                prevtime=System.currentTimeMillis();
                                while((System.currentTimeMillis()-prevtime)>(180-angle*4))
                                    System.out.print("");
                            }
                        }
                }
            }
        }
    };
    Thread pwmForKeyVer=new Thread()
    {
        public void run()
        {
            int key=0,angle=0;
            while(!Closing)
            {
                System.out.print("");
                key=VerKeyPWD;
                System.out.print("");
                angle=angleVer;
                System.out.print("");
                System.out.println(key+"  "+angle);
                if(key!=0&&key!=-1)
                {
                    if(key!=120&&key!=121)
                    {
                        r.keyPress(key);
                        long prevtime=System.currentTimeMillis();
                        while((System.currentTimeMillis()-prevtime)>angle*4)
                                System.out.print("");
                        r.keyRelease(key);
                        prevtime=System.currentTimeMillis();
                        while((System.currentTimeMillis()-prevtime)>(180-angle*4))
                                System.out.print("");
                     }
                     else
                        {
                            if(key==120)
                            {
                                r.mousePress(InputEvent.BUTTON1_MASK);
                                long prevtime=System.currentTimeMillis();
                                while((System.currentTimeMillis()-prevtime)>angle*4)
                                    System.out.print("");
                                r.mouseRelease(InputEvent.BUTTON1_MASK);
                                prevtime=System.currentTimeMillis();
                                while((System.currentTimeMillis()-prevtime)>(180-angle*4))
                                    System.out.print("");
                            }
                            else
                            {
                                r.mousePress(InputEvent.BUTTON3_MASK);
                                long prevtime=System.currentTimeMillis();
                                while((System.currentTimeMillis()-prevtime)>angle*4)
                                    System.out.print("");
                                r.mouseRelease(InputEvent.BUTTON3_MASK);
                                prevtime=System.currentTimeMillis();
                                while((System.currentTimeMillis()-prevtime)>(180-angle*4))
                                    System.out.print("");
                            }
                        }
                }
            }
        }
    };*/
    public static void main(String[] args) throws IOException{
        
        Virtickx vir=new Virtickx();
        vir.virtickStart();
    }
}
