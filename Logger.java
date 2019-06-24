package org.firstinspires.ftc.teamcode;

//imports
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Vector;

public class Logger
{
    //members
    private String m_logPath;
    private int m_burstSize;
    private AtomicBoolean m_isRunning = new AtomicBoolean(false);
    private AtomicBoolean m_stopping = new AtomicBoolean(false);

    //private functions
    private void init(String logPath, int burstSize)
    {
        m_logPath = logPath;
        m_burstSize = burstSize;
    }

    //constructors
    public Logger(String logPath)
    {
        init(logPath, 100);
    }
    public Logger(String logPath, int burstSize)
    {
        init(logPath, burstSize);
    }

    //public functions
    public void run()
    {
        if (!m_isRunning.get())
        {
            m_isRunning.set(true);
            while (!m_stopping.get())
            {
            } //end  while (!m_stopping.get())
        } //end  if (!m_isRunning.get())
    }
    //public void log(
    synchronized void stop()
    {
        m_stopping.set(true);
    }
}
