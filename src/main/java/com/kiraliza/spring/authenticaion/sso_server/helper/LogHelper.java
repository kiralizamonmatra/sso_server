package com.kiraliza.spring.authenticaion.sso_server.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class LogHelper
{
    private static final byte stackDeep = 4;

    public static String getErrorMsg(Exception e)
    {
        return getTAG() + " ERROR: " + getErrStr(e);
    }


    public static String getErrorMsg(String msg)
    {
        return getTAG() + " ERROR: " + msg;
    }


    private static void error(Logger logger, Exception e)
    {
        if (logger != null)
        {
            String errText = (getErrStr(e));

            if (e.getCause() != null)
            {
                errText += "\r\n Cause: " + e
                    .getCause()
                    .toString();
                Throwable rootCause = e;
                while (rootCause.getCause() != null && rootCause.getCause() != rootCause)
                {
                    rootCause = rootCause.getCause();
                }
                errText += "\r\n        " + rootCause.getMessage();
            }

            String trace = getShortStackTrace(e);

            if (!trace.isEmpty())
            {
                errText += "\r\n" + trace;
            }

            logger.error(errText);
        }
    }


    public static void error(Exception e)
    {
        Logger logger = LoggerFactory.getLogger(getTAG());
        error(logger, e);
    }


    public static void error(Exception e, String message)
    {
        Logger logger = LoggerFactory.getLogger(getTAG());
        logger.error(message);
        error(logger, e);
    }


    private static String getShortStackTrace(Exception e)
    {
        StringBuilder errorStack = new StringBuilder();
        if (e.getStackTrace().length > 0)
        {
            errorStack.append(" Exception Trace \r\n");
            for (int i = 0; i < Math.min(stackDeep, e.getStackTrace().length); i++)
            {
                String eTrace = e.getStackTrace()[i].toString();
                errorStack.append("   at " + eTrace + "\r\n");
            }
        }

        StackTraceElement[] steList = Thread
            .currentThread()
            .getStackTrace();
        if (steList.length > 0)
        {
            int currentStackPosition = stackDeep;
            int index = Math.min(currentStackPosition, steList.length - 1);
            errorStack.append(" Method Trace \r\n");
            for (int i = index; i < Math.min(currentStackPosition + stackDeep, steList.length); i++)
            {
                if (!steList[i]
                    .getClassName()
                    .equals(LogHelper.class.getName()))
                {
                    errorStack.append("   at " + steList[i].toString() + "\r\n");
                }
            }
        }
        return errorStack.toString();
    }


    public static void printStackTrace()
    {
        printStackTrace(null);
    }


    public static void printStackTrace(Exception e)
    {
        printStackTrace(e, stackDeep);
    }


    public static void printStackTrace(Exception e, int count)
    {
        StringBuilder errorStack = new StringBuilder();

        if (e != null && e.getStackTrace().length > 0)
        {
            errorStack.append(" Exception Trace: \r\n");
            for (int i = 0; i < Math.min(count, e.getStackTrace().length); i++)
            {
                String eTrace = e.getStackTrace()[i].toString();
                errorStack.append("   at " + eTrace + "\r\n");
            }
        }
        else
        {
            errorStack.append(" Method Trace: \r\n");
            StackTraceElement[] steList = Thread
                .currentThread()
                .getStackTrace();
            if (steList.length > 0)
            {
                for (int i = 0; i < Math.min(count, steList.length); i++)
                {
                    if (!steList[i]
                        .getClassName()
                        .equals(LogHelper.class.getName()))
                    {
                        errorStack.append("   at " + steList[i].toString() + "\r\n");
                    }
                }
            }
        }

        LoggerFactory
            .getLogger(getTAG())
            .info(errorStack.toString());
    }


    public static void error(String msg)
    {
        LoggerFactory
            .getLogger(getTAG())
            .error(msg);
    }


    public static void error(String... messages)
    {
        StringBuilder resultMsg = new StringBuilder();

        for (String msg : messages)
        {
            if (!resultMsg.isEmpty())
                resultMsg.append(" ");

            resultMsg.append(msg);
        }

        error(resultMsg.toString());
    }


    public static void warn(String msg)
    {
        LoggerFactory
            .getLogger(getTAG())
            .warn(msg);
    }


    public static void warn(String... messages)
    {
        StringBuilder resultMsg = new StringBuilder();

        for (String msg : messages)
        {
            if (!resultMsg.isEmpty())
                resultMsg.append(" ");

            resultMsg.append(msg);
        }

        warn(resultMsg.toString());
    }


    public static void info(String msg)
    {
        if (msg != null && msg.length() > 2024)
        {
            int startPos = 0;
            int endPos = 0;
            int length = msg.length();
            for (int step = 0; endPos < length; step++)
            {
                startPos = step * 2024;
                endPos = startPos + 2024;
                endPos = Math.min(endPos, length);
                LoggerFactory
                    .getLogger(getTAG())
                    .info(msg.substring(startPos, endPos));
            }
        }
        else
        {
            LoggerFactory
                .getLogger(getTAG())
                .info(msg);
        }
    }


    public static void info(String... messages)
    {
        StringBuilder resultMsg = new StringBuilder();

        for (String msg : messages)
        {
            if (!resultMsg.isEmpty())
                resultMsg.append(" ");

            resultMsg.append(msg);
        }

        info(resultMsg.toString());
    }


    public static void debug(String msg)
    {
        LoggerFactory
            .getLogger(getTAG())
            .debug(msg);
    }


    public static void debug(String... messages)
    {
        StringBuilder resultMsg = new StringBuilder();

        for (String msg : messages)
        {
            if (!resultMsg.isEmpty())
                resultMsg.append(" ");

            resultMsg.append(msg);
        }

        debug(resultMsg.toString());
    }


    public static void trace(String msg)
    {
        LoggerFactory
            .getLogger(getTAG())
            .trace(msg);
    }


    public static void trace(String... messages)
    {
        StringBuilder resultMsg = new StringBuilder();

        for (String msg : messages)
        {
            if (!resultMsg.isEmpty())
                resultMsg.append(" ");

            resultMsg.append(msg);
        }

        trace(resultMsg.toString());
    }

    private static String getErrStr(Exception e)
    {
        String strMessage = null;
        String strErrorClass = null;
        if (null == e)
        {
            strMessage = "Unknown Exception";
        }
        else
        {
            strErrorClass = e
                .getClass()
                .getCanonicalName();
            strMessage = e.getMessage();
            if (!StringUtils.hasText(strMessage))
                strMessage = "Exception has no message";
            if (StringUtils.hasText(strErrorClass))
                strMessage = strErrorClass + ": " + strMessage;
        }
        return strMessage;
    }


    private static String getTAG()
    {
        StackTraceElement[] steList = Thread
            .currentThread()
            .getStackTrace();
        if (steList.length > 0)
        {
            int index = Math.min(stackDeep - 1, steList.length - 1);
            StackTraceElement ste = steList[index];
            return getTAG(ste);
        }
        return "-?-";
    }


    private static String getTAG(StackTraceElement ste)
    {
        return ste.getClassName() + "@" + ste.getMethodName() + ":" + ste.getLineNumber();
    }
}
